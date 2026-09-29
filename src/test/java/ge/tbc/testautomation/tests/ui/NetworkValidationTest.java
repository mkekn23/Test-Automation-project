package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.Response;
import ge.tbc.testautomation.steps.AtmsAndBranchesUiSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.TARGET_CITY;

@Epic("UI Network Validation")
@Feature("ATMs Search by City")
public class NetworkValidationTest extends BaseTest {
    private AtmsAndBranchesUiSteps uiSteps;
    private Response capturedResponse;
    @BeforeClass
    public void setUpSteps() {
        uiSteps = new AtmsAndBranchesUiSteps(page);
    }

    @Test(
            priority = 1,
            description = ":KANT-16 Open ATMs and Branches page"
    )
    public void testOpenAtmsAndBranchesPage() {
        uiSteps.openPage();
    }


    @Test(
            priority = 2,
            dependsOnMethods = "testOpenAtmsAndBranchesPage",
            description = "Filter ATMs by city and capture network response"
    )
    public void testFilterAtmsByCityAndCaptureNetwork() {
        capturedResponse = uiSteps.filterAtmsByCityWithNetworkCapture(TARGET_CITY);
    }


    @Test(
            priority = 3,
            dependsOnMethods = "testFilterAtmsByCityAndCaptureNetwork",
            description = "Verify captured network response data matches target city and ui mathces the results"
    )
    public void testVerifyNetworkResponseData() {
        uiSteps.verifyNetworkResponseData(capturedResponse, TARGET_CITY)
                .verifyUiResultsUpdated(TARGET_CITY);
    }

}