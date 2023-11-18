package dummy_api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTagApiTests extends BaseDummyTest {

    String getTagsListEndpoint = "/tag";

    @Test(testName = "Get Tags API test")
    public void getTagListApiTest() {

        RequestSpecification requestSpec = RestAssured.given().headers(send_headers);
        Response response = requestSpec.request(Method.GET, getTagsListEndpoint);
        response.prettyPrint();
        Assert.assertTrue(response.getStatusCode() == 200);
    }
}
