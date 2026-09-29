package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CurrencyPage extends CommonPage {
    public final Locator usdRow;

    public final Locator dialogContainer;
    public final Locator periodInput;

    public final Locator calendarContainer;
    public final Locator historyDateCells;
    public final Locator calculatorDescription;
    public final Locator swapButton;

    public CurrencyPage(Page page) {
        super(page);

        this.usdRow = page.locator(".ag-row").filter(new Locator.FilterOptions().setHasText("USD"));

        this.dialogContainer = page.locator("[role='dialog'], .tbcx-pw-dialog_container");
        this.periodInput = dialogContainer.locator("input[bsdaterangepicker], tbcx-pw-datepicker input");

        this.calendarContainer = page.locator("bs-daterangepicker-container");
        this.historyDateCells = dialogContainer.locator("div[col-id='date'] .tbcx-pw-table-cell__content__subtitle");
        this.calculatorDescription = page.locator(".exchange-rates-calculator__description");
        this.swapButton = page.locator(".currency-calculator__swap button");

    }

    public Locator getCalendarDay(String dayNumber) {
        return calendarContainer.locator("td[role='gridcell'] span:not(.is-other-month)")
                .filter(new Locator.FilterOptions().setHasText(dayNumber))
                .first();
    }


}
