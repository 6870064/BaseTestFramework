package tests.api.chucknorris.io;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static tests.api.chucknorris.io.ApiTestsMethods.CHUCK_NORRIS_URL;

public class ChuckNorrisApiTests {

    String[] strArray = new String[] {"animal","career","celebrity", "dev", "explicit", "fashion", "food", "history",
            "money", "movie", "music","political","religion","science", "sport", "travel"};

ApiTestsMethods apiTestsMethods = new ApiTestsMethods();

@Test
public void statusCodeCheckTest(){
    int statusCode = apiTestsMethods.getResponseCode(CHUCK_NORRIS_URL);
    assertEquals(statusCode, 200, "Status code is equal 200");
}

@Test
public void responseCheckTest(){
   assertEquals(Arrays.equals(strArray, apiTestsMethods.extractArrayFromResponse(CHUCK_NORRIS_URL)), true, "Response body is wrong");
    }
}
