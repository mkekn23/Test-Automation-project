package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.QuickActionsComponent;
import ge.tbc.testautomation.pages.CurrencyPage;
import io.qameta.allure.Step;

import java.util.Locale;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.data.Constants.GEL_TO_GBP;

public class CurrencySteps extends CommonSteps{
    private final QuickActionsComponent quickActionsComponent;
    private final CurrencyPage currencyRatesPage;

    public CurrencySteps(Page page) {
        super(page);
        this.quickActionsComponent = new QuickActionsComponent(page);
        this.currencyRatesPage = new CurrencyPage(page);
    }

    public CurrencySteps navigateToCurrencyRatesViaQuickActions() {
        quickActionsComponent.kebabMenuButton.scrollIntoViewIfNeeded();
        quickActionsComponent.kebabMenuButton.click();

        quickActionsComponent.currencyRatesLink.click();

        assertThat(page).hasURL(Pattern.compile(".*valutis-kursi.*"));
        return this;
    }

    public CurrencySteps openUsdHistoryDialog() {
        currencyRatesPage.usdRow.scrollIntoViewIfNeeded();
        currencyRatesPage.usdRow.click();

        assertThat(currencyRatesPage.dialogContainer).isVisible();
        return this;
    }

    public CurrencySteps selectDateRange(String startDay, String endDay) {
        currencyRatesPage.periodInput.click();

        assertThat(currencyRatesPage.calendarContainer).isVisible();

        currencyRatesPage.getCalendarDay(startDay).click();
        currencyRatesPage.getCalendarDay(endDay).click();

        return this;
    }

    public CurrencySteps verifyFilteredHistoryDatesWithWeekendFallback(String startDay, String endDay) {
        assertThat(currencyRatesPage.historyDateCells.first()).isVisible();

        int startDayNum = Integer.parseInt(startDay);
        int prevDayNum = startDayNum - 1; // 26 -> 25

        Pattern validDatePattern = Pattern.compile(".*(" + prevDayNum + "|" + startDay + "|" + endDay + ").*");

        int rowCount = currencyRatesPage.historyDateCells.count();
        for (int i = 0; i < rowCount; i++) {
            assertThat(currencyRatesPage.historyDateCells.nth(i)).hasText(validDatePattern);
        }

        return this;
    }

    @Step("Navigate to GEL to GBP currency calculator page")
    public CurrencySteps navigateToGelToGbpCalculator() {
            currencyRatesPage.page.navigate(GEL_TO_GBP);
        return this;
    }

    @Step("Verify initial calculator buy rate: {buyRate}")
    public CurrencySteps verifyInitialBuyRate(double buyRate) {
        String formattedBuyRate = String.format(Locale.US, "%.4f", buyRate);
        String expectedText = "1 GEL = " + formattedBuyRate + " GBP";
        assertThat(currencyRatesPage.calculatorDescription).hasText(expectedText);
        return this;
    }

    @Step("Click currency swap button")
    public CurrencySteps clickSwapButton() {
        currencyRatesPage.swapButton.click();
        return this;
    }

    @Step("Verify swapped calculator sell rate (1/sellRate): {sellRate}")
    public CurrencySteps verifySwappedSellRate(double sellRate) {
        double calculatedRate = 1 / sellRate;
        String formattedSellRate = String.format(Locale.US, "%.3f", calculatedRate);
        String expectedText = "1 GBP = " + formattedSellRate + " GEL";
        assertThat(currencyRatesPage.calculatorDescription).hasText(expectedText);
        return this;
    }
}