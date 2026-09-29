package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.CookieComponent;

public class CookieSteps extends CommonSteps {
    private CookieComponent cookieComponent;
    public CookieSteps(Page page) {
        super(page);
        cookieComponent = new CookieComponent(page);
    }

    public void acceptCookiesIfPresent() {
        try {
            cookieComponent.acceptCookiesBtn.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            cookieComponent.acceptCookiesBtn.evaluate("el => el.click()");
        } catch (Exception ignored) {
        }
    }
}
