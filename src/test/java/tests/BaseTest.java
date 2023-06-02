package tests;

import org.junit.runners.Parameterized;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import pages.MainPage;
import utils.PropertyReader;
import utils.WebDriver_Initializer;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    private final WebDriver_Initializer webDriverInitializer = WebDriver_Initializer.getInstance();
    protected PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");
    WebDriver driver;
    LoginPage loginPage = new LoginPage(driver);
    MainPage mainPage = new MainPage(driver);

    @Parameters({"browser"})
    @BeforeMethod(description = "Open browser")
    public void setUp(@Optional("chrome") String browser) {
        driver = webDriverInitializer.driverInitialization();

        if (browser.equals("chrome")) {

            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//Неявные ожидания
            driver.manage().window().maximize();
            /**
             * --headless - запуск тестов в браузере без UI
             */
            if (propertyReader.getPropertyValueByKey("headless").equals("true")) {
                chromeOptions.addArguments("--headless");
            }

        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
    }

    @AfterMethod(alwaysRun = true, description = "Close browser")
    public void tearDown() {
        webDriverInitializer.closeDriver();
    }
}
