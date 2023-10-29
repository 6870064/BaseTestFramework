package dummy_api;

import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class DummyApiTests extends BaseDummyTest {

    @Test
    public void dummyApiTest() {

        String getUsersEndpoint = "/user?page=1&limit=10";

        given().header("app-id", "653e7ecc4cdc863e717a3587")
                .when()
                .get(getUsersEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body();
    }

}
