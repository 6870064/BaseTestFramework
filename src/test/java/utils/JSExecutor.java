//package utils;
//
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class JSExecutor {
//    WebDriver driver;
//    WebDriverWait wait;
//
//    public JSExecutor(WebDriver driver) {
//        this.driver = driver;
//        wait = new WebDriverWait(driver, 20000);
//    }
//
//
//    public void scrollToElement(WebElement element) {
//
//        wait = new WebDriverWait(driver, 20000);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
//        wait.until(ExpectedConditions.elementToBeClickable(element));
//    }
//
//    public void clickOnElement(WebElement element) {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
//    }
//}