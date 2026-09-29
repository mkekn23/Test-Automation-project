package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.client.currency.ExchangeCurrencyApi;
import ge.tbc.testautomation.api.client.models.response.ExchangeRateResponse;
import ge.tbc.testautomation.steps.CurrencySteps;
import ge.tbc.testautomation.tests.ui.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("API to UI Consistency")
@Feature("Exchange Rates")
public class ExchangeRateConsistencyTest extends BaseTest {

    private ExchangeCurrencyApi exchangeCurrencyApi;
    private CurrencySteps currencySteps;
    private ExchangeRateResponse apiResponse;

    @BeforeClass
    public void setUpApi() {
        exchangeCurrencyApi = new ExchangeCurrencyApi();
    }

    @BeforeMethod
    public void setUpSteps() {
        currencySteps = new CurrencySteps(page);
    }

    @Test(priority = 1, description = "Fetch GEL to GBP exchange rates from API")
    public void testFetchApiExchangeRates() {
        apiResponse = exchangeCurrencyApi.getExchangeRate("GEL", "GBP");
    }

    @Test(priority = 2, description = "Navigate to calculator and validate initial buy rate")
    public void testValidateBuyRate() {
        currencySteps.navigateToGelToGbpCalculator()
                .verifyInitialBuyRate(apiResponse.getBuyRate());
    }

    @Test(priority = 3, description = "Click swap button and validate swapped sell rate")
    public void testValidateSellRateAfterSwap() {
        currencySteps.clickSwapButton()
                .verifySwappedSellRate(apiResponse.getSellRate());
    }
}