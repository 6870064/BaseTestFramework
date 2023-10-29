package chucknorris_api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseChuckNorrisApiTest {

    @BeforeMethod()
    public void setUpApi() {

        //setup RestAssured
        RestAssured.baseURI = "https://api.chucknorris.io";
    }

}
