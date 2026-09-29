package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OffersPage extends CommonPage {
    public final Locator pageHeading;
    public final Locator filterItems;

    public OffersPage(Page page) {
        super(page);

        this.pageHeading = page.locator("h2, h1").first();
        this.filterItems = page.locator("app-marketing-filter-item");
    }

    public Locator getAllOffersButton(String allOffersUrlPart) {
        return page.locator("a[href*='" + allOffersUrlPart + "']").first();
    }

    public Locator getShoppingFilterLabel(String categoryText) {
        return filterItems.filter(new Locator.FilterOptions().setHasText(categoryText));
    }

    public Locator getShoppingFilterCheckbox(String categoryText) {
        return getShoppingFilterLabel(categoryText).locator("input[type='checkbox']");
    }
}