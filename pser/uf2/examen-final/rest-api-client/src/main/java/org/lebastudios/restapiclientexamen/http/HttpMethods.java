package org.lebastudios.restapiclientexamen.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpMethods
{
    public static <T> T get(String uri, Class<T> result)
    {
        try (HttpClient client = Client.defaultClient())
        {
            HttpRequest reques = HttpRequest.newBuilder().GET()
                    .uri(URI.create(uri))
                    .build();

            String body = client.send(reques, HttpResponse.BodyHandlers.ofString()).body();

            return new Gson().fromJson(body, result);
        }
        catch (IOException | InterruptedException e)
        {
            return null;
        }
    }
}
