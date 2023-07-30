package tests.api.chucknorris.io;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;

import static io.restassured.RestAssured.when;

public class ApiTestsMethods {

    static String CHUCK_NORRIS_URL = "https://api.chucknorris.io/jokes/categories";

    public static int getResponseCode(String apiUrl) {
        int statusCode = when().get(apiUrl).getStatusCode();
        return statusCode;
    }

    public String[] extractArrayFromResponse(String apiUrl)  {
        OkHttpClient client = new OkHttpClient();

        String[] categoriesArray = new String[0];
        try {
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);

                // Convert JSONArray to a regular Java array if needed
                categoriesArray = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    categoriesArray[i] = jsonArray.getString(i);
                }

                // Now you can use the 'categoriesArray' as needed
                for (String category : categoriesArray) {
                    System.out.println(category);
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoriesArray;
    }
}