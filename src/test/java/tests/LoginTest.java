package tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;

import static org.testng.Assert.assertTrue;

@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Login Feature")
@Log4j2

public class LoginTest extends BaseTest {

    protected final String USER_LOGIN = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
    protected final String USER_PASSWORD = System.getProperty("username", propertyReader.getPropertyValueByKey("password"));
    String usernameFieldTitle = "username";
    String wrongUserLogin = "bla bla bla";
    String passwordFieldTitle = "password";

    @Test(priority = 0, description = "Login with valid user credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: login test with valid username and valid password.")
    @Story("Valid username and password login test")
    public void validUserLogin() {
        loginPage.pageOpen();
        assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.setValue(usernameFieldTitle, USER_LOGIN);
        loginPage.setValue(passwordFieldTitle, USER_PASSWORD);
        loginPage.clickCheckBoxInput();
        loginPage.clickLoginButton();
        mainPage.isPageOpened();
    }

    @Test(priority = 0, description = "Login with invalid user credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: login test with invalid username and valid password.")
    @Story("Valid username and password login test")
    public void invalidUserLogin() {
        loginPage.pageOpen();
        assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.setValue(usernameFieldTitle, wrongUserLogin);
        loginPage.setValue(passwordFieldTitle, USER_PASSWORD);
        loginPage.clickCheckBoxInput();
        loginPage.clickLoginButton();
        mainPage.isPageOpened();
        assertTrue(false, "Main page is not opened");
    }
}