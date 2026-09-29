package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.SecuredConsumerLoanPage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SecuredConsumerLoanSteps {
    private final Page page;
    private final HeaderComponent headerComponent;
    private final SecuredConsumerLoanPage loanPage;

    public SecuredConsumerLoanSteps(Page page) {
        this.page = page;
        this.headerComponent = new HeaderComponent(page);
        this.loanPage = new SecuredConsumerLoanPage(page);
    }

    @Step("Navigate to Consumer Loans section")
    public SecuredConsumerLoanSteps navigateToConsumerLoans() {
        headerComponent.formeNavigationButton.hover();
        headerComponent.consumerLoanMenuLink.click();
        return this;
    }

    @Step("Open Secured Consumer Loan details")
    public SecuredConsumerLoanSteps openSecuredConsumerLoanDetails() {
        loanPage.clickSecuredLoanDetails();
        return this;
    }

    @Step("Enter loan amount: {amount}")
    public SecuredConsumerLoanSteps enterAmount(String amount) {
        loanPage.fillAmount(amount);
        return this;
    }

    @Step("Select currency: {currency}")
    public SecuredConsumerLoanSteps selectCurrency(String currency) {
        loanPage.selectCurrency(currency);
        return this;
    }

    @Step("Enter loan period in years: {period}")
    public SecuredConsumerLoanSteps enterPeriod(String period) {
        loanPage.fillYears(period);
        return this;
    }

    @Step(" Verify calculation result is visible")
    public SecuredConsumerLoanSteps verifyCalculationResult() {
        assertThat(loanPage.getMonthlyPaymentResult()).isVisible();
        return this;
    }
}