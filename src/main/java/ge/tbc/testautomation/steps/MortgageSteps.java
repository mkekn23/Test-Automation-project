package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.MortgagePage;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MortgageSteps {
    private final HeaderComponent headerComponent;
    private final MortgagePage mortgagePage;

    public MortgageSteps(Page page) {
        this.headerComponent = new HeaderComponent(page);
        this.mortgagePage = new MortgagePage(page);
    }

    public MortgageSteps navigateToMortgagePage() {
        headerComponent.formeNavigationButton.hover();
        headerComponent.mortgageSubMenuLink.first().click();

        assertThat(mortgagePage.twoPeriodsTabBtn).isVisible();
        return this;
    }

    public MortgageSteps selectTwoPeriodsTab() {
        mortgagePage.twoPeriodsTabBtn.click();

        assertThat(mortgagePage.calculatedInfoContainer).containsText("პირველი 3 წელი");
        return this;
    }

    public MortgageSteps fillLoanAmount(String amount) {
        mortgagePage.loanAmountInput.fill(amount);
        mortgagePage.loanAmountInput.press("Enter");

        assertThat(mortgagePage.loanAmountInput).hasValue(amount);
        return this;
    }

    public MortgageSteps fillLoanTermInYears(String years) {
        mortgagePage.loanTermYearsInput.fill(years);
        mortgagePage.loanTermYearsInput.press("Enter");

        assertThat(mortgagePage.loanTermYearsInput).hasValue(years);
        return this;
    }

    public MortgageSteps verifyFirstPaymentIsAbout(String expectedAmount, int tolerancePercent) {
        int expected = Integer.parseInt(expectedAmount);
        int delta = Math.max(1, expected * tolerancePercent / 100);
        String accepted = IntStream.rangeClosed(expected - delta, expected + delta)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("|"));

        assertThat(mortgagePage.firstMonthlyPayment)
                .hasText(Pattern.compile("^\\s*(" + accepted + ")(\\.\\d+)?\\s*₾?\\s*$"));
        return this;
    }
}