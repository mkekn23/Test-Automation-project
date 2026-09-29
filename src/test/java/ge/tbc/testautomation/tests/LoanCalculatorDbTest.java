package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.database.models.LoanCalculatorData;
import ge.tbc.testautomation.steps.SecuredConsumerLoanSteps;
import ge.tbc.testautomation.tests.ui.BaseTest;
import ge.tbc.testautomation.utils.LoanDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Loan Calculator Automation")
@Feature("Data-Driven Testing via SQL + MyBatis")
public class LoanCalculatorDbTest extends BaseTest {

    private SecuredConsumerLoanSteps calculatorSteps;

    @BeforeClass
    public void setUpSteps() {

        calculatorSteps = new SecuredConsumerLoanSteps(page);
    }

    @Test(priority = 1, description = "KAN-T15 | navigate to Consumer Loans section")
    public void testNavigateToConsumerLoans() {
        calculatorSteps.navigateToConsumerLoans();
    }


    @Test(priority = 2, dependsOnMethods = "testNavigateToConsumerLoans", description = "Open Secured Consumer Loan details")
    public void testOpenSecuredLoanDetails() {
        calculatorSteps.openSecuredConsumerLoanDetails();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "testOpenSecuredLoanDetails",
            dataProvider = "dbLoanData",
            dataProviderClass = LoanDataProvider.class,
            description = "Calculate loan and verify results using DB records"
    )
    public void testCalculateAndVerifyLoan(LoanCalculatorData loanData) {
        calculatorSteps.selectCurrency(loanData.getCurrency());
        calculatorSteps.enterAmount(loanData.getAmount());
        calculatorSteps.enterPeriod(loanData.getPeriod());
        calculatorSteps.verifyCalculationResult(loanData);
    }
}