package ge.tbc.testautomation.api.client.locations;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AtmsAndBranchesApi extends BaseApi {

    private static final String ATMS_AND_BRANCHES_ENDPOINT = "/atmsAndBranches/list";

    public Response getBranches(Object requestBody) {
        return given()
                .spec(REQ_SPEC)
                .body(requestBody)
                .when()
                .post(ATMS_AND_BRANCHES_ENDPOINT);
    }
}