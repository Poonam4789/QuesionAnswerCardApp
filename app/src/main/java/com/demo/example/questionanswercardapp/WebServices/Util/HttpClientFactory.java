package com.demo.example.questionanswercardapp.WebServices.Util;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

/**
 * Created by poonampatel on 25/02/18.
 */

public class HttpClientFactory
{
    private static DefaultHttpClient __client;
    public static void resetConnection()
    {
        __client = null;
    }

    private static void regenerate()
    {
        __client = new DefaultHttpClient();
        ClientConnectionManager mgr = __client.getConnectionManager();
        HttpParams params = __client.getParams();
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry());
        __client = new DefaultHttpClient(threadSafeClientConnManager, params);
    }

    public static DefaultHttpClient getThreadSafeClient()
    {
        if(__client != null)
        {
            return __client;
        }
        regenerate();
        return __client;
    }
}
