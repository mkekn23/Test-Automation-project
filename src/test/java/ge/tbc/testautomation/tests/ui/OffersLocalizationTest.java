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
            description = "KAN-T17 | Switch the site language via the header language switcher"
    )
    public void testSwitchLanguage() {
        offersSteps.switchLanguage(localeData);
    }

    @Test(
            priority = 2,
            dependsOnMethods = "testSwitchLanguage",
            description = "Navigate to Offers page via the header and verify localized heading"
    )
    public void testNavigateToOffersViaHeader() {
        offersSteps.navigateToOffers(localeData);
    }

    @Test(
            priority = 3,
            dependsOnMethods = "testNavigateToOffersViaHeader",
            description = "Click 'All offers' button and verify localized page"
    )
    public void testOpenAllOffers() {
        offersSteps.openAllOffers(localeData);
    }

    @Test(
            priority = 4,
            dependsOnMethods = "testOpenAllOffers",
            description = "Filter by Shopping category and verify localized label, checked state and URL"
    )
    public void testFilterByCategory() {
        offersSteps.filterByCategory(localeData);
    }
}