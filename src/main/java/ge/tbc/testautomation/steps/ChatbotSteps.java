package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.ChatbotComponent;
import ge.tbc.testautomation.components.QuickActionsComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChatbotSteps {
    private final QuickActionsComponent quickActionsComponent;
    private final ChatbotComponent chatbotComponent;

    public ChatbotSteps(Page page) {
        this.quickActionsComponent = new QuickActionsComponent(page);
        this.chatbotComponent = new ChatbotComponent(page);
    }

    public ChatbotSteps openChatbotViaQuickActions() {
        quickActionsComponent.kebabMenuButton.scrollIntoViewIfNeeded();
        quickActionsComponent.kebabMenuButton.click();
        quickActionsComponent.chatButton.click();

        assertThat(chatbotComponent.chatWindow).isVisible();
        return this;
    }


    public ChatbotSteps sendMessage(String messageText) {
        chatbotComponent.messageInput.fill(messageText);
        chatbotComponent.sendButton.click();
        return this;
    }

    public ChatbotSteps verifyUserMessageSent(String messageText) {
        assertThat(chatbotComponent.getChatMessageByText(messageText).last()).isVisible();
        return this;
    }

    public ChatbotSteps verifyAnyBotResponseReceived() {
        Locator lastMessage = chatbotComponent.allChatMessageBubbles.last();
        assertThat(lastMessage).isVisible();
        assertThat(lastMessage).not().hasText("");

        return this;
    }
}