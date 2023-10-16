package tests.api;

import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void ApiCategoriesValidationTest() {

        //Setup Endpoint
        String categoriesEndpoint = "/jokes/categories/";

        List<Object> categoriesList = new ArrayList<>();
        categoriesList.add("animal");
        categoriesList.add("career");
        categoriesList.add("celebrity");
        categoriesList.add("dev");
        categoriesList.add("explicit");
        categoriesList.add("fashion");
        categoriesList.add("food");
        categoriesList.add("history");
        categoriesList.add("money");
        categoriesList.add("movie");
        categoriesList.add("music");
        categoriesList.add("political");
        categoriesList.add("religion");
        categoriesList.add("science");
        categoriesList.add("sport");
        categoriesList.add("travel");

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

    @Test
    public void ApiJokesValidationTest() {
        String categoriesEndpoint = "/jokes/random/";
        Response response = given()
                .get(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body()
                .extract()
                .response();

    }
}
