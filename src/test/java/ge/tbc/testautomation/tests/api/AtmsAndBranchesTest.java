package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.api.client.models.response.BranchResponse;
import ge.tbc.testautomation.steps.AtmsAndBranchesSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static ge.tbc.testautomation.data.Constants.*;

@Epic("API Testing")
@Feature("ATMs and Branches")
public class AtmsAndBranchesTest {

    private AtmsAndBranchesSteps branchSteps;

    @BeforeClass
    public void setUp() {
        branchSteps = new AtmsAndBranchesSteps();
    }

    @Test(description = "KAN-T13 | Happy Path: Search branches by keyword and validate nested work hours")
    public void testSearchBranchesHappyPath() {
        Response response = branchSteps.searchBranches(FILTER_BRANCH, DEFAULT_SEARCH_KEYWORD);
        branchSteps.verifyStatusCode(response, 200);
        List<BranchResponse> branches = branchSteps.verifyBranchListAndGet(response, DEFAULT_SEARCH_KEYWORD);
        branchSteps.verifyWorkHours(branches.get(0), WEEKDAY_MON_FRI);
    }

    @Test(description = "KAN-T14 | Negative Path: Send invalid request payload type")
    public void testSearchBranchesNegativePath() {
        String invalidPayload = "{\"filter\": \"INVALID_TYPE\"}";

        Response response = branchSteps.searchBranchesWithRawPayload(invalidPayload);

        branchSteps.verifyStatusCode(response, 400);
    }
}