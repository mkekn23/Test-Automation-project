package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.data.OffersLocalizationData;
import ge.tbc.testautomation.pages.OffersPage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OffersSteps {
    private final Page page;
    private final OffersPage offersPage;
    private final HeaderComponent header;

    public OffersSteps(Page page) {
        this.page = page;
        this.offersPage = new OffersPage(page);
        this.header = new HeaderComponent(page);
    }

    @Step("Navigate to Offers page via HeaderComponent and verify localization [{data.locale}]")
    public void openPageAndNavigateToOffers(OffersLocalizationData data) {
        page.navigate(data.getHomeUrl());

        // HeaderComponent-იდან იძახებს შეთავაზებების ბმულს
        header.
        var offersLink = header.getOffersLink(data.getOffersUrlPart());
        offersLink.click();

        assertThat(page).hasURL(data.getHomeUrl() + "/offers");
        assertThat(offersLink).containsText(data.getOffersHeaderText());
    }

    @Step("Click 'All Offers' button and verify page title and URL [{data.locale}]")
    public void clickAllOffersAndVerifyPage(OffersLocalizationData data) {
        var allOffersBtn = offersPage.getAllOffersButton(data.getAllOffersUrlPart());

        assertThat(allOffersBtn).containsText(data.getAllOffersBtnText());
        allOffersBtn.click();

        assertThat(page).hasURL(page.url());
        assertThat(offersPage.pageHeading).containsText(data.getOffersHeaderText());
    }

    @Step("Select 'Shopping' category filter and verify localized label [{data.locale}]")
    public void selectShoppingFilterAndVerify(OffersLocalizationData data) {
        var filterLabel = offersPage.getShoppingFilterLabel(data.getShoppingCategoryText());
        var filterCheckbox = offersPage.getShoppingFilterCheckbox(data.getShoppingCategoryText());

        assertThat(filterLabel).isVisible();
        assertThat(filterLabel).containsText(data.getShoppingCategoryText());

        filterLabel.click();

        assertThat(filterCheckbox).isChecked();
        assertThat(page).hasURL(page.url());
    }
}