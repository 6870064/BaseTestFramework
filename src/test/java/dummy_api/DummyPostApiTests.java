package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyPostApiTests extends BaseDummyTest {

    String createPostEndpoint = "/post/create";
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

    @Test
    public void createPostApiTest() {
        String randomLikesInt = getRandomValue(2);
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");

        JSONObject jsonPostOwner = getJSONObjectFromFile("src/test/resources/json_files/post_owner.json");


        jsonRequestBody.put("likes", randomLikesInt);

        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonRequestBody.toString());

        Response response = requestSpecification.request(Method.POST, createPostEndpoint);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
       Assert.assertEquals(response.jsonPath().getString("text"), jsonRequestBody.get("text"));
       Assert.assertEquals(response.jsonPath().getString("image"),jsonRequestBody.get("image"));
       Assert.assertEquals(response.jsonPath().getString("likes"), randomLikesInt);

        Assert.assertEquals(response.jsonPath().getString("owner"), jsonPostOwner);

//        Assert.assertEquals(response.jsonPath().getString("gender"), jsonRequestBody.get("gender"));
//        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail.toLowerCase());
//        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"),  jsonRequestBody.get("dateOfBirth"));
    }
}
