package test.market.function;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;

public class CodingTest {
    public static void main(String[] args) {
        String url = "https://esi.evetech.net/latest/universe/names/?datasource=tranquility";
        String[] ids = { "22" };

        // Create the JSON payload
        String payload = Arrays.toString(ids);

        // Create the URI
        URI uri = URI.create(url);

        // Create the HTTP client
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create the HTTP request with the POST method, URI, headers, and payload
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            // Send the HTTP request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response status code
            int statusCode = response.statusCode();
            System.out.println("Response status code: " + statusCode);

            // Print the response headers
            HttpHeaders headers = response.headers();
            System.out.println("Response headers: " + headers);

            // Print the response body
            String responseBody = response.body();
            System.out.println("Response body: " + responseBody);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}
