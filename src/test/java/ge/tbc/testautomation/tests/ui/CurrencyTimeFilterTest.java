package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.CurrencySteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.END_DAY;
import static ge.tbc.testautomation.data.Constants.START_DAY;

public class CurrencyTimeFilterTest extends BaseTest {

    private CurrencySteps currencyRatesSteps;

    @BeforeClass
    public void setUpSteps() {
        currencyRatesSteps = new CurrencySteps(page);
    }

    @Test(priority = 1, description = "Navigate to currency rates page via quick actions menu")
    public void testNavigateToCurrencyRatesPage() {
        currencyRatesSteps.navigateToCurrencyRatesViaQuickActions();
    }

    @Test(priority = 2, description = "Click USD row and open exchange rate history dialog")
    public void testOpenUsdHistoryDialog() {
        currencyRatesSteps.openUsdHistoryDialog();
    }

    @Test(priority = 3, description = "Filter rate history for date range start day to end day")
    public void testSelectDateRangeInCalendar() {
        currencyRatesSteps.selectDateRange(START_DAY, END_DAY);
    }

    @Test(priority = 4, description = "Verify filtered grid rows display dates matching selected range (26 or 27)")
    public void testVerifyFilteredHistoryDates() {
        currencyRatesSteps.verifyFilteredHistoryDatesWithWeekendFallback(START_DAY, END_DAY);
    }
}