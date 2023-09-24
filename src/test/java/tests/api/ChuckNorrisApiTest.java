package tests.api;

import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class ChuckNorrisApiTest extends BaseApiTest {

    @Test
    public void ApiResponseCodeApiTest() {

        given()
                .when()
                .get(baseURI)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().status()
                .statusCode(200);
    }

    @Test
    public void ApiCategoriesResponseApiTest() {

        //Setup Endpoint
        String categoriesEndpoint = "/jokes/categories/";

        given()
                .when()
                .get(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body();
    }



}
