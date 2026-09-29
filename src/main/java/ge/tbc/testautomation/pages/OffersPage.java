package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OffersPage extends CommonPage {
    public final Locator pageHeading;
    public final Locator categoryFilters;

    public OffersPage(Page page) {
        super(page);

        this.pageHeading = page.locator("h1.marketing__header__title");
        this.categoryFilters = page.locator("button.filter-item");
    }

    public Locator getAllOffersButton(String allOffersUrlPart, String buttonText) {
        return page.locator("a[href*='" + allOffersUrlPart + "?']")
                .filter(new Locator.FilterOptions().setHasText(buttonText))
                .first();
    }

    public Locator getCategoryFilter(String categoryText) {
        return categoryFilters.filter(new Locator.FilterOptions().setHasText(categoryText));
    }

    public Locator getCategoryFilterCheckbox(String categoryText) {
        return getCategoryFilter(categoryText).locator("input.filter-item__input");
    }
}
