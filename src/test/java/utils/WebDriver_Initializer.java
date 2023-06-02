package utils;

import java.util.concurrent.TimeUnit;

public class WebDriver_Initializer {
    private static WebDriver driver;
    private static WebDriver_Initializer instance;
    protected PropertyReader propertyReader = new PropertyReader("src/test/resources/configuration.properties");

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

        return driver;
    }

    public void closeDriver() {
        driver.quit();
    }

    @Parameters({"browser"})
    @BeforeMethod(description = "Open browser")
    private ChromeOptions setOptionsForChromeDriver(@Optional("chrome") String browser) {

        ChromeOptions chromeOptions = new ChromeOptions();
        if (browser.equals("chrome")) {

            WebDriverManager.chromedriver().setup();
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
        return chromeOptions;
    }
}
