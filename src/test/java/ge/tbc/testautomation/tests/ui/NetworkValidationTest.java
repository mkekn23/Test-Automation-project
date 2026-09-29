package ge.tbc.testautomation.tests.ui;

import com.microsoft.playwright.Response;
import ge.tbc.testautomation.steps.AtmsAndBranchesUiSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
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
            description = "ZEPHYR-201: Open ATMs and Branches page"
    )
    @TmsLink("ZEPHYR-201")
    public void testOpenAtmsAndBranchesPage() {
        uiSteps.openPage();
    }


    @Test(
            priority = 2,
            dependsOnMethods = "testOpenAtmsAndBranchesPage",
            description = "ZEPHYR-202: Filter ATMs by city and capture network response"
    )
    @TmsLink("ZEPHYR-202")
    public void testFilterAtmsByCityAndCaptureNetwork() {
        capturedResponse = uiSteps.filterAtmsByCityWithNetworkCapture(TARGET_CITY);
    }


    @Test(
            priority = 3,
            dependsOnMethods = "testFilterAtmsByCityAndCaptureNetwork",
            description = "ZEPHYR-203: Verify captured network response data matches target city and ui mathces the results"
    )
    @TmsLink("ZEPHYR-203")
    public void testVerifyNetworkResponseData() {
        uiSteps.verifyNetworkResponseData(capturedResponse, TARGET_CITY)
                .verifyUiResultsUpdated(TARGET_CITY);
    }

}