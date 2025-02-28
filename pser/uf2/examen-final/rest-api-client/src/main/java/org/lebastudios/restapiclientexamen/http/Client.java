package org.lebastudios.restapiclientexamen.http;

import java.net.http.HttpClient;

public class Client
{
    public static HttpClient defaultClient()
    {
        return HttpClient.newHttpClient();
    }
}
