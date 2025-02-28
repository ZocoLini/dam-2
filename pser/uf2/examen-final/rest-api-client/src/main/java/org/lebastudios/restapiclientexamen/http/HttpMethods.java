package org.lebastudios.restapiclientexamen.http;

import com.google.gson.Gson;
import org.lebastudios.restapiclientexamen.httpbodies.URLEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpMethods
{
    public static String BASE_REST_API_URI = "http://localhost/examen-final/index.php/";

    private static final Gson gson = new Gson();

    public static <T> T get(String uri, Class<T> result)
    {
        try (HttpClient client = Client.defaultClient())
        {
            HttpRequest reques = HttpRequest.newBuilder().GET()
                    .uri(URI.create(BASE_REST_API_URI + uri))
                    .build();

            String body = client.send(reques, HttpResponse.BodyHandlers.ofString()).body();

            return gson.fromJson(body, result);
        }
        catch (IOException | InterruptedException e)
        {
            return null;
        }
    }

    public static boolean post(String uri, Object bodyObject)
    {
        try (HttpClient client = Client.defaultClient())
        {
            final var body = URLEncoder.encode(bodyObject);
            
            HttpRequest reques = HttpRequest.newBuilder().POST(
                            HttpRequest.BodyPublishers.ofString(body)
                    ).uri(URI.create(BASE_REST_API_URI + uri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            final var response = client.send(reques, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            
            return statusCode == 200;
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean put(String uri, Object bodyObject)
    {
        try (HttpClient client = Client.defaultClient())
        {
            HttpRequest reques = HttpRequest.newBuilder().PUT(
                            HttpRequest.BodyPublishers.ofString(URLEncoder.encode(bodyObject))
                    ).uri(URI.create(BASE_REST_API_URI + uri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            
            int statusCode = client.send(reques, HttpResponse.BodyHandlers.ofString()).statusCode();

            return statusCode == 200;
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
