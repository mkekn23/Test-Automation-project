package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.QuickActionsComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class QuickActionsSteps extends CommonSteps {
    private final QuickActionsComponent quickActionsComponent;

    public QuickActionsSteps(Page page) {
        super(page);
        this.quickActionsComponent = new QuickActionsComponent(page);
    }

    public QuickActionsSteps openQuickActionsMenu() {
        quickActionsComponent.kebabMenuButton.click();

        assertThat(quickActionsComponent.chatButton).isVisible();
        assertThat(quickActionsComponent.currencyRatesLink).isVisible();
        return this;
    }
}