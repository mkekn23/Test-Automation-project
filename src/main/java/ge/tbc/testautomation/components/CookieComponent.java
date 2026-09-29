package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

import static ge.tbc.testautomation.data.Constants.AGREE_TEXT;

public class CookieComponent extends CommonPage {
    public final Locator acceptCookiesBtn;

    public CookieComponent(Page page) {
        super(page);
        acceptCookiesBtn = page.locator("app-cookie-consent button.primary")
                .filter(new Locator.FilterOptions().setHasText(AGREE_TEXT))
                .first();
    }


}
