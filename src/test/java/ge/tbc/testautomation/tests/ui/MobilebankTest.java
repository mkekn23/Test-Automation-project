package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.MobilebankSteps;
import ge.tbc.testautomation.steps.TechschoolSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class MobilebankTest extends BaseTest{
    private MobilebankSteps mobilebankSteps;

    @BeforeClass
    public void setUpSteps() {
        mobilebankSteps = new MobilebankSteps(page);
    }

    @Test(priority = 1, description = "Verify all feature explainer tabs update content and image properly")
    public void testNavigationToMobilebankPage() {
        mobilebankSteps
                .navigateToMobilebank();
    }

    @Test(priority = 2, description = "Verify all feature explainer tabs update content and image properly")
    public void testExplainerTabsChangeContentAndImage() {
        mobilebankSteps
                .verifyExplainerTabContent(MAIN_TAB, MAIN_TAB, "main")
                .verifyExplainerTabContent(TRANSACTIONS_TAB, TRANSACTIONS_NEW_TAB, "transfer")
                .verifyExplainerTabContent(PAYMENTS_TAB, PAYMENTS_NEW_TAB, "payment")
                .verifyExplainerTabContent(NEWPRODUCTS_TAB, NEWPRODUCTS_TAB, "product");
    }
}
