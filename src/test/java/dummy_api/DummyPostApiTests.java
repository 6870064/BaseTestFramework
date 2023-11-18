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
    String getUserPostEndpoint = "/user/" + userId + "/post";
    String getPostByIdEndpoint = "/post/" + postId;


    @Test(testName = "Get List of Posts API test")
    public void dummyPostsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getPostsListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);

        //валидация схемы респонса
        String jsonPath = "schema/post/getListsOfPostsSchema.json";
        Assert.assertTrue(checkJSONSchema(response, jsonPath));
    }

    @Test(testName = "Get List By User API Test")
    public void dummyPostsListsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUserPostEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }

    @Test(testName = "Get Post by id API Test")
    public void getPostByIdApiTest() {
        RequestSpecification getPostRequestSpecification = RestAssured.given().headers(send_headers);
        Response getPostResponse = getPostRequestSpecification.request(Method.GET, getPostByIdEndpoint);
        JSONObject jsonOwnerData = getJSONObjectFromFile("src/json_files/post_data.json");
        getPostResponse.prettyPrint();

        //валидация схемы респонса
        String jsonPath = "schema/post/getListsOfPostsSchema.json";
        Assert.assertTrue(checkJSONSchema(getPostResponse, jsonPath));
        Assert.assertEquals(200, getPostResponse.statusCode());
        Assert.assertEquals(getPostResponse.statusCode(), 200);
        Assert.assertEquals(getPostResponse.jsonPath().getString("id"), postId);
        Assert.assertEquals(getPostResponse.jsonPath().getString("image"), jsonOwnerData.get("image"));
        Assert.assertEquals(getPostResponse.jsonPath().getString("likes"), jsonOwnerData.get("likes"));
        Assert.assertEquals(getPostResponse.jsonPath().getString("text"), jsonOwnerData.get("text"));
    }

    @Test(testName = "Creating a new Post by API Test")
    public void createPostApiTest() {
        //подготовка реквеста на создание пользователя
        String randomLikesInt = getRandomValue(1);
        String randomPostText = "A " + getRandomText(8) + " " + getRandomText(11) + " " + getRandomText(9);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/json_files/create_post_payload_request.json");
        JSONObject jsonUserData = getJSONObjectFromFile("src/json_files/one_user_data.json");

        jsonCreateUserRequestBody.put("likes", randomLikesInt);
        jsonCreateUserRequestBody.put("text", randomPostText);

        //отправка запроса на создание пользователя
        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());

        //получение респонса
        Response createPostResponse = requestSpecification.request(Method.POST, createPostEndpoint);
        createPostResponse.prettyPrint();

        //валидация схемы респонса
        String jsonPath = "schema/post/createPostSchema.json";
        Assert.assertTrue(checkJSONSchema(createPostResponse, jsonPath));
        //Валидация данных в созданном посте
        Assert.assertEquals(200, createPostResponse.statusCode());
        Assert.assertEquals(createPostResponse.statusCode(), 200);
        Assert.assertEquals(createPostResponse.jsonPath().getString("text"), jsonCreateUserRequestBody.get("text"));
        Assert.assertEquals(createPostResponse.jsonPath().getString("image"), jsonCreateUserRequestBody.get("image"));
        Assert.assertEquals(createPostResponse.jsonPath().getString("likes"), randomLikesInt);
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.id"), jsonUserData.get("id"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.title"), jsonUserData.get("title"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.firstName"), jsonUserData.get("firstName"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.lastName"), jsonUserData.get("lastName"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.picture"), jsonUserData.get("picture"));
    }

    @Test(testName = "Updating API Test")
    public void updatePostApiTest() {
        //подготовка реквеста на создание пользователя
        String randomLikesInt = getRandomValue(1);
        String randomPostText = "B " + getRandomText(8) + " " + getRandomText(11) + " " + getRandomText(9);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/json_files/create_post_payload_request.json");
        JSONObject jsonUserData = getJSONObjectFromFile("src/json_files/one_user_data.json");

        jsonCreateUserRequestBody.put("likes", randomLikesInt);
        jsonCreateUserRequestBody.put("text", randomPostText);

        //отправка запроса на создание пользователя
        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());

        //получение респонса
        Response createPostResponse = requestSpecification.request(Method.POST, createPostEndpoint);
        createPostResponse.prettyPrint();

        //создание endpoint для обновления пользователя
        String updateUserEndpoint = "/post/" + createPostResponse.jsonPath().getString("id");
        RequestSpecification requestSpec2 = given().headers(send_headers);

        //Получение данных для обновления поста
        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/json_files/update_post_payload_request.json");
        randomLikesInt = getRandomValue(1);
        randomPostText = "Fre " + getRandomText(8) + " " + getRandomText(11) + " " + getRandomText(9);
        jsonUpdateRequestBody.put("likes", randomLikesInt);
        jsonUpdateRequestBody.put("text", randomPostText);

        //отправка запроса на обновление поста
        requestSpec2.body(jsonUpdateRequestBody.toString());
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint); //получение респонса
        updateUserResponse.prettyPrint();

        //валидация схемы респонса
        String jsonPath = "schema/post/createPostSchema.json";
        Assert.assertTrue(checkJSONSchema(createPostResponse, jsonPath));

        //Валидация данных в обновленном посте
        Assert.assertEquals(updateUserResponse.jsonPath().getString("id"), createPostResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("image"), jsonUpdateRequestBody.get("image"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("likes"), randomLikesInt);
        Assert.assertEquals(updateUserResponse.jsonPath().getString("text"), jsonUpdateRequestBody.get("text"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.id"), jsonUserData.get("id"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.title"), jsonUserData.get("title"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.firstName"), jsonUserData.get("firstName"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.lastName"), jsonUserData.get("lastName"));
        Assert.assertEquals(createPostResponse.getBody().jsonPath().get("owner.picture"), jsonUserData.get("picture"));
    }

    @Test
    public void deletePostApiTest() {
        //подготовка реквеста на создание пользователя
        String randomLikesInt = getRandomValue(1);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");
        jsonCreateUserRequestBody.put("likes", randomLikesInt);

        //отправка запроса на создание пользователя
        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());

        //получение респонса
        Response createPostResponse = requestSpecification.request(Method.POST, createPostEndpoint);
        createPostResponse.prettyPrint();

        //создание endpoint для удаления пользователя
        String deletePostEndpoint = "/post/" + createPostResponse.jsonPath().getString("id");

        RequestSpecification jsonRequestDeleteBody = given().headers(send_headers);
        Response deletePostResponse = jsonRequestDeleteBody.request(Method.DELETE, deletePostEndpoint);
        deletePostResponse.prettyPrint();

        // Валидация данных после удаления поста
        Assert.assertTrue(deletePostResponse.getStatusCode() == 200);
        Assert.assertEquals(200, deletePostResponse.statusCode());
        Assert.assertEquals(deletePostResponse.jsonPath().getString("id"), createPostResponse.jsonPath().getString("id"));
    }
}


