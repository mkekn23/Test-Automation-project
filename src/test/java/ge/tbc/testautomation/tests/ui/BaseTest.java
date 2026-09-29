package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.steps.CookieSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static ge.tbc.testautomation.data.Constants.BASE_URL;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected CookieSteps cookieSteps;

    // the site's calculators/chat/rates load slowly (skeleton loaders), especially with several browsers running
    private static final double ASSERTION_TIMEOUT_MS = 15_000;

    @BeforeClass
    public void setUp() {
        PlaywrightAssertions.setDefaultAssertionTimeout(ASSERTION_TIMEOUT_MS);
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        launchOptions.setArgs(Collections.singletonList("--start-maximized"));

        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setPermissions(List.of("geolocation"))
                .setGeolocation(41.7151, 44.8271)
                .setViewportSize(1920, 1080));

        browserContext.route(Pattern.compile(".*(medallia|kampyle).*"), Route::abort);

        page = browserContext.newPage();
        page.navigate(BASE_URL);

        cookieSteps = new CookieSteps(page);
        cookieSteps.acceptCookiesIfPresent();
    }

    @AfterClass
    public void tearDown() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}