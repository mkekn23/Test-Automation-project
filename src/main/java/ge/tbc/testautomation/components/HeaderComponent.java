package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

public class HeaderComponent extends CommonPage {
    public final Locator formeNavigationButton;
    public final Locator tbcNavigationButton;

    public final Locator techschoolSubMenuLink;
    public final Locator mobilebankSubMenuLink;
    public final Locator mortgageSubMenuLink;
    public final Locator consumerLoanMenuLink;
    public final Locator languageSwitcher;

    public HeaderComponent(Page page) {
        super(page);
        this.formeNavigationButton = page.locator("a[href='/ka'] button.tbcx-pw-navigation-item__link:visible, " +
                "a[href='/en'] button.tbcx-pw-navigation-item__link:visible");
        this.languageSwitcher = page.locator("tbcx-lang-switcher.show-tablet-up .tbcx-language-select__field");
        this.tbcNavigationButton = page.locator("a[href*='about-us'] button.tbcx-pw-navigation-item__link:visible");
        this.techschoolSubMenuLink =  page.locator(".show-tablet-up a[href*='tech-school']");
        this.mobilebankSubMenuLink = page.locator(".show-tablet-up a[href*='/ka/digital-services/mobile-bank']");
        this.mortgageSubMenuLink = page.locator(".show-tablet-up a[href*='/ka/loans/mortgage']");
        this.consumerLoanMenuLink = page.locator(".show-tablet-up a[href*='consumer-loan']").first();
    }

    public Locator getOffersLink(String offersUrlPart) {
        return page.locator("a[href='" + offersUrlPart + "']:visible");
    }
}