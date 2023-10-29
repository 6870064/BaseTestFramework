package dummy_api;

import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class DummyApiTests extends BaseDummyTest {

    String userID = "60d0fe4f5311236168a109dd";
    String getUsersListEndpoint = "/user";
    String createUserEndpoint = "/user/create";

    private static String requestBody = "{\n" +
            "  \"title\": \"mr\",\n" +
            "  \"firstName\": \"Billy\",\n" +
            "  \"lastName\": \"Bob Tornton\",\n" +
            "  \"gender\": \"male\",\n" +
            "  \"email\": \"BobTornton3333@gmail.com\",\n" +
            "  \"dateOfBirth\": \"1980-07-05T22:21:32.623Z\" \n}";

    @Test
    public void dummyUsersApiTest() {

        given().header("app-id", "653e7ecc4cdc863e717a3587")
                .when()
                .get(getUsersListEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusCode(200)
                .log().status()
                .log().body();
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

        //TODO уточнить, как оптимизировать, через обьект, двумерный массив?
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

        //TODO уточнить, как оптимизировать, через обьект, двумерный массив?
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("mr", response.jsonPath().getString("title"));
        Assert.assertEquals("Billy", response.jsonPath().getString("firstName"));
        //TODO уточнить про email
        Assert.assertEquals("Bob Tornton", response.jsonPath().getString("lastName"));
        Assert.assertEquals("male", response.jsonPath().getString("gender"));
        Assert.assertEquals("bobtornton3333@gmail.com", response.jsonPath().getString("email"));
        Assert.assertEquals("1980-07-05T22:21:32.623Z", response.jsonPath().getString("dateOfBirth"));

    }
}
