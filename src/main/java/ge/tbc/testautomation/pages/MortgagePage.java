package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MortgagePage extends CommonPage {
    public final Locator twoPeriodsTabBtn;

    public final Locator loanAmountInput;
    public final Locator loanTermYearsInput;

    public final Locator calculatedInfoContainer;

    public MortgagePage(Page page) {
        super(page);

        this.twoPeriodsTabBtn = page.locator("button.tbcx-pw-tab-segment__item")
                .filter(new Locator.FilterOptions().setHasText("ორი პერიოდი"));

        this.loanAmountInput = page.locator("tbcx-text-input input").first();

        this.loanTermYearsInput = page.locator("tbcx-slider-input input[type='number']");

        this.calculatedInfoContainer = page.locator("div.tbcx-pw-calculated-info");
    }

    public Locator getCalculatedAmountLocator(String amountText) {
        return calculatedInfoContainer.locator("div")
                .filter(new Locator.FilterOptions().setHasText(amountText));
    }
}