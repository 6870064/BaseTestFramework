package dummy_api;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class DummyUserApiTests extends BaseDummyTest {

    String getUsersEndpoint = "/user/" + userID;
    String getUsersListEndpoint = "/user";
    String createUserEndpoint = "/user/create";

    @Test
    public void dummyUsersApiTest() {

        RequestSpecification requestSpec = given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }

    @Test
    public void dummyGetOneUserApiTest() {

        JSONObject jsonUserData = getJSONObjectFromFile("src/json_files/one_user_data.json");
        RequestSpecification requestSpec = given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersEndpoint);
        response.prettyPrint();

        Assert.assertTrue(response.getStatusCode() == 200);
        Assert.assertEquals(200, response.statusCode());

        //TODO фикс валидации схемы
        //   Assert.assertTrue(checkJSONSchema(response, "/Users/dzmitry.viachaslavau/Documents/Projects/BaseTestFramework/src/schemas/User_schema.json"));

        Assert.assertEquals(response.getBody().jsonPath().get("id"), jsonUserData.get("id"));
        Assert.assertEquals(response.getBody().jsonPath().get("title"), jsonUserData.get("title"));
        Assert.assertEquals(response.getBody().jsonPath().get("firstName"), jsonUserData.get("firstName"));
        Assert.assertEquals(response.getBody().jsonPath().get("lastName"), jsonUserData.get("lastName"));
        Assert.assertEquals(response.getBody().jsonPath().get("gender"), jsonUserData.get("gender"));
        Assert.assertEquals(response.getBody().jsonPath().get("email"), jsonUserData.get("email"));
        Assert.assertEquals(response.getBody().jsonPath().get("dateOfBirth"), jsonUserData.get("dateOfBirth"));
        Assert.assertEquals(response.getBody().jsonPath().get("phone"), jsonUserData.get("phone"));
        Assert.assertEquals(response.getBody().jsonPath().get("location.street"), jsonUserData.getJSONObject("location").get("street"));
        Assert.assertEquals(response.getBody().jsonPath().get("location.city"), jsonUserData.getJSONObject("location").get("city"));
        Assert.assertEquals(response.getBody().jsonPath().get("location.state"), jsonUserData.getJSONObject("location").get("state"));
        Assert.assertEquals(response.getBody().jsonPath().get("location.country"), jsonUserData.getJSONObject("location").get("country"));
        Assert.assertEquals(response.getBody().jsonPath().get("location.timezone"), jsonUserData.getJSONObject("location").get("timezone"));
    }

    @Test
    public void createUserNewTest() {

        String randomEmail = "test" + getRandomValue(5) + "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());
        Response response = requestSpec.request(Method.POST, createUserEndpoint);
        response.prettyPrint();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().jsonPath().get("title"), jsonRequestBody.get("title"));
        Assert.assertEquals(response.getBody().jsonPath().get("firstName"), jsonRequestBody.get("firstName"));
        Assert.assertEquals(response.getBody().jsonPath().get("lastName"), jsonRequestBody.get("lastName"));
        Assert.assertEquals(response.getBody().jsonPath().get("gender"), jsonRequestBody.get("gender"));
        Assert.assertEquals(response.getBody().jsonPath().get("email"), randomEmail.toLowerCase());
        Assert.assertEquals(response.getBody().jsonPath().get("dateOfBirth"), jsonRequestBody.get("dateOfBirth"));
    }

    @Test
    public void updateUserTest() {

        //Получение данных для создания пользователя
        String randomEmail = "test" + getRandomValue(5) + "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);


        RequestSpecification requestSpec = given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint); //получение респонса
        createUserResponse.prettyPrint();
        Assert.assertEquals(200, createUserResponse.statusCode()); //проверка респонса на 200 статус

        //Получение данных для обновления пользователя
        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/json_files/update_user_payload_request.json");
        jsonUpdateRequestBody.put("email", randomEmail);

        //создание endpoint для обновления пользователя
        String updateUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");
        RequestSpecification requestSpec2 = given().headers(send_headers);

        requestSpec2.body(jsonUpdateRequestBody.toString());
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint); //получение респонса
        updateUserResponse.prettyPrint();

        //Проверка ответа на 200 статус и эквивалентность данных с репонса с данными обновленного пользователя
        Assert.assertTrue(updateUserResponse.getStatusCode() == 200);
        Assert.assertEquals(200, updateUserResponse.statusCode());
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("id"), createUserResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("title"), jsonUpdateRequestBody.get("title"));
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("firstName"), jsonUpdateRequestBody.get("firstName"));
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("lastName"), jsonUpdateRequestBody.get("lastName"));
        ;
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("gender"), jsonUpdateRequestBody.get("gender"));
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("email"), randomEmail.toLowerCase());
        Assert.assertEquals(updateUserResponse.getBody().jsonPath().get("dateOfBirth"), jsonUpdateRequestBody.get("dateOfBirth"));
    }

    @Test
    public void deleteUserTest() {

        String randomEmail = "test" + getRandomValue(5) + "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();

        String deleteUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        RequestSpecification jsonRequestDeleteBody = given().headers(send_headers);
        Response deleteUserResponse = jsonRequestDeleteBody.request(Method.DELETE, deleteUserEndpoint);
        deleteUserResponse.prettyPrint();

        Assert.assertTrue(deleteUserResponse.getStatusCode() == 200);
        Assert.assertEquals(200, deleteUserResponse.statusCode());
        Assert.assertEquals(deleteUserResponse.jsonPath().getString("id"), createUserResponse.jsonPath().getString("id"));
    }
}
