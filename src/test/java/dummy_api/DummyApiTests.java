package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;

@Log4j2
public class DummyApiTests extends BaseDummyTest {

    String userID = "60d0fe4f5311236168a109dd";
    String getUsersListEndpoint = "/user";
    String createUserEndpoint = "/user/create";
    String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
    HashMap<String, String> send_headers = new HashMap<>() {{
        put("app-id", "653e7ecc4cdc863e717a3587");
    }};

    private String requestBody = "{\n" +
            "  \"title\": \"mr\",\n" +
            "  \"firstName\": \"Billy\",\n" +
            "  \"lastName\": \"Bob Tornton\",\n" +
            "  \"gender\": \"male\",\n" +
            "  \"email\": \""+randomEmail+"\",\n" +
            "  \"dateOfBirth\": \"1980-07-05T22:21:32.623Z\" \n}";

    @Test
    public void dummyUsersApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);

        }

    @Test
    public void dummyOneUserApiTest() {

        String getUsersEndpoint = "/user/" +userID;

        Response response = given().header("app-id", "653e7ecc4cdc863e717a3587")
                .when()
                .get(getUsersEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("60d0fe4f5311236168a109dd", response.jsonPath().getString("id"));
        Assert.assertEquals("mr", response.jsonPath().getString("title"));
        Assert.assertEquals("Miguel", response.jsonPath().getString("firstName"));
        Assert.assertEquals("Lima", response.jsonPath().getString("lastName"));
        Assert.assertEquals("male", response.jsonPath().getString("gender"));
        Assert.assertEquals("miguel.lima@example.com", response.jsonPath().getString("email"));
        Assert.assertEquals("1979-07-05T22:21:32.623Z", response.jsonPath().getString("dateOfBirth"));
        Assert.assertEquals("(33) 1399-0495", response.jsonPath().getString("phone"));
        Assert.assertEquals("9637, Rua Rio de Janeiro ", response.jsonPath().getString("location.street"));
        Assert.assertEquals("Cascavel", response.jsonPath().getString("location.city"));
        Assert.assertEquals("AmapÃ¡", response.jsonPath().getString("location.state"));
        Assert.assertEquals("Brazil", response.jsonPath().getString("location.country"));
        Assert.assertEquals("AmapÃ¡", response.jsonPath().getString("location.state"));
        Assert.assertEquals("+4:00", response.jsonPath().getString("location.timezone"));
    }

    @Test
    public void createUserTest() {
        Response response = given()
                .header("app-id", "653e7ecc4cdc863e717a3587")
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(createUserEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("mr", response.jsonPath().getString("title"));
        Assert.assertEquals("Billy", response.jsonPath().getString("firstName"));
        Assert.assertEquals("Bob Tornton", response.jsonPath().getString("lastName"));
        Assert.assertEquals("male", response.jsonPath().getString("gender"));
        Assert.assertEquals(randomEmail.toLowerCase(), response.jsonPath().getString("email"));
        Assert.assertEquals("1980-07-05T22:21:32.623Z", response.jsonPath().getString("dateOfBirth"));

    }

    @Test
    public void createUserNewTest() {

        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/request.json");
        jsonRequestBody.put("email", randomEmail);

                Response response = given()
                .header("app-id", "653e7ecc4cdc863e717a3587")
                .header("Content-type", "application/json")
                .and()
                .body(jsonRequestBody)
                .when()
                .post(createUserEndpoint)
                .then()
               .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().response();

        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
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
