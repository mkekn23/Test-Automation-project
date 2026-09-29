package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.steps.CookieSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.util.Collections;
import java.util.List;

import static ge.tbc.testautomation.data.Constants.BASE_URL;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected CookieSteps cookieSteps;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        launchOptions.setArgs(Collections.singletonList("--start-maximized"));

        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setPermissions(List.of("geolocation"))
                .setGeolocation(41.7151, 44.8271)
                .setViewportSize(null));

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