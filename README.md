# TBC Bank Test Automation Project

UI, API and data-driven tests for tbcbank.ge, written in Java 21 with Playwright, REST Assured,
MyBatis (H2), TestNG and Allure.

## Running

    mvn clean test
    mvn clean test -DsuiteXml=path/to/other-suite.xml

The suite in testng.xml runs with parallel="classes" and thread-count="2". Each test class starts its own
Chromium in BaseTest, so parallel classes share no browser state.

Lombok is configured as an explicit annotation processor in the compiler plugin, because JDK 23 and newer no
longer run processors from the classpath on their own.

Results go to allure-results/. ScreenshotOnFailureListener saves a screenshot of every failed UI test to
target/screenshots/ and attaches it to the Allure report. To use it, add it under <listeners> in testng.xml.

## 1. Framework Architecture

    src/main/java/ge/tbc/testautomation
        pages/        one class per page, with the locators of that page
        components/   parts used on many pages: header, quick actions, chatbot, cookie banner
        steps/        user actions and their assertions, built from pages and components
        api/client/   REST Assured clients and the JSON models
        database/     MyBatis mapper and the model a row is mapped to
        data/         constants and test data objects
        utils/        DataBaseConfig and the TestNG data providers
    src/test/java/ge/tbc/testautomation/tests
        ui/           BaseTest (browser setup) and the UI tests
        api/          API tests, and ExchangeRateConsistencyTest (API compared with UI)
        LoanCalculatorDbTest   UI test that gets its data from SQL

What each part does:

- pages/ holds the locators for one page. There are no assertions or test logic in them.
- components/ holds the parts that appear on more than one page: HeaderComponent, QuickActionsComponent,
  ChatbotComponent and CookieComponent.
- steps/ holds what a user does and what must be true afterwards. Every step does an action and asserts its
  result (URL, input value, active state), so a failure points to the step that went wrong.
- api/client/ has a BaseApi with the shared RequestSpecification (base URI, JSON headers, logging).
  ExchangeCurrencyApi and AtmsAndBranchesApi add one method per endpoint. The models (BranchSearchRequest,
  BranchResponse, ExchangeRateResponse) are Lombok and Jackson classes.
- database/ and utils/DataBaseConfig contain the MyBatis mapper, the model and the database setup.
- tests/ has the TestNG classes. They only call steps and set the order (priority, dependsOnMethods).

Why components are separate from page objects: the header, the quick actions menu and the cookie banner
appear on every page, so they do not belong to one page. If they were part of the page objects, the same
locators would be copied into each page class. As components they are written once and used wherever they
are needed.

Reuse example: HeaderComponent is used by MortgageSteps, MobilebankSteps, SecuredConsumerLoanSteps and
OffersSteps (through formeNavigationButton) and by TechschoolSteps (through tbcNavigationButton). When
formeNavigationButton was changed to match both /ka and /en links, all of these navigation flows worked in
English after one change. QuickActionsComponent is shared in the same way by ChatbotSteps, CurrencySteps and
QuickActionsSteps.

## 2. Localization Strategy

Georgian and English use one test class and one set of steps. Only the data is different.

- OffersLocalizationData holds everything that differs per language: the locale code, the URL parts, the
  filter parameter and the translated texts.
- OffersDataProvider returns one OffersLocalizationData per language. OffersLocalizationTest has a @Factory
  constructor that uses it, so TestNG creates one test class instance per locale. Each instance has its own
  browser (from BaseTest) and its own copy of the four ordered tests.
- Steps and pages contain no language-specific text. The locators take their text and URLs from the data
  object, and the header locator formeNavigationButton matches both /ka and /en.
- The language is changed through the header language switcher (HeaderComponent.languageSwitcher), the same
  way a user does it, and the step then asserts the URL. So the switch itself is tested.

To add another locale, add one row to OffersDataProvider with the URL prefix and the translated texts of the
new language. The test, steps and pages stay the same. One thing to know: the site's switcher is currently a
single toggle that shows the other language and flips to it, and that is what switchLanguage clicks. A third
language would turn it into a list, so HeaderComponent would need a locator for the option to select.

## 3. SQL and Test Data Strategy

The inputs for the loan calculator are stored in a database and not in the test code. They are read with
MyBatis, so the test gets normal Java objects.

The flow is Database, MyBatis, Java model, DataProvider, Test:

1. Database: DataBaseConfig creates an in-memory H2 database, creates the table
   loan_test_data(id, amount, currency, period) and inserts three rows (20000 USD 8 years, 50000 GEL
   10 years, 10000 EUR 5 years). H2 needs no external service, so the suite runs on its own.
2. MyBatis: LoanDataMapper is an annotation-based mapper with
   @Select("SELECT id, amount, currency, period FROM loan_test_data"). DataBaseConfig.dbMapper() builds the
   SqlSessionFactory once and returns the mapper.
