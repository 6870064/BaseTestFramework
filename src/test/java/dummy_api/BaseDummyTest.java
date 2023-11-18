package dummy_api;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class BaseDummyTest {


    String userId = "60d0fe4f5311236168a109dd";
    String postId = "654faf489d2bedc28af7049f";
    HashMap<String, String> send_headers = new HashMap<>() {{
        put("app-id", "653e7ecc4cdc863e717a3587");
        put("Content-type", "application/json");
    }};

    @BeforeMethod()
    public void setUpApi() {

        //setup RestAssured
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
    }

    public static boolean checkJSONSchema(Response response, String pathToSchema) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(pathToSchema));
            return true;// think about it
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public static String getRandomText(int length) {
        String charset = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
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

//    public static void JsonSchemaValidationExample(String schemaString, String jsonDataString) {
//
//       //Parse the schema and data
//       JSONObject rawSchema = new JSONObject(new JSONTokener(schemaString));
//       JSONObject jsonData = new JSONObject(new JSONTokener(jsonDataString));
//
//       //Create a JSON Schema object
//        Schema schema = SchemaLoader.load(rawSchema);
//
//        // Perform validation
//        try {
//            schema.validate(jsonData);
//            System.out.println("Validation passed!");
//        } catch (Exception e) {
//            System.out.println("Validation failed: " + e.getMessage());
//        }
//
//    }
}
