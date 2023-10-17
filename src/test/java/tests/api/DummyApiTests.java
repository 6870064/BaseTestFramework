package tests.api;

import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class DummyApiTests extends BaseApiTest {

    @Test
    public void dummieApiTest() {

        Response response = given()
                .get(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body()
                .extract()
                .response();

        List<String> myList = response.getBody().jsonPath().getList("");
        Assert.assertEquals(myList, categoriesList);
    }

}
