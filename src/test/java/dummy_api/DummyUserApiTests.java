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

    String getUsersEndpoint = "/user/" +userID;
    String getUsersListEndpoint = "/user";
    String createUserEndpoint = "/user/create";

    @Test
    public void dummyUsersApiTest() {

        RequestSpecification requestSpec = given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode()==200);
        }

    @Test
    public void dummyGetOneUserApiTest() {

        RequestSpecification requestSpec = given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getUsersEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
        Assert.assertEquals(200, response.statusCode());
        JSONObject jsonUserData = getJSONObjectFromFile("src/test/resources/json_files/one_user_data.json");
        JSONObject jsonSchema = getJSONObjectFromFile("src/test/resources/schemas/User_schema.json");

        //TODO уточнить, как забирать данные с location. Сейчас забирается с отдельного JSON с данными по локации.
        JSONObject locationJson = getJSONObjectFromFile("src/test/resources/json_files/one_user_data_location.json");

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
        Assert.assertEquals(checkJSONSchema(response, "src/test/resources/json_files/one_user_data_location.json"), true);
    }

    @Test
    public void createUserNewTest() {

        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = given().headers(send_headers);
         requestSpec.body(jsonRequestBody.toString());

        Response response = requestSpec.request(Method.POST, createUserEndpoint);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("title"), jsonRequestBody.get("title"));
        Assert.assertEquals(response.jsonPath().getString("firstName"),jsonRequestBody.get("firstName"));
        Assert.assertEquals(response.jsonPath().getString("lastName"), jsonRequestBody.get("lastName"));
        Assert.assertEquals(response.jsonPath().getString("gender"), jsonRequestBody.get("gender"));
        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(response.jsonPath().getString("dateOfBirth"),  jsonRequestBody.get("dateOfBirth"));
        }

    @Test
    public void updateUserTest() {

        //Получение данных для создания пользователя
        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);


        RequestSpecification requestSpec = given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint); //получение респонса
        createUserResponse.prettyPrint();
        Assert.assertEquals(200, createUserResponse.statusCode()); //проверка респонса на 200 статус

        //Получение данных для обновления пользователя
        JSONObject jsonUpdateRequestBody = getJSONObjectFromFile("src/test/resources/json_files/update_user_payload_request.json");
        jsonUpdateRequestBody.put("email", randomEmail);

        //создание endpoint для обновления пользователя
        String updateUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");
        RequestSpecification requestSpec2 = given().headers(send_headers);

        requestSpec2.body(jsonUpdateRequestBody.toString());
        Response updateUserResponse = requestSpec2.request(Method.PUT, updateUserEndpoint); //получение респонса
        updateUserResponse.prettyPrint();

        //Проверка ответа на 200 статус и эквивалентность данных с репонса с данными обновленного пользователя
        Assert.assertTrue(updateUserResponse.getStatusCode()==200);
        Assert.assertEquals(200, updateUserResponse.statusCode());
        Assert.assertEquals(updateUserResponse.jsonPath().getString("id"), createUserResponse.jsonPath().getString("id"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("title"),jsonUpdateRequestBody.get("title"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("firstName"),jsonUpdateRequestBody.get("firstName"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("lastName"), jsonUpdateRequestBody.get("lastName"));;
        Assert.assertEquals(updateUserResponse.jsonPath().getString("gender"),jsonUpdateRequestBody.get("gender"));
        Assert.assertEquals(updateUserResponse.jsonPath().getString("email"), randomEmail.toLowerCase());
        Assert.assertEquals(updateUserResponse.jsonPath().getString("dateOfBirth"),jsonUpdateRequestBody.get("dateOfBirth"));
    }

    @Test
    public void deleteUserTest() {

        String randomEmail = "test" +getRandomValue(5)+ "@gmail.com";
        JSONObject jsonRequestBody = getJSONObjectFromFile("src/test/resources/json_files/create_user_payload_request.json");
        jsonRequestBody.put("email", randomEmail);

        RequestSpecification requestSpec = given().headers(send_headers);
        requestSpec.body(jsonRequestBody.toString());

        Response createUserResponse = requestSpec.request(Method.POST, createUserEndpoint);
        createUserResponse.prettyPrint();

        String deleteUserEndpoint = "/user/" + createUserResponse.jsonPath().getString("id");

        RequestSpecification jsonRequestDeleteBody = given().headers(send_headers);
        Response deleteUserResponse = jsonRequestDeleteBody.request(Method.DELETE, deleteUserEndpoint);
        deleteUserResponse.prettyPrint();

        Assert.assertTrue(deleteUserResponse.getStatusCode()==200);
        Assert.assertEquals(200, deleteUserResponse.statusCode());
        Assert.assertEquals(deleteUserResponse.jsonPath().getString("id"), createUserResponse.jsonPath().getString("id"));
    }
}
