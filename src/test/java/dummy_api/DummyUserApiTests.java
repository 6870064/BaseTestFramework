package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

@Log4j2
public class DummyUserApiTests extends BaseDummyTest {

    String getUsersEndpoint = "/user/" +userID;
    String getUsersListEndpoint = "/user";
    String createUserEndpoint = "/user/create";

    @Test
    public void dummyUsersApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
        }

    @Test
    public void dummyGetOneUserApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
        Assert.assertEquals(200, response.statusCode());
        JSONObject jsonUserData = getJSONObjectFromFile("src/test/resources/one_user_data.json");

        //TODO уточнить, как забирать данные с location.
        JSONObject locationJson = getJSONObjectFromFile("src/test/resources/one_user_data_location.json");

        Assert.assertEquals(response.jsonPath().getString("id"), jsonUserData.get("id"));
        Assert.assertEquals(response.jsonPath().getString("title"), jsonUserData.get("title"));
        Assert.assertEquals(response.jsonPath().getString("firstName"), jsonUserData.get("firstName"));
        Assert.assertEquals(response.jsonPath().getString("lastName"), jsonUserData.get("lastName"));
        Assert.assertEquals(response.jsonPath().getString("gender"), jsonUserData.get("gender"));
        Assert.assertEquals(response.jsonPath().getString("email"), jsonUserData.get("email"));
        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"), jsonUserData.get("dateOfBirth"));
        Assert.assertEquals(response.jsonPath().getString("phone"), jsonUserData.get("phone"));
        Assert.assertEquals(response.jsonPath().getString("location.street"), locationJson.get("street"));
        Assert.assertEquals(response.jsonPath().getString("location.city"), locationJson.get("city"));
        Assert.assertEquals(response.jsonPath().getString("location.state"), locationJson.get("state"));
        Assert.assertEquals(response.jsonPath().getString("location.country"), locationJson.get("country"));
        Assert.assertEquals(response.jsonPath().getString("location.timezone"), locationJson.get("timezone"));
    }

    @Test
    public void createUserNewTest() {

        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
         requestSpec.body(jsonRequestBody.toString());

        Response response = requestSpec.request(Method.POST, createUserEndpoint);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("firstName"),jsonRequestBody.get("firstName"));
        Assert.assertEquals(response.jsonPath().getString("lastName"), jsonRequestBody.get("lastName"));
        Assert.assertEquals(response.jsonPath().getString("gender"), jsonRequestBody.get("gender"));
        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"),  jsonRequestBody.get("dateOfBirth"));
        }

    @Test
    public void updateUserTest() {

        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();
        Assert.assertEquals(200, createUserResponse.statusCode());

        String updateUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/test/resources/update_user_payload_request.json");
        jsonUpdateRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec2 = RestAssured.given().headers(send_headers);
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint);
        updateUserResponse.prettyPrint();

        Assert.assertTrue(updateUserResponse.getStatusCode()==200);
        Assert.assertEquals(200, updateUserResponse.statusCode());
        Assert.assertEquals(createUserResponse.jsonPath().getString("id"), updateUserResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("title"),updateUserResponse.jsonPath().getString("title"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("firstName"),updateUserResponse.jsonPath().getString("firstName"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("lastName"), updateUserResponse.jsonPath().getString("lastName"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("gender"),updateUserResponse.jsonPath().getString("gender"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(updateUserResponse.jsonPath().getString("dateOfBirth"),updateUserResponse.jsonPath().getString("dateOfBirth"));
    }

    @Test
    public void deleteUserTest() {

        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();

        String deleteUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        RequestSpecification jsonRequestDeleteBody = RestAssured.given().headers(send_headers);
        Response deleteUserResponse = jsonRequestDeleteBody.request(Method.DELETE, deleteUserEndpoint);
        deleteUserResponse.prettyPrint();

        Assert.assertTrue(deleteUserResponse.getStatusCode()==200);
        Assert.assertEquals(200, deleteUserResponse.statusCode());
        Assert.assertEquals(deleteUserResponse.jsonPath().getString("id"), createUserResponse.jsonPath().getString("id"));
    }

    public static String getRandomValue(int length) {
        String charset = "0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(charset.length());
            char randomChar = charset.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    public static JSONObject getJSONObjectFromFile(String filePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
