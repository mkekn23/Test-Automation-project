package ge.tbc.testautomation.components;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CommonPage;

public class ChatbotComponent extends CommonPage {
    public final FrameLocator chatFrame;

    public final Locator chatWindow;
    public final Locator messageInput;
    public final Locator sendButton;
    public final Locator allChatMessageBubbles;

    public ChatbotComponent(Page page) {
        super(page);

        this.chatFrame = page.frameLocator("iframe[title='Messaging window']");

        this.chatWindow = chatFrame.locator("div[role='dialog']");
        this.messageInput = chatFrame.locator("#composer-input");
        this.sendButton = chatFrame.locator("button[aria-label='Send message'], button[title='Send message']");
        this.allChatMessageBubbles = chatFrame.locator("div[role='log'] span[class*='sc-gn0wg3']");

    }

    public Locator getChatMessageByText(String text) {
        return chatFrame.locator("span").filter(new Locator.FilterOptions().setHasText(text));
    }
}