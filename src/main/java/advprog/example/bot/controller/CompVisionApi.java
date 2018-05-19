package advprog.example.bot.controller;

import java.io.InputStream;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CompVisionApi {

    public static final String uriBase = "https://southeastasia.api.cognitive.microsoft.com/vision/v1.0/recognizeText?handwriting=true";

    public static String extractHandwriting(InputStream binaryImage) {
        HttpClient textClient = new DefaultHttpClient();
        HttpClient resultClient = new DefaultHttpClient();
        String result = "";

        try {
            // This operation requrires two REST API calls.
            // One to submit the image for processing,
            // the other to retrieve the text found in the image.
            //
            // Begin the REST API call to submit the image for processing.
            URI uri = new URI(uriBase);
            HttpPost textRequest = new HttpPost(uri);

            // Content type is application/octet-stream for binary image file
            textRequest.setHeader("Content-Type", "application/octet-stream");
            textRequest.setHeader("Ocp-Apim-Subscription-Key", GlobalValue.subscriptionKey);

            // Request body. Use InputStreamEntity.
            InputStreamEntity requestEntity = new InputStreamEntity(binaryImage, -1);
            textRequest.setEntity(requestEntity);
            HttpResponse textResponse = textClient.execute(textRequest);

            // Check for success.
            if (textResponse.getStatusLine().getStatusCode() != 202) {
                // Format and display the JSON error message.
                HttpEntity entity = textResponse.getEntity();
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("Error:\n");
                System.out.println(json.toString(2));
                // result = json.toString(2);
                return "Error.";
            }

            String operationLocation = null;

            // The 'Operation-Location' in the response contains
            // the URI to retrieve the recognized text.
            Header[] responseHeaders = textResponse.getAllHeaders();
            for (Header header : responseHeaders) {
                if (header.getName().equals("Operation-Location")) {
                    // This string is the URI where you can get the
                    // text recognition operation result.
                    operationLocation = header.getValue();
                    break;
                }
            }

            System.out.println("\nHandwritten text submitted. Waiting 10 seconds to "
                    + "retrieve the recognized text.\n");
            Thread.sleep(10000);

            // Execute the second REST API call and get the response.
            HttpGet resultRequest = new HttpGet(operationLocation);
            resultRequest.setHeader("Ocp-Apim-Subscription-Key", GlobalValue.subscriptionKey);

            HttpResponse resultResponse = resultClient.execute(resultRequest);
            HttpEntity responseEntity = resultResponse.getEntity();

            if (responseEntity != null) {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(responseEntity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("Text recognition result response: \n");
                System.out.println(json.toString(2));
                result = json.toString(2);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }
}