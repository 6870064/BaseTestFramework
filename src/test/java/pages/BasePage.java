package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.JSExecutor;
import utils.PropertyReader;

import static tests.BaseTest.driver;

//@Log4j2
public abstract class BasePage {
    protected PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");
    public final String BASE_URL = propertyReader.getPropertyValueByKey("base.url");
    public static JSExecutor jsExecutor;

    public BasePage(WebDriver driver) {
        jsExecutor = new JSExecutor(driver);
    }

    public abstract boolean isPageOpened();

    public void projectOpen() {
        driver.get(BASE_URL);
    }

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
