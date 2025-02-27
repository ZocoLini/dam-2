package org.lebastudios.restapiclientexamen;

import java.net.http.HttpClient;

public class Client
{
    public static HttpClient defaultClient()
    {
        return HttpClient.newHttpClient();
    }
}
