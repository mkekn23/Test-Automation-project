package ge.tbc.testautomation.api.client.currency;

import ge.tbc.testautomation.api.client.models.response.ExchangeRateResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ExchangeCurrencyApi extends BaseApi {

    public Response getExchangeRateRaw(String iso1, String iso2) {
        return given()
                .spec(REQ_SPEC)
                .queryParam("Iso1", iso1)
                .queryParam("Iso2", iso2)
                .when()
                .get("/exchangeRates/getExchangeRate");
    }

    public ExchangeRateResponse getExchangeRate(String iso1, String iso2) {
        return getExchangeRateRaw(iso1, iso2)
                .then()
                .statusCode(200)
                .extract()
                .as(ExchangeRateResponse.class);
    }
}