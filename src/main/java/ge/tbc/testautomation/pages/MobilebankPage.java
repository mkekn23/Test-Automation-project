package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

public class MobilebankPage extends CommonPage {
    private final Locator explainerSection;

    public MobilebankPage(Page page) {
        super(page);
        this.explainerSection = page.locator("tbcx-pw-feature-explainer-section, [class*='explainer-section']");
    }

    public Locator getTabButton(String tabText) {
        return explainerSection.locator("button.tbcx-pw-feature-explainer-section-switcher")
                .filter(new Locator.FilterOptions().setHasText(tabText));
    }


    public Locator getExplainerTitle() {
        return explainerSection.locator("h2.tbcx-pw-explainer-section__body-content-text-title");
    }

    public Locator getExplainerImage() {
        return explainerSection.locator("img").first();
    }
}