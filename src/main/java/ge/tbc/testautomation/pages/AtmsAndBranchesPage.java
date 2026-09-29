package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AtmsAndBranchesPage extends CommonPage{

    // Locators
    private final Locator cityDropdown;
    private final Locator atmTab;
    private final Locator atmTabButton;
    private final Locator locationCards;

    public AtmsAndBranchesPage(Page page) {
        super(page);
        this.cityDropdown = page.locator(".tbcx-pw-atm-branches-section_inputs tbcx-dropdown-selector, tbcx-dropdown-selector").first();
        this.atmTab = page.locator("text=ბანკომატები");
        this.atmTabButton = page.locator("button.tbcx-pw-tab-menu__item")
                .filter(new Locator.FilterOptions().setHasText("ბანკომატები"));
        this.locationCards = page.locator("app-atm-branches-section-list-item");
    }

    public void navigateToPage(String url) {
        page.navigate(url);
    }

    public void clickCityDropdown() {
        cityDropdown.click();
    }

    public void selectCityFromDropdown(String cityName) {
        page.locator("tbcx-dropdown-popover-item")
                .filter(new Locator.FilterOptions().setHasText(cityName))
                .first()
                .click();
    }

    public void clickAtmTab() {
        atmTab.click();
    }

    public Locator getAtmTabButton() {
        return atmTabButton;
    }

    public Locator getCityDropdown() {
        return cityDropdown;
    }

    public Locator getLocationCards() {
        return locationCards;
    }

    public Page getPage() {
        return page;
    }
}