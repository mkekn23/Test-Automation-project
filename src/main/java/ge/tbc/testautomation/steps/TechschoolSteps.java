package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.TechschoolPage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TechschoolSteps extends CommonSteps{
    private final HeaderComponent headerComponent;
    private final TechschoolPage techschoolPage;

    public TechschoolSteps(Page page) {
        super(page);
        this.headerComponent = new HeaderComponent(page);
        this.techschoolPage = new TechschoolPage(page);
    }

    public TechschoolSteps navigateToTechschoolPage() {
        headerComponent.tbcNavigationButton.hover();
        headerComponent.techschoolSubMenuLink.click();
        assertThat(page).hasURL(Pattern.compile(".*tech-school.*"));
        return this;
    }

    public void navigateToCourses() {
        techschoolPage.techcoursesSection.click();
        assertThat(techschoolPage.coursesSection).isVisible();
    }

    public void openProductDesignCourse() {
        techschoolPage.productDesignCourseCard.click();
        assertThat(techschoolPage.productDesignTitle).isVisible();
    }

    public void navigateToRegistration() {
        Page newTab = page.waitForPopup(() -> {
            techschoolPage.registerButton.click();
        });

        newTab.waitForLoadState();

        assertThat(newTab).hasURL(Pattern.compile(".*(register|forms|google).*"));
    }
}