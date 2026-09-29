package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.MobilebankPage;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MobilebankSteps extends CommonSteps{
    private final HeaderComponent headerComponent;
    private final MobilebankPage mobilebankPage;
    public MobilebankSteps(Page page) {
        super(page);
        this.headerComponent = new HeaderComponent(page);
        this.mobilebankPage = new MobilebankPage(page);
    }


    public MobilebankSteps verifyExplainerTabContent(String tabName, String expectedTitle, String expectedImageSrcSubstring) {
        Locator tab = mobilebankPage.getTabButton(tabName);
        tab.click();
        assertThat(tab).hasClass(Pattern.compile(".*active.*"));
        tab.click();
        assertThat(mobilebankPage.getExplainerTitle())
                .containsText(expectedTitle);

        return this;
    }

    public MobilebankSteps navigateToMobilebank() {
        headerComponent.formeNavigationButton.hover();
        headerComponent.mobilebankSubMenuLink.click();
        assertThat(page).hasURL(Pattern.compile(".*mobile-bank.*"));
        return this;
    }
}
