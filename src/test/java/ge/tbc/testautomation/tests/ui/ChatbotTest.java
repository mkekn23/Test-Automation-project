package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.ChatbotSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.CHATBOT_MESSAGE;

public class ChatbotTest extends BaseTest {

    private ChatbotSteps chatbotSteps;

    @BeforeClass
    public void setUpSteps() {
        chatbotSteps = new ChatbotSteps(page);
    }

    @Test(priority = 1, description = "Open chatbot dialog from quick actions menu")
    public void testOpenChatbot() {
        chatbotSteps.openChatbotViaQuickActions();
    }

    @Test(priority = 2, description = "Send user message and verify it appears in chat")
    public void testSendMessage() {
        chatbotSteps.sendMessage(CHATBOT_MESSAGE)
                .verifyUserMessageSent(CHATBOT_MESSAGE);
    }

    @Test(priority = 3, description = "Verify automated bot response is received in chat")
    public void testVerifyBotResponse() {
        chatbotSteps.verifyAnyBotResponseReceived();
    }
}