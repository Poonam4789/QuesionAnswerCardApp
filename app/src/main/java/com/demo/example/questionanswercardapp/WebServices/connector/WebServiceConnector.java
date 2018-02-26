package com.demo.example.questionanswercardapp.WebServices.connector;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.demo.example.questionanswercardapp.WebServices.Error.HttpConnetionError;
import com.demo.example.questionanswercardapp.WebServices.Util.HttpClientFactory;
import com.demo.example.questionanswercardapp.WebServices.model.IWebServiceResponseVO;
import com.demo.example.questionanswercardapp.WebServices.model.OnWebServiceCompleteListener;
import com.demo.example.questionanswercardapp.WebServices.model.OnWebServiceResponseListener;

/**
 * Created by poonampatel on 25/02/18.
 */

public final class WebServiceConnector
{
    public enum ContentType
    {
        NameValue(1), Json(2);

        private int _type;

        private ContentType(int type)
        {
            _type = type;
        }

        public int getCode()
        {
            return _type;
        }
    }

    private OnWebServiceCompleteListener _listener;
    private AsyncTask<ServiceDataInfo, Void, ServiceDataInfo> _task;
    private Context _context;

    WebServiceConnector(OnWebServiceCompleteListener listener, Context context)
    {
        _context = context;
        _listener = listener;
    }

    void serviceGet(ServiceDataInfo info)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            _task = new FetchData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, info);
        }
        else
        {
            _task = new FetchData().execute(info);
        }
    }

    void servicePost(ServiceDataInfo info)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            _task = new FetchData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, info);
        }
        else
        {
            _task = new FetchData().execute(info);
        }
    }
    private class FetchData extends AsyncTask<ServiceDataInfo, Void, ServiceDataInfo>
    {

        @Override
        protected ServiceDataInfo doInBackground(ServiceDataInfo... params)
        {
            ServiceDataInfo info = params[0];
            try
            {
                if (info.getType() == WebServicesManager.Method.GET)
                {
                    String data = getService(info);

                        Object obj = new JSONTokener(data).nextValue();
                        info.getResponseVO().processResponse(obj);
                }
                else
                {
                    String data = null;
                    try
                    {
                        data = postService(info);
                    }
                    catch (Exception exception)
                    {
                        data = postService(info);
                    }


                        Object obj = new JSONTokener(data).nextValue();
                        info.getResponseVO().processResponse(obj);
                }
                info._isSuccess = true;
            }
            catch (ClientProtocolException e)
            {
                info._isSuccess = false;
                info._errorMesg = e.getMessage();
                info._errorCode = 5001;
            }
            catch (IOException e)
            {
                info._isSuccess = false;
                info._errorMesg = e.getMessage();
                info._errorCode = 5001;
            }
            catch (URISyntaxException e)
            {
                info._isSuccess = false;
                info._errorMesg = e.getMessage();
                info._errorCode = 5001;
            }
            catch (HttpConnetionError e)
            {
                HttpClientFactory.resetConnection();
                info._isSuccess = false;
                info._errorMesg = e.getMessage();
                info._errorCode = e.getStatusCode();
            }
            catch (JSONException e)
            {
                info._isSuccess = false;
                info._errorMesg = e.getMessage();
                info._errorCode = 5001;
            }
            return info;
        }

        @Override
        protected void onPostExecute(ServiceDataInfo result)
        {
            super.onPostExecute(result);
            _listener.serviceComplete(WebServiceConnector.this, result);
        }
    }
    private HttpClient getHTTPClient()
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();

        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        HttpConnectionParams.setSoTimeout(httpParams, 30000);
        return httpClient;
    }
    private String getService(ServiceDataInfo info) throws IOException, URISyntaxException, HttpConnetionError, JSONException
    {

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = getHTTPClient();

        HttpGet httpGet = null;
        if (info.getContentType() == ContentType.NameValue)
        {
            List<NameValuePair> params = info.getParamsNameValue();
            if (params != null)
            {
                URI uri = new URI(info.getUrl() + "?" + URLEncodedUtils.format(params, "utf-8"));
                httpGet = new HttpGet(uri);
            }
            else
            {
                httpGet = new HttpGet(info.getUrl());
            }
        }

        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200)
        {
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            inputStream.close();
            Log.d("Response JSON","stringBuilder = "+stringBuilder.toString());
        }
        else
        {
            throw new HttpConnetionError(statusCode);
        }
        return stringBuilder.toString();
    }

    private String postService(ServiceDataInfo info) throws IOException, HttpConnetionError, JSONException
    {
        if (info.getContentType() == ContentType.NameValue)
        {
            return postServiceNameValuePair(info);
        }
        return postServiceJson(info);
    }
    private String postServiceNameValuePair(ServiceDataInfo info) throws IOException, HttpConnetionError,JSONException
    {

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = getHTTPClient();

        HttpPost post = new HttpPost(info.getUrl());
        post.setHeader("Accept", "application/json");
        post.setHeader("content-type", "application/x-www-form-urlencoded");

        if (info.getParamsNameValue() != null)
        {
            post.setEntity(new UrlEncodedFormEntity(info.getParamsNameValue()));
        }
        HttpResponse response = httpClient.execute(post);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            inputStream.close();
        }
        if (statusCode != 200)
        {
            throw new HttpConnetionError(statusCode);
        }
        String str = stringBuilder.toString();
        return str;
    }

    private String postServiceJson(ServiceDataInfo info) throws IOException, HttpConnetionError,JSONException
    {

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = getHTTPClient();

        HttpPost post = new HttpPost(info.getUrl());
        post.setHeader("Accept", "application/json");
        post.setHeader("content-type", "application/json");

        if (info.getParamJson() != null)
        {
            post.setEntity(new StringEntity(info.getParamJson().toString(), "UTF-8"));
        }

        HttpResponse response = httpClient.execute(post);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            inputStream.close();
        }
         if (statusCode != 200)
        {
            throw new HttpConnetionError(statusCode);
        }
        String str = stringBuilder.toString();
        return str;
    }

   public class ServiceDataInfo
    {
        private WeakReference<OnWebServiceResponseListener> _listener;
        private WebServicesManager.Method _type;
        private String _url;
        private List<NameValuePair> _paramsNameValue;
        private JSONObject _paramJson;
        private IWebServiceResponseVO _responseVO;
        private boolean _isSuccess;
        private String _errorMesg;
        private int _errorCode;
        private ContentType _contentType;

        ServiceDataInfo(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url, List<NameValuePair> paramsForGet, WebServicesManager.Method method)
        {
            _listener = new WeakReference<OnWebServiceResponseListener>(listener);
            _responseVO = responseVO;
            _url = url;
            _paramsNameValue = paramsForGet;
            _type = method;
            _paramJson = null;
            _isSuccess = true;
            _errorMesg = null;
            _contentType = ContentType.NameValue;
        }

        ServiceDataInfo(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url, JSONObject paramForPost)
        {
            _listener = new WeakReference<OnWebServiceResponseListener>(listener);
            _responseVO = responseVO;
            _url = url;
            _paramsNameValue = null;
            _type = WebServicesManager.Method.Post;
            _paramJson = paramForPost;
            _isSuccess = true;
            _errorMesg = null;
            _contentType = ContentType.Json;
        }

        WeakReference<OnWebServiceResponseListener> getListener()
        {
            return _listener;
        }

        WebServicesManager.Method getType()
        {
            return _type;
        }

        String getUrl()
        {
            return _url;
        }

        List<NameValuePair> getParamsNameValue()
        {
            return _paramsNameValue;
        }

        JSONObject getParamJson()
        {
            return _paramJson;
        }

        IWebServiceResponseVO getResponseVO()
        {
            return _responseVO;
        }

        boolean isSuccess()
        {
            return _isSuccess;
        }

        String getErrorMesg()
        {
            return _errorMesg;
        }

        int getErrorCode()
        {
            return _errorCode;
        }

        ContentType getContentType()
        {
            return _contentType;
        }
    }

    void destroy()
    {
        _listener = null;
        _context = null;
        try
        {
            if (_task != null && !_task.isCancelled())
            {
                _task.cancel(true);
                _task = null;
            }
        }
        catch (Exception ex)
        {

        }
        _task = null;
    }
}
