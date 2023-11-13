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
    String getUserPostEndpoint = "/user/" +userID+ "/post";
    String postId = "654faf489d2bedc28af7049f";
    String getPostByIdEndpoint = "/post/" + postId;


    @Test (testName = "Get List API test")
    public void dummyPostsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getPostsListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
    }

    @Test (testName = "Get List By User API Test")
    public void dummyPostsListsApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUserPostEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
    }

    @Test (testName = "Get Post by id API Test")
    public void getPostByIdApiTest(){
        RequestSpecification getPostRequestSpecification = RestAssured.given().headers(send_headers);
        Response getPostResponse = getPostRequestSpecification.request(Method.GET,getPostByIdEndpoint);
        JSONObject jsonOwnerData = getJSONObjectFromFile("src/test/resources/json_files/post_data.json");
        getPostResponse.prettyPrint();

        Assert.assertEquals(200, getPostResponse.statusCode());
        Assert.assertEquals(getPostResponse.statusCode(),200);
        Assert.assertEquals(getPostResponse.jsonPath().getString("id"), postId);
        Assert.assertEquals(getPostResponse.jsonPath().getString("image"), jsonOwnerData.get("image"));
        Assert.assertEquals(getPostResponse.jsonPath().getString("likes"), jsonOwnerData.get("likes"));
        Assert.assertEquals(getPostResponse.jsonPath().getString("text"), jsonOwnerData.get("text"));
        //TODO Сделать валидаю tags and owner
    }

    @Test (testName = "Sandbox API Test for trying different things")
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

    @Test (testName = "Creating a new Post by API Test")
    public void createPostApiTest() {
        //подготовка реквеста на создание пользователя
        String randomLikesInt = getRandomValue(1);
        String randomPostText = "A " +getRandomText(8)+ " "+ getRandomText(11)+ " " +getRandomText(9);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");
        jsonCreateUserRequestBody.put("likes", randomLikesInt);
        jsonCreateUserRequestBody.put("text", randomPostText);

        //отправка запроса на создание пользователя
        RequestSpecification requestSpecification = RestAssured.given().headers(send_headers);
        requestSpecification.body(jsonCreateUserRequestBody.toString());

        //получение респонса
        Response createPostResponse = requestSpecification.request(Method.POST, createPostEndpoint);
        createPostResponse.prettyPrint();

        //Валидация данных в созданном посте
        Assert.assertEquals(200, createPostResponse.statusCode());
        Assert.assertEquals(createPostResponse.statusCode(),200);
        Assert.assertEquals(createPostResponse.jsonPath().getString("text"), jsonCreateUserRequestBody.get("text"));
        Assert.assertEquals(createPostResponse.jsonPath().getString("image"),jsonCreateUserRequestBody.get("image"));
        Assert.assertEquals(createPostResponse.jsonPath().getString("likes"), randomLikesInt);
        //TODO доделать валидацию owner-a
        //TODO доделать валидацию tags
    }

    @Test (testName = "Updating API Test")
    public void updatePostApiTest() {
        //подготовка реквеста на создание пользователя
        String randomLikesInt = getRandomValue(1);
        String randomPostText = "B " +getRandomText(8)+ " "+ getRandomText(11)+ " " +getRandomText(9);
        JSONObject jsonCreateUserRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_post_payload_request.json");
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
        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/test/resources/json_files/update_post_payload_request.json");
        randomLikesInt = getRandomValue(1);
        randomPostText = "Fre " +getRandomText(8)+ " "+ getRandomText(11)+ " " +getRandomText(9);
        jsonUpdateRequestBody.put("likes", randomLikesInt);
        jsonUpdateRequestBody.put("text", randomPostText);

        //отправка запроса на обновление поста
        requestSpec2.body(jsonUpdateRequestBody.toString());
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint); //получение респонса
        updateUserResponse.prettyPrint();

        //Валидация данных в обновленном посте
        Assert.assertEquals(updateUserResponse.jsonPath().getString("id"), createPostResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("image"), jsonUpdateRequestBody.get("image"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("likes"), randomLikesInt);
        Assert.assertEquals(updateUserResponse.jsonPath().getString("text"), jsonUpdateRequestBody.get("text"));
        //TODO доделать валидацию owner-a
        //TODO доделать валидацию tags
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
        Assert.assertTrue(deletePostResponse.getStatusCode()==200);
        Assert.assertEquals(200, deletePostResponse.statusCode());
        Assert.assertEquals(deletePostResponse.jsonPath().getString("id"), createPostResponse.jsonPath().getString("id"));
    }
}


