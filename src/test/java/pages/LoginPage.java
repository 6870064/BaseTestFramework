package pages;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginPage extends BasePage {

    public static final By SIGN_IN_BUTTON = By.xpath("//a[@class='btn btn-primary']");
    public static final String INPUT_LOCATOR = "//input[@id='%s']";
    public static final String CHECKBOX_LOCATOR = "//input[@class='form-check-input']";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void pageOpen() {
        driver.get(BASE_URL);
    }

    @Override
    public boolean isPageOpened() {
        return isElementExist(SIGN_IN_BUTTON);
    }

    public void setValue(String fieldTitle, String input) {
        log.info("Entering value {} in the field {}", input, fieldTitle);
        driver.findElement(By.xpath(String.format(INPUT_LOCATOR, fieldTitle))).sendkeys(input);
    }

    public void clickCheckBoxInput() {
        log.info("Clicking on checkbox 'Remember me'");
        driver.findElement(CHECKBOX_LOCATOR).click;
    }

    public void clickLoginButton() {
        log.info("Clicking on [Login] button")
        driver.findElement(SIGN_IN_BUTTON).click;
    }
}
