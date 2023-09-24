package tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest {

    @BeforeMethod()
    public void setUpApi() {
        //setup RestAssured
        RestAssured.baseURI = "https://api.chucknorris.io";
    }

}
