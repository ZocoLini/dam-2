package org.lebastudios.restapiclientexamen.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.lebastudios.restapiclientexamen.httpbodies.URLEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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

    public static <T> List<T> getArray(String uri, Class<T> result)
    {
        try (HttpClient client = Client.defaultClient())
        {
            HttpRequest reques = HttpRequest.newBuilder().GET()
                    .uri(URI.create(BASE_REST_API_URI + uri))
                    .build();

            String body = client.send(reques, HttpResponse.BodyHandlers.ofString()).body();

            JsonArray array = JsonParser.parseString(body).getAsJsonArray();

            return array.asList().stream().map(element -> gson.fromJson(element, result)).toList();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean post(String uri, Object bodyObject)
    {
        try (HttpClient client = Client.defaultClient())
        {
            HttpRequest reques = HttpRequest.newBuilder().POST(
                            HttpRequest.BodyPublishers.ofString(URLEncoder.encode(bodyObject))
                    ).uri(URI.create(BASE_REST_API_URI + uri))
                    .build();

            // TODO: Meter el header de url enconded
            
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
