package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyPostApiTests extends BaseDummyTest {

    String getPostsListEndpoint = "/post";
    String getUsersPostEndpoint = "/user/" +userID+ "/post";

    @Test
    public void dummyPostsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getPostsListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
    }

    @Test
    public void dummyPostsListsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersPostEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
    }



}
