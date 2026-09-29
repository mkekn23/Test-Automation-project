package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.MortgagePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MortgageSteps {
    private final HeaderComponent headerComponent;
    private final MortgagePage mortgagePage;

    public MortgageSteps(Page page) {
        this.headerComponent = new HeaderComponent(page);
        this.mortgagePage = new MortgagePage(page);
    }

    // 1. ჰედერის მენიუდან იპოთეკური სესხის გვერდზე გადასვლა
    public MortgageSteps navigateToMortgagePage() {
        // თუ მეგა-მენიუ ჰოვერზე ან კლიკზე იხსნება:
        headerComponent.formeNavigationButton.hover();
        headerComponent.mortgageSubMenuLink.first().click();

        assertThat(mortgagePage.twoPeriodsTabBtn).isVisible();
        return this;
    }

    // 2. "ორი პერიოდი" ჩანართის არჩევა
    public MortgageSteps selectTwoPeriodsTab() {
        mortgagePage.twoPeriodsTabBtn.click();
        return this;
    }

    // 3. თანხის შეყვანა
    public MortgageSteps fillLoanAmount(String amount) {
        mortgagePage.loanAmountInput.fill(amount);
        mortgagePage.loanAmountInput.press("Enter");
        return this;
    }

    // 4. ვადის (წლების) შეყვანა
    public MortgageSteps fillLoanTermInYears(String years) {
        mortgagePage.loanTermYearsInput.fill(years);
        mortgagePage.loanTermYearsInput.press("Enter");
        return this;
    }

    // 5. დათვლილი თანხის გადამოწმება
    public MortgageSteps verifyCalculatedPaymentContains(String expectedAmountText) {
        assertThat(mortgagePage.getCalculatedAmountLocator(expectedAmountText).first()).isVisible();
        return this;
    }
}