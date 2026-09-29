package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SecuredConsumerLoanPage extends CommonPage {
    private final Locator securedLoanDetailsButton;
    private final Locator amountInput;
    private final Locator yearsInput;
    private final Locator monthlyPaymentResult;

    public SecuredConsumerLoanPage(Page page) {
        super(page);
        this.securedLoanDetailsButton = page.locator("a[href*='secured-consumer-loan'] button").first();
        this.amountInput = page.locator("tbcx-text-input[formcontrolname='amount'] input");
        this.yearsInput = page.locator("tbcx-slider-input[formcontrolname='period'] input");
        this.monthlyPaymentResult = page.locator(".tbcx-pw-calculated-info__number--new");
    }

    public void clickSecuredLoanDetails() {
        securedLoanDetailsButton.click();
    }

    public void fillAmount(String amount) {
        amountInput.fill(amount);
    }

    public void selectCurrency(String currency) {
        page.locator(".tbcx-pw-calculator-form__currencies button:has-text('" + currency + "')").click();
    }

    public void fillYears(String years) {
        yearsInput.fill(years);
    }

    public Locator getMonthlyPaymentResult() {
        return monthlyPaymentResult;
    }
}