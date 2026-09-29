package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

public class CookieComponent extends CommonPage {
    public final Locator acceptCookiesBtn;

    public CookieComponent(Page page) {
        super(page);
        acceptCookiesBtn = page.locator("app-cookie-consent button.primary")
                .filter(new Locator.FilterOptions().setHasText("თანხმობა"))
                .first();
    }


}
