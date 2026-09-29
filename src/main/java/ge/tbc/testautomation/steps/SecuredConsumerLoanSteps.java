package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.database.models.LoanCalculatorData;
import ge.tbc.testautomation.pages.SecuredConsumerLoanPage;
import io.qameta.allure.Step;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.data.Constants.YEARS_SUFFIX;

public class SecuredConsumerLoanSteps {
    private static final Map<String, String> CURRENCY_SYMBOLS = Map.of("GEL", "₾", "USD", "$", "EUR", "€");

    private final Page page;
    private final HeaderComponent headerComponent;
    private final SecuredConsumerLoanPage loanPage;

    public SecuredConsumerLoanSteps(Page page) {
        this.page = page;
        this.headerComponent = new HeaderComponent(page);
        this.loanPage = new SecuredConsumerLoanPage(page);
    }

    @Step("Navigate to Consumer Loans section")
    public SecuredConsumerLoanSteps navigateToConsumerLoans() {
        headerComponent.formeNavigationButton.hover();
        headerComponent.consumerLoanMenuLink.click();

        assertThat(page).hasURL(Pattern.compile(".*/loans/consumer-loan/?$"));
        return this;
    }

    @Step("Open Secured Consumer Loan details")
    public SecuredConsumerLoanSteps openSecuredConsumerLoanDetails() {
        loanPage.clickSecuredLoanDetails();

        assertThat(page).hasURL(Pattern.compile(".*/consumer-loan/secured-consumer-loan/?$"));
        assertThat(loanPage.getAmountInput()).isVisible();
        assertThat(loanPage.getYearsInput()).isVisible();
        return this;
    }

    @Step("Enter loan amount: {amount}")
    public SecuredConsumerLoanSteps enterAmount(String amount) {
        loanPage.fillAmount(amount);

        assertThat(loanPage.getAmountInput()).hasValue(amount);
        return this;
    }

    @Step("Select currency: {currency}")
    public SecuredConsumerLoanSteps selectCurrency(String currency) {
        loanPage.selectCurrency(currency);

        assertThat(loanPage.getCurrencyButton(currency)).hasClass(Pattern.compile(".*--active.*"));
        return this;
    }

    @Step("Enter loan period in years: {period}")
    public SecuredConsumerLoanSteps enterPeriod(String period) {
        loanPage.fillYears(period);

        assertThat(loanPage.getYearsInput()).hasValue(period);
        return this;
    }

    @Step("Verify calculation result reflects the entered data: {loanData}")
    public SecuredConsumerLoanSteps verifyCalculationResult(LoanCalculatorData loanData) {
        String symbol = CURRENCY_SYMBOLS.get(loanData.getCurrency());
        String formattedAmount = String.format(Locale.US, "%,d", Long.parseLong(loanData.getAmount()));

        assertThat(loanPage.getCalculationSummary()).containsText(formattedAmount + symbol);
        assertThat(loanPage.getCalculationSummary()).containsText(loanData.getPeriod() + " " + YEARS_SUFFIX);

        assertThat(loanPage.getMonthlyPaymentResult()).isVisible();
        assertThat(loanPage.getMonthlyPaymentResult()).containsText(symbol);
        assertThat(loanPage.getMonthlyPaymentResult()).containsText(Pattern.compile("[1-9]"));
        return this;
    }
}
