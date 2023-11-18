package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyCommentApiTests extends BaseDummyTest {

    String getCommentsListEndpoint = "/comment";
    String getGetCommentsListByPostEndpoint = "post/"+ postId+ "/comment";
    String getGetCommentsListByUserEndpoint = "user/"+ userId + "/comment";
    String createCommentEndpoint = "/comment/create";

    @Test(testName = "Get Comments API test")
    public void getTagListApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getCommentsListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }

    @Test(testName = "Get List By User")
    public void getListByPostApiTest() {
        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getGetCommentsListByPostEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }

    @Test(testName = "Get List of comments By User")
    public void getListByUserApiTest() {
        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getGetCommentsListByUserEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }

    @Test(testName = "Create a comment")
    public void createCommentApiTest() {

        //подготовка реквеста на создание комментария
        String randomCommentText = "A " + getRandomText(8) + " " + getRandomText(11) + " " + getRandomText(9);
        JSONObject jsonCreateCommentRequestBody = getJSONObjectFromFile("src/json_files/create_comment_payload_request.json");
        jsonCreateCommentRequestBody.put("message", randomCommentText);

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.POST, createCommentEndpoint);
        response.prettyPrint();
        System.out.println(response.getStatusCode());
        Assert.assertTrue(response.getStatusCode() == 400);
    }

}
