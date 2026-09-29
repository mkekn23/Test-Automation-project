package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

@Getter
public class SecuredConsumerLoanPage extends CommonPage {
    private final Locator securedLoanDetailsButton;
    private final Locator amountInput;
    private final Locator yearsInput;
    private final Locator monthlyPaymentResult;
    private final Locator calculationSummary;

    public SecuredConsumerLoanPage(Page page) {
        super(page);
        this.securedLoanDetailsButton = page.locator("a[href*='secured-consumer-loan'] button").first();
        this.amountInput = page.locator("tbcx-text-input[formcontrolname='amount'] input");
        this.yearsInput = page.locator("tbcx-slider-input[formcontrolname='period'] input");
        this.monthlyPaymentResult = page.locator(".tbcx-pw-calculated-info__number--new");
        this.calculationSummary = page.locator("div.tbcx-pw-calculated-info").first();
    }

    public void clickSecuredLoanDetails() {
        securedLoanDetailsButton.click();
    }

    public void fillAmount(String amount) {
        amountInput.fill(amount);
        amountInput.press("Tab");
    }

    public void selectCurrency(String currency) {
        getCurrencyButton(currency).click();
    }

    public void fillYears(String years) {
        yearsInput.fill(years);
    }

    public Locator getCurrencyButton(String currency) {
        return page.locator(".tbcx-pw-calculator-form__currencies button:has-text('" + currency + "')");
    }

}
