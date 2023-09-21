package tests.api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ChuckNorrisApiTest {

    @Test
    public void ApiTest() {

        Logger logger = LogManager.getLogger(ChuckNorrisApiTest.class);

        //setup RestAssured
        RestAssured.baseURI = "https://api.chucknorris.io";

        //Setup Endpoint
        String categoriesEndpoint = "/jokes/categories/";

        //Setup Request Object
        RequestSpecification httpRequest = given();

        //Setup Response Object
        Response response = httpRequest.request(Method.GET, categoriesEndpoint);

        //Assertions
        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);

        //Get Response's body
        logger.info(response.getBody());

    }
}
