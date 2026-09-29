package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

public class QuickActionsComponent extends CommonPage {

    private final Locator quickActionsContainer;
    public final Locator kebabMenuButton;
    public final Locator chatButton;
    public final Locator currencyRatesLink;

    public QuickActionsComponent(Page page) {
        super(page);

        this.quickActionsContainer = page.locator("app-quick-actions");
        this.kebabMenuButton = quickActionsContainer.locator("app-quick-action-button")
                .filter(new Locator.FilterOptions().setHasText("kebab"));
        this.chatButton = quickActionsContainer.locator("app-quick-action-button")
                .filter(new Locator.FilterOptions().setHasText("chat-dots"));

        this.currencyRatesLink = quickActionsContainer.locator("a[href*='valutis-kursi']");
    }
}