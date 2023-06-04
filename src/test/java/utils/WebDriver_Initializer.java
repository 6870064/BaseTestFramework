package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Optional;
import tests.BaseTest;

import java.util.concurrent.TimeUnit;

public class WebDriver_Initializer {

    private static WebDriver driver;
    private static WebDriver_Initializer instance;

    public static WebDriver_Initializer getInstance() {
        if (instance == null) {
            instance = new WebDriver_Initializer();
        }
        return instance;
    }

    public WebDriver driverInitialization() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(setOptionsForChromeDriver("chrome"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//Неявные ожидания
        return driver;
    }

    private ChromeOptions setOptionsForChromeDriver(@Optional("chrome") String browser) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            if (BaseTest.propertyReader.getPropertyValueByKey("headless").equals("true")) {
                chromeOptions.addArguments("--headless");
            }
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        return chromeOptions;
    }

    public void closeDriver() {
        driver.quit();
    }
}