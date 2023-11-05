package dummy_api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;

public class BaseDummyTest {

    String userID = "60d0fe4f5311236168a109dd";
    HashMap<String, String> send_headers = new HashMap<>() {{
        put("app-id", "653e7ecc4cdc863e717a3587");
        put("Content-type", "application/json");
    }};

    @BeforeMethod()
    public void setUpApi() {

        //setup RestAssured
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
    }
}
