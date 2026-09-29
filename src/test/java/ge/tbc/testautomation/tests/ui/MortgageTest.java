package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.MortgageSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class MortgageTest extends BaseTest {

    private MortgageSteps mortgageSteps;

    @BeforeClass
    public void setUpSteps() {
        mortgageSteps = new MortgageSteps(page);
    }

    @Test(priority = 1, description = "Navigate to mortgage page via header navigation")
    public void testNavigateToMortgagePage() {
        mortgageSteps.navigateToMortgagePage();
    }

    @Test(priority = 2, description = "Select 'Two Periods' tab in mortgage calculator")
    public void testSelectTwoPeriodsTab() {
        mortgageSteps.selectTwoPeriodsTab();
    }

    @Test(priority = 3, description = "Fill loan amount input")
    public void testFillLoanAmount() {
        mortgageSteps.fillLoanAmount(LOAN_AMOUNT);
    }

    @Test(priority = 4, description = "Fill loan term in years")
    public void testFillLoanTermInYears() {
        mortgageSteps.fillLoanTermInYears(LOAN_YEAR);
    }

    @Test(priority = 5, description = "Verify calculated payment amount is within tolerance of the expected value")
    public void testVerifyCalculatedPayment() {
        mortgageSteps.verifyFirstPaymentIsAbout(EXPECTED_AMOUNT, PAYMENT_TOLERANCE_PERCENT);
    }
}