package tests.api;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;

public class HttpClientTest {
    static Logger logger = LogManager.getLogger(HttpClientTest.class);

    @Test
    public void simpleApiTest() throws IOException {
        String restUrl = "https://api.chucknorris.io/";

        //Creating an Object and passing URL
        HttpUriRequest request = new HttpGet(restUrl);

        //Send the request
        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        logger.info(request);
        logger.info(httpResponse.toString());

        System.out.println(request);
        System.out.println(restUrl);

    }
}
