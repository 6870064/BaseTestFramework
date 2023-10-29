package dummy_api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseDummyTest {

    @BeforeMethod()
    public void setUpApi() {

        //setup RestAssured
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
    }
}