3. Java model: LoanCalculatorData. The column names are the same as the field names, so MyBatis maps the rows
   automatically.
4. DataProvider: LoanDataProvider ("dbLoanData") calls the mapper and returns one LoanCalculatorData per row.
5. Test: LoanCalculatorDbTest.testCalculateAndVerifyLoan(LoanCalculatorData) runs once per row. It selects
   the currency, enters the amount, enters the period and verifies the result.

To add a new data variation without changing the test, insert another row in DataBaseConfig.initDatabase, for
example INSERT INTO loan_test_data (amount, currency, period) VALUES ('75000', 'USD', '12'). The
DataProvider then produces one more test run. The mapper, model and test do not change. The values have to
follow the calculator's own limits (amount from 3,000 to 500,000).

## 4. API to UI Strategy

ExchangeRateConsistencyTest compares the rate from the API with the rate shown on the site.

- API: GET /api/v1/exchangeRates/getExchangeRate?Iso1=GEL&Iso2=GBP on the TBC API gateway
  (ExchangeCurrencyApi). The response is read into ExchangeRateResponse (buyRate, sellRate). The calculator
  page for the same pair (/ka/valutis-kursi/GEL-to-GBP?amount=100) shows these rates.
- Compared values (element .exchange-rates-calculator__description):
  - at the start: "1 GEL = buyRate GBP", with 4 decimals
  - after clicking the swap button: "1 GBP = 1/sellRate GEL", with 3 decimals
- Why these values: they are the two numbers the calculator shows for the two directions. The number of
  decimals is the same as on the page (for example 0.2833 and 3.371), so the comparison is exact and not
  approximate. The API request runs first and its result is used by the UI tests.
- Problems the test can find: an old or cached rate on the page, buy and sell mixed up, a wrong inversion or
  rounding after the swap, and the wrong currency pair being loaded for the URL.
- Known limitation: the test assumes the calculator opens in the GEL to GBP direction. The direction was seen
  to be different in at least one run, so the first assertion can fail because of that.

## 5. Network Validation

NetworkValidationTest (with AtmsAndBranchesUiSteps) connects one UI action to the request it causes.

- UI action: open /ka/atms&branches, click the ATMs tab (its active state is asserted), open the city
  dropdown and select Kutaisi.
- Monitored request: the action is wrapped in page.waitForResponse and matches POST
  .../api/v1/atmsAndBranches/list with status 200. The wait is registered before the click, so the response
  cannot be missed.
- Checked at network level: status 200, method POST, the URL is the locations endpoint, the request body is
  valid JSON with ATM in filter and ka-GE as locale, the response is a non-empty array where every item has
  type ATM, and the number of items with regionName Kutaisi is counted (62 when this was written).
- Checked on the UI: the dropdown shows the selected city, the number of cards is equal to the API count for
  that city, and every card contains the city name and ATM.
- What this showed: the city is not sent to the API (keyword is empty). The response contains all ATMs and the
  page filters them in the browser. Comparing the card count with the API count for the city is what proves
  that the filter works.

## 6. Test Stability

Scenario: the secured consumer loan calculator (LoanCalculatorDbTest). Sources of instability that were found,
and what the implementation does about them:

- The form state depends on the order of actions. Pressing Enter in the amount field resets the currency to
  GEL and the period to 10, and changing the currency resets the amount to 50,000. So fillAmount confirms the
  value with Tab, and the test selects the currency first, then the amount, then the period.
- The result is animated. The monthly payment is drawn digit by digit (one span per digit, with an old and a
  new copy), so reading it too early gives a partial number. The test asserts the summary that repeats the
  input values (for example 20,000$ and 8 years) using containsText, which retries by itself. The payment is
  only checked for the currency symbol and a non-zero digit, never for a fixed amount. The mortgage payment
  is compared with a tolerance for the same reason.
- The backend and the page can be slow. Calculators, the chat window and rate tables sometimes show loading
  placeholders first, especially when several browsers run. The tests rely on Playwright auto-waiting and
  web-first assertions, and the default assertion timeout is set to 15 seconds in BaseTest. This is a maximum
  and not a delay: an assertion passes as soon as its condition is true.
- An ignored input would show up much later as a wrong result. To avoid that, each step asserts its own effect
  (input value, active currency button, URL), so the failure is reported at the step that caused it.
- Parallel runs can interfere with each other. Each class instance has its own browser, the viewport is fixed
  at 1920x1080 so a small window cannot collapse the header, and no page state is stored in static fields.

There is no Thread.sleep and no waitForTimeout in the code, and no retry logic, so a failed test is a real
failure. The network test does not guess timing: it waits for the response that its own click triggers.

Known limitations: CookieSteps uses a 3 second optional wait and ignores the timeout, because the banner may
not be shown. The two BaseApi classes (currency and locations) are almost the same. The MySQL driver is a
dependency, but only H2 is configured.
