package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.BaseTest;

import static tests.BaseTest.driver;

@Log4j2
public class LoginPage extends BasePage {

    public static final By SIGN_IN_BUTTON = By.xpath("//a[@class='btn btn-primary']");
    public static final String INPUT_LOCATOR = "//input[@id='%s']";
    public static final String CHECKBOX_LOCATOR = "//input[@class='form-check-input']";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void pageOpen() {
        driver.get(BaseTest.BASE_URL);
    }

    @Override
    public boolean isPageOpened() {
        return isElementExist(SIGN_IN_BUTTON);
    }

    @Step("Setting value in the field")
    public void setValue(String fieldTitle, String input) {
        log.info("Entering value {} in the field {}", input, fieldTitle);
        driver.findElement(By.xpath(String.format(INPUT_LOCATOR, fieldTitle))).sendKeys(input);
    }

    @Step("Clicking checkbox input")
    public void clickCheckBoxInput() {
        log.info("Clicking on checkbox 'Remember me'");
        driver.findElement(By.xpath(CHECKBOX_LOCATOR)).click();
    }

    @Step("Clicking login button")
    public void clickLoginButton() {
        log.info("Clicking on [Login] button");
        driver.findElement(SIGN_IN_BUTTON).click();
    }
}
