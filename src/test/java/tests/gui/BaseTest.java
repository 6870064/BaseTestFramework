package tests.gui;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.MainPage;
import utils.PropertyReader;
import utils.TestListener;
import utils.WebDriver_Initializer;

@Log4j2
@Listeners(TestListener.class)
public abstract class BaseTest {

    public static PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");
    public static WebDriver_Initializer webDriverInitializer = WebDriver_Initializer.getInstance();
    public static WebDriver driver = webDriverInitializer.driverInitialization();
    public static String BASE_URL = propertyReader.getPropertyValueByKey("base.url");

    LoginPage loginPage = new LoginPage(driver);
    MainPage mainPage = new MainPage(driver);

    @Parameters({"browser"})
    @BeforeMethod()
    public void setUp(@Optional("chrome") String browser) {
    driver = webDriverInitializer.driverInitialization();
    }

    @AfterMethod()
    public void tearDown() {
        webDriverInitializer.closeDriver();
    }
}
