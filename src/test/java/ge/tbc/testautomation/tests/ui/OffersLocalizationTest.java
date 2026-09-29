package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.data.OffersLocalizationData;
import ge.tbc.testautomation.steps.OffersSteps;
import ge.tbc.testautomation.utils.OffersDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Epic("UI Localization Testing")
@Feature("Offers Page Navigation & Category Filter Localization")
public class OffersLocalizationTest extends BaseTest {

    private final OffersLocalizationData localeData;
    private OffersSteps offersSteps;

    @Factory(dataProvider = "offersLocaleData", dataProviderClass = OffersDataProvider.class)
    public OffersLocalizationTest(OffersLocalizationData localeData) {
        this.localeData = localeData;
    }

    @BeforeClass
    public void setUpSteps() {
        offersSteps = new OffersSteps(page);
    }

    @Test(
            priority = 1,
            description = "Open home page and navigate to Offers via header"
    )
    public void testNavigateToOffersAndVerifyLocalization() {
        offersSteps.openPageAndNavigateToOffers(localeData);
    }

    @Test(
            priority = 2,
            dependsOnMethods = "testNavigateToOffersAndVerifyLocalization",
            description = "Click 'All Offers' button and verify localized page"
    )
    public void testClickAllOffersAndVerifyLocalization() {
        offersSteps.clickAllOffersAndVerifyPage(localeData);
    }

    @Test(
            priority = 3,
            dependsOnMethods = "testClickAllOffersAndVerifyLocalization",
            description = "Select Shopping category filter and verify localized label and state"
    )
    public void testFilterByShoppingAndVerifyLocalization() {
        offersSteps.selectShoppingFilterAndVerify(localeData);
    }
}