package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.data.OffersLocalizationData;
import ge.tbc.testautomation.pages.OffersPage;
import io.qameta.allure.Step;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OffersSteps extends CommonSteps {
    private final OffersPage offersPage;
    private final HeaderComponent header;

    public OffersSteps(Page page) {
        super(page);
        this.offersPage = new OffersPage(page);
        this.header = new HeaderComponent(page);
    }

    @Step("Switch site language to [{data.locale}] using the header language switcher")
    public OffersSteps switchLanguage(OffersLocalizationData data) {
        String localePath = "/" + data.getLocale();
        if (!page.url().contains(localePath + "/") && !page.url().endsWith(localePath)) {
            header.languageSwitcher.click();
        }
        assertThat(page).hasURL(urlMatching(escape(localePath) + "(/.*)?$"));
        return this;
    }

    @Step("Open 'Offers' page from the header navigation [{data.locale}]")
    public OffersSteps navigateToOffers(OffersLocalizationData data) {
        header.formeNavigationButton.hover();
        var offersLink = header.getOffersLink(data.getOffersUrlPart());

        assertThat(offersLink).containsText(data.getOffersHeaderText());
        offersLink.click();

        assertThat(page).hasURL(urlMatching(escape(data.getOffersUrlPart()) + "$"));
        return this;
    }

    @Step("Click 'All offers' button [{data.locale}]")
    public OffersSteps openAllOffers(OffersLocalizationData data) {
        var allOffersButton = offersPage.getAllOffersButton(data.getAllOffersUrlPart(), data.getAllOffersBtnText());

        assertThat(allOffersButton).isVisible();
        allOffersButton.click();

        assertThat(page).hasURL(urlMatching(escape(data.getAllOffersUrlPart()) + ".*"));
        assertThat(offersPage.pageHeading).hasText(data.getOffersHeaderText());
        return this;
    }

    @Step("Filter offers by category '{data.shoppingCategoryText}' [{data.locale}]")
    public OffersSteps filterByCategory(OffersLocalizationData data) {
        var categoryFilter = offersPage.getCategoryFilter(data.getShoppingCategoryText());

        assertThat(categoryFilter).isVisible();
        assertThat(categoryFilter).hasText(data.getShoppingCategoryText());

        categoryFilter.click();

        assertThat(offersPage.getCategoryFilterCheckbox(data.getShoppingCategoryText())).isChecked();
        assertThat(page).hasURL(urlMatching(escape(data.getShoppingParamPart()) + ".*"));
        return this;
    }

    private static String escape(String text) {
        return text.replaceAll("[\\\\^$.|?*+()\\[\\]{}]", "\\\\$0");
    }

    private static Pattern urlMatching(String regex) {
        return Pattern.compile(".*" + regex);
    }
}
