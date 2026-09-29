package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static ge.tbc.testautomation.data.Constants.*;

public class TechschoolPage extends  CommonPage{
    public final Locator techcoursesSection;
    public final Locator coursesSection;
    public final Locator productDesignCourseCard;
    public final Locator registerButton;
    public final Locator registrationForm;
    public final Locator productDesignTitle;

    public TechschoolPage(Page page) {
        super(page);
        this.techcoursesSection =page.locator("text=\"" + SECTION_TITLE_TECHCOURSES + "\"");
        this.coursesSection = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(SECTION_TITLE_COURSES).setExact(true));
        this.productDesignCourseCard = page.locator("h3.tbcx-pw-card__title")
                .filter(new Locator.FilterOptions().setHasText(COURSE_NAME));
        this.productDesignTitle = page.locator("h1").filter(new Locator.FilterOptions().setHasText("პროდუქტის დიზაინი"));
        this.registerButton = page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName(BUTTON_REGISTER))
                .or(page.locator("text=\"" + BUTTON_REGISTER + "\""));
        this.registrationForm = page.locator("form, .registration-form, #registration-form");
    }
}