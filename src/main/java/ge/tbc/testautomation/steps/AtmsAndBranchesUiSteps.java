package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import ge.tbc.testautomation.pages.AtmsAndBranchesPage;
import io.qameta.allure.Step;

import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.data.Constants.*;
import static org.testng.Assert.*;

public class AtmsAndBranchesUiSteps extends CommonSteps {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final AtmsAndBranchesPage pageObject;

    private int atmsInCityFromApi;

    public AtmsAndBranchesUiSteps(Page page) {
        super(page);
        this.pageObject = new AtmsAndBranchesPage(page);
    }

    @Step("Open ATMs and Branches UI page")
    public AtmsAndBranchesUiSteps openPage() {
        pageObject.navigateToPage(TBC_UI_BASE_URL);

        assertThat(page).hasURL(Pattern.compile(".*atms&branches.*"));
        assertThat(pageObject.getAtmTabButton()).isVisible();
        return this;
    }

    @Step("Select 'ATMs' tab and filter by city '{cityName}' while capturing network response")
    public Response filterAtmsByCityWithNetworkCapture(String cityName) {
        pageObject.clickAtmTab();
        assertThat(pageObject.getAtmTabButton()).hasClass(Pattern.compile(".*active.*"));

        Response response = pageObject.getPage().waitForResponse(
                res -> res.url().contains(LOCATIONS_API_ENDPOINT) && res.status() == 200,
                () -> {
                    pageObject.clickCityDropdown();
                    pageObject.selectCityFromDropdown(cityName);
                }
        );

        assertThat(pageObject.getCityDropdown()).containsText(cityName);
        return response;
    }

    @Step("Verify Network response parameters (Status, Method, Payload and Response body)")
    public AtmsAndBranchesUiSteps verifyNetworkResponseData(Response response, String expectedCity) {
        assertEquals(response.status(), 200, "Status code should be 200");
        assertEquals(response.request().method(), HTTP_POST, "HTTP method should be POST");
        assertTrue(response.url().contains(LOCATIONS_API_ENDPOINT), "URL should match location endpoint");

        JsonNode payload = parseJson(response.request().postData(), "Request payload");
        assertTrue(StreamSupport.stream(payload.path("filter").spliterator(), false)
                        .anyMatch(f -> ATM_FILTER_TYPE.equals(f.asText())),
                "Request filter should contain " + ATM_FILTER_TYPE + " but was: " + payload.path("filter"));
        assertEquals(payload.path("locale").asText(), GEORGIAN_LOCALE, "Request locale");

        JsonNode locations = parseJson(response.text(), "Response body");
        assertTrue(locations.isArray() && locations.size() > 0, "Response should be a non-empty list of locations");
        assertTrue(StreamSupport.stream(locations.spliterator(), false)
                        .allMatch(l -> ATM_FILTER_TYPE.equals(l.path("type").asText())),
                "Every returned location should be of type " + ATM_FILTER_TYPE);

        atmsInCityFromApi = (int) StreamSupport.stream(locations.spliterator(), false)
                .filter(l -> expectedCity.equals(l.path("regionName").asText().trim()))
                .count();
        assertTrue(atmsInCityFromApi > 0, "Response should contain ATMs located in " + expectedCity);

        return this;
    }

    @Step("Verify UI content updated correctly after API response")
    public AtmsAndBranchesUiSteps verifyUiResultsUpdated(String expectedCity) {
        assertTrue(atmsInCityFromApi > 0, "verifyNetworkResponseData must run before the UI check");

        Locator cards = pageObject.getLocationCards();
        assertThat(cards.first()).isVisible();

        assertThat(cards).hasCount(atmsInCityFromApi);
        assertThat(cards.filter(new Locator.FilterOptions().setHasText(expectedCity))).hasCount(atmsInCityFromApi);
        assertThat(cards.filter(new Locator.FilterOptions().setHasText(ATM_FILTER_TYPE))).hasCount(atmsInCityFromApi);

        return this;
    }

    private static JsonNode parseJson(String json, String what) {
        assertNotNull(json, what + " should not be null");
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new AssertionError(what + " should be valid JSON", e);
        }
    }
}
