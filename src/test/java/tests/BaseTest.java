package tests;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import pages.MainPage;
import utils.PropertyReader;
import utils.WebDriver_Initializer;

@Log4j2
//@Listeners(TestListener.class)
public abstract class BaseTest {

    public static PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");
    public static WebDriver_Initializer webDriverInitializer = WebDriver_Initializer.getInstance();
    public static WebDriver driver = webDriverInitializer.driverInitialization();
    public static String BASE_URL = propertyReader.getPropertyValueByKey("base.url");
    public String loginFieldTitle = "Email";
    public String passwordFieldTitle = "Password";
    protected final String USER_LOGIN = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
    protected final String USER_PASSWORD = System.getProperty("username", propertyReader.getPropertyValueByKey("password"));

    LoginPage loginPage = new LoginPage(driver);
    MainPage mainPage = new MainPage(driver);

    @Parameters({"browser"})
    @BeforeMethod()
    public void setUp(@Optional("chrome") String browser) {
//        driver = webDriverInitializer.driverInitialization();
    }

    @AfterMethod()
    public void tearDown() {
        webDriverInitializer.closeDriver();
    }
}
