package pages;

import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return true;
    }
}
