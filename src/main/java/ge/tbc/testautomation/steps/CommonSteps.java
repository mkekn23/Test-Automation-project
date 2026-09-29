package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;

public abstract class CommonSteps {
    protected final Page page;

    public CommonSteps(Page page) {
        this.page = page;
    }
}