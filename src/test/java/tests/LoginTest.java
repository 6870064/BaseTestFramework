package tests;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

@Log4j2
public class LoginTest extends BaseTest {

    protected final String USER_LOGIN = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
    protected final String USER_PASSWORD = System.getProperty("username", propertyReader.getPropertyValueByKey("password"));
    String usernameFieldTitle = "username";
    String passwordFieldTitle = "password";


    @Test(description = "Login with valid user credentials")
    public void validUserLogin() {
        loginPage.pageOpen();
        assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.setValue(usernameFieldTitle, USER_LOGIN);
        loginPage.setValue(passwordFieldTitle, USER_PASSWORD);
        loginPage.clickCheckBoxInput();
        loginPage.clickLoginButton();
        mainPage.isPageOpened();
    }
}
