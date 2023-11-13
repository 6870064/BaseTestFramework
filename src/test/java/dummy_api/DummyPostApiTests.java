package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

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
    public void sandboxApiTest() {
        String randomLikesInt = getRandomValue(2);
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");

        jsonRequestBody.put("likes", randomLikesInt);

//        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
//        requestSpecification.body(jsonRequestBody.toString());
//
//        Response response = requestSpecification.request(Method.POST, createPostEndpoint);
//        response.prettyPrint();
//
//
//        JSONObject jsonBody = response.jsonPath().getString("text");
//        jsonBody.get("id");
//
//        System.out.println(jsonBody.get("id"));

    }

    @Test
    public void createPostApiTest() {
        String randomLikesInt = getRandomValue(1);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");

        jsonCreateUserRequestBody.put("likes", randomLikesInt);

        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());

        Response response = requestSpecification.request(Method.POST, createPostEndpoint);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("text"), jsonCreateUserRequestBody.get("text"));
        Assert.assertEquals(response.jsonPath().getString("image"),jsonCreateUserRequestBody.get("image"));
        Assert.assertEquals(response.jsonPath().getString("likes"), randomLikesInt);
        //TODO доделать валидацию owner-a
        //TODO доделать валидацию tags
    }

    @Test
    public void updatePostApiTest() {
        String randomLikesInt = getRandomValue(1);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");
        jsonCreateUserRequestBody.put("likes", randomLikesInt);

        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());
        Response createPostResponse = requestSpecification.request(Method.POST, createPostEndpoint);
        createPostResponse.prettyPrint();

        //создание endpoint для обновления пользователя
        String updateUserEndpoint = "/post/" + createPostResponse.jsonPath().getString("id");
        RequestSpecification requestSpec2 = given().headers(send_headers);

        //Получение данных для обновления поста
        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/test/resources/json_files/update_post_payload_request.json");
        randomLikesInt = getRandomValue(1);
        jsonUpdateRequestBody.put("likes", randomLikesInt);

        //отправка запроса на обновление поста
        requestSpec2.body(jsonUpdateRequestBody.toString());
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint); //получение респонса
        updateUserResponse.prettyPrint();

        //Валидация обновленных данных
        Assert.assertEquals(updateUserResponse.jsonPath().getString("id"), createPostResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("image"), jsonUpdateRequestBody.get("image"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("likes"), randomLikesInt);
        Assert.assertEquals(updateUserResponse.jsonPath().getString("text"), jsonUpdateRequestBody.get("text"));
        //TODO доделать валидацию owner-a
        //TODO доделать валидацию tags
    }






}
