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
    String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";

    private String requestBody = "{\n" +
            "  \"title\": \"mr\",\n" +
            "  \"firstName\": \"Billy\",\n" +
            "  \"lastName\": \"Bob Tornton\",\n" +
            "  \"gender\": \"male\",\n" +
            "  \"email\": \""+randomEmail+"\",\n" +
            "  \"dateOfBirth\": \"1980-07-05T22:21:32.623Z\" \n}";

    private String updateRequestBody = "{\n" +
            "  \"title\": \"mr\",\n" +
            "  \"firstName\": \"Johnny\",\n" +
            "  \"lastName\": \"Sins\",\n" +
            "  \"gender\": \"male\",\n" +
            "  \"email\": \""+randomEmail+"\",\n" +
            "  \"dateOfBirth\": \"1978-12-31T22:21:32.623Z\" \n}";

    @Test
    public void dummyUsersApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
        }

    @Test
    public void dummyOneUserApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.jsonPath().getString("id"), "60d0fe4f5311236168a109dd");
        Assert.assertEquals(response.jsonPath().getString("title"), "mr");
        Assert.assertEquals(response.jsonPath().getString("firstName"),"Miguel");
        Assert.assertEquals(response.jsonPath().getString("lastName"),"Lima");
        Assert.assertEquals(response.jsonPath().getString("gender"),"male");
        Assert.assertEquals(response.jsonPath().getString("email"),"miguel.lima@example.com");
        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"),"1979-07-05T22:21:32.623Z");
        Assert.assertEquals(response.jsonPath().getString("phone"),"(33) 1399-0495");
        Assert.assertEquals(response.jsonPath().getString("location.street"), "9637, Rua Rio de Janeiro ");
        Assert.assertEquals(response.jsonPath().getString("location.city"),"Cascavel");
        Assert.assertEquals(response.jsonPath().getString("location.state"),"AmapÃ¡");
        Assert.assertEquals(response.jsonPath().getString("location.country"), "Brazil");
        Assert.assertEquals(response.jsonPath().getString("location.state"), "AmapÃ¡");
        Assert.assertEquals(response.jsonPath().getString("location.timezone"), "+4:00");
    }

    @Test
    public void createUserTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers).and().body(requestBody);
        Response response = requestSpec.request(Method.POST, createUserEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("title"), "mr");
        Assert.assertEquals(response.jsonPath().getString("firstName"),"Billy");
        Assert.assertEquals(response.jsonPath().getString("lastName"), "Bob Tornton");
        Assert.assertEquals(response.jsonPath().getString("gender"), "male");
        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"), "1980-07-05T22:21:32.623Z");

    }

    @Test
    public void createUserNewTest() {

        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
         requestSpec.body(jsonRequestBody.toString());

        Response response = requestSpec.request(Method.POST, createUserEndpoint);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
        }

    @Test
    public void updateUserTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers).and().body(requestBody);
        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();

        String updateUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        RequestSpecification requestSpec2 = RestAssured.given().headers(send_headers).and().body(updateRequestBody);
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint);
        updateUserResponse.prettyPrint();

        Assert.assertTrue(updateUserResponse.getStatusCode()==200);
        Assert.assertEquals(200, updateUserResponse.statusCode());
        Assert.assertEquals(createUserResponse.jsonPath().getString("id"), updateUserResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("title"),"mr");
        Assert.assertEquals(updateUserResponse.jsonPath().getString("firstName"),"Johnny");
        Assert.assertEquals(updateUserResponse.jsonPath().getString("lastName"), "Sins");
        Assert.assertEquals(updateUserResponse.jsonPath().getString("gender"),"male");
        Assert.assertEquals(updateUserResponse.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(updateUserResponse.jsonPath().getString("dateOfBirth"),"1978-12-31T22:21:32.623Z");
    }

    @Test
    public void deleteUserTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers).and().body(requestBody);
        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();

        String deleteUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        RequestSpecification requestSpec2 = RestAssured.given().headers(send_headers);
        Response deleteUserResponse = requestSpec2.request(Method.DELETE, deleteUserEndpoint);
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
