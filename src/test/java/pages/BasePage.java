package pages;

import lombok.extern.log4j.Log4j2;
import utils.PropertyReader;

import java.util.NoSuchElementException;

@Log4j2
public abstract class BasePage {
    protected PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");
    public final String BASE_URL = propertyReader.getPropertyValueByKey("base.url");

    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        jsExecutor = new JSExecutor(driver);
    }

    public void projectOpen() {
        driver.get(PROJECT_URL);
    }

    public abstract boolean isPageOpened();

    public boolean isElementExist(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }


}
