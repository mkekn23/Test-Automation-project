package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.locations.AtmsAndBranchesApi;
import ge.tbc.testautomation.api.client.models.response.BranchResponse;
import ge.tbc.testautomation.api.client.models.request.BranchSearchRequest;
import ge.tbc.testautomation.api.client.models.response.WorkHour;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static ge.tbc.testautomation.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AtmsAndBranchesSteps {

    private final AtmsAndBranchesApi atmsAndBranchesApi = new AtmsAndBranchesApi();

    @Step("Search branches using filter: {filterType} and keyword: '{keyword}'")
    public Response searchBranches(String filterType, String keyword) {
        BranchSearchRequest request = BranchSearchRequest.builder()
                .filter(List.of(filterType))
                .locale(GEORGIAN_LOCALE)
                .keyword(keyword)
                .myLocation(new BranchSearchRequest.Location(0, 0))
                .build();

        return atmsAndBranchesApi.getBranches(request);
    }

    @Step("Send raw/invalid request payload to locations endpoint")
    public Response searchBranchesWithRawPayload(Object rawPayload) {
        return atmsAndBranchesApi.getBranches(rawPayload);
    }

    @Step("Verify status code is {expectedStatusCode}")
    public AtmsAndBranchesSteps verifyStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
        return this;
    }

    @Step("Deserialize response and verify branch list contains keyword '{expectedKeyword}'")
    public List<BranchResponse> verifyBranchListAndGet(Response response, String expectedKeyword) {
        List<BranchResponse> branches = response.jsonPath().getList("", BranchResponse.class);

        assertThat("Branch list should not be empty", branches, is(not(empty())));
        assertThat(branches.get(0).getType(), equalTo(BRANCH_TYPE));
        assertThat(branches.get(0).getAddress(), containsString(expectedKeyword));

        return branches;
    }

    @Step("Verify nested work hours data structure for branch")
    public AtmsAndBranchesSteps verifyWorkHours(BranchResponse branch, String expectedWeekDay) {
        List<WorkHour> workHours = branch.getWorkHours();

        assertThat("Work hours list should not be empty", workHours, is(not(empty())));

        WorkHour firstWorkHour = workHours.get(0);
        assertThat(firstWorkHour.getStartTime(), is(notNullValue()));
        assertThat(firstWorkHour.getEndTime(), is(notNullValue()));
        assertThat(firstWorkHour.getWeekDay(), equalTo(expectedWeekDay));

        return this;
    }


}