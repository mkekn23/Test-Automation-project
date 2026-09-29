package ge.tbc.testautomation.tests.ui;

import ge.tbc.testautomation.steps.TechschoolSteps;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TechschoolTest extends BaseTest {
    private TechschoolSteps techschoolSteps;

    @BeforeClass
    public void setUpSteps() {
        techschoolSteps = new TechschoolSteps(page);
    }

    @Test(priority = 1, description = "Step 1: Navigate to homepage and select 'Techschool' from 'TBC' header navigation")
    @Description("Navigates to the homepage, hovers over the TBC navigation item, and opens the Techschool page.")
    public void navigateToTechschoolPage() {
        techschoolSteps.navigateToTechschoolPage();
    }

    @Test(priority = 2, description = "Step 2: Scroll to and select 'Techschool Courses' section")
    @Description("Navigates to the Techschool courses section and verifies the courses list loads successfully.")
    public void navigateToCoursesSection() {
        techschoolSteps.navigateToCourses();
    }

    @Test(priority = 3, description = "Step 3: Open 'Product Design' course details page")
    @Description("Selects the Product Design course card and verifies the course detail section displays correctly.")
    public void openProductDesignCourse() {
        techschoolSteps.openProductDesignCourse();
    }

    @Test(priority = 4, description = "Step 4: Click 'Register' button and verify registration page display")
    @Description("Clicks the registration button and confirms redirection to the registration form page.")
    public void navigateToRegistrationPage() {
        techschoolSteps.navigateToRegistration();
    }
}