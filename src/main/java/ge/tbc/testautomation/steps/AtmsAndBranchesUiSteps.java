package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import ge.tbc.testautomation.pages.AtmsAndBranchesPage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.data.Constants.*;
import static org.testng.Assert.*;

public class AtmsAndBranchesUiSteps extends CommonSteps {

    private final AtmsAndBranchesPage pageObject;

    public AtmsAndBranchesUiSteps(Page page) {
        super(page);
        this.pageObject = new AtmsAndBranchesPage(page);
    }

    @Step("Open ATMs and Branches UI page")
    public AtmsAndBranchesUiSteps openPage() {
        pageObject.navigateToPage(TBC_UI_BASE_URL);
        return this;
    }

    @Step("Select 'ATMs' tab and filter by city '{cityName}' while capturing network response")
    public Response filterAtmsByCityWithNetworkCapture(String cityName) {
        pageObject.clickAtmTab();

        return pageObject.getPage().waitForResponse(
                res -> res.url().contains(LOCATIONS_API_ENDPOINT) && res.status() == 200,
                () -> {
                    pageObject.clickCityDropdown();
                    pageObject.selectCityFromDropdown(cityName);
                }
        );
    }

    @Step("Verify Network response parameters (Status, Method, Payload and Response body)")
    public AtmsAndBranchesUiSteps verifyNetworkResponseData(Response response, String expectedCity) {
        assertEquals(response.status(), 200, "Status code should be 200");
        assertEquals(response.request().method(), HTTP_POST, "HTTP method should be POST");
        assertTrue(response.url().contains(LOCATIONS_API_ENDPOINT), "URL should match location endpoint");

        String requestPayload = response.request().postData();
        assertNotNull(requestPayload, "Request payload should not be null");

        String responseBody = response.text();
        assertTrue(responseBody.contains(ATM_FILTER_TYPE) || responseBody.contains(expectedCity),
                "Response body should contain filtered ATM/City data");

        return this;
    }

    @Step("Verify UI content updated correctly after API response")
    public AtmsAndBranchesUiSteps verifyUiResultsUpdated(String expectedCity) {
        Locator firstCard = pageObject.getLocationCards().first();
        assertThat(firstCard).isVisible();

        assertThat(firstCard).containsText(expectedCity);

        return this;
    }
}