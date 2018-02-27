package com.demo.example.questionanswercardapp.webServices.connector;

import android.content.Context;

import com.demo.example.questionanswercardapp.webServices.model.IWebServiceResponseVO;
import com.demo.example.questionanswercardapp.webServices.model.OnWebServiceCompleteListener;
import com.demo.example.questionanswercardapp.webServices.model.OnWebServiceResponseListener;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poonampatel on 25/02/18.
 */

public class WebServicesManager implements OnWebServiceCompleteListener
{
    private ArrayList<WebServiceConnector> _connectors;
    private Context _context;

    public WebServicesManager(Context context)
    {
        _context = context;
        _connectors = new ArrayList<WebServiceConnector>();
    }

    public enum RequestContentType
    {
        NameValue(1), Json(2);

        private int _type;

        private RequestContentType(int type)
        {
            _type = type;
        }

        public int getCode()
        {
            return _type;
        }
    }

    public enum Method
    {
        GET(1),Post(2);

        private int _type;

        private Method(int type)
        {
            _type = type;
        }

        public int getCode()
        {
            return _type;
        }
    }

    public void serviceGet(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url)
    {
        this.serviceGet(listener, responseVO, url, null);
    }

    public void serviceGet(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url, List<NameValuePair> paramsForGet)
    {
        WebServiceConnector connector = new WebServiceConnector(this,_context);
        _connectors.add(connector);
        connector.serviceGet(connector.new ServiceDataInfo(listener, responseVO, url, paramsForGet, Method.GET));
    }

    public void servicePost(OnWebServiceResponseListener listener, IWebServiceResponseVO vo, String url)
    {
        ArrayList<NameValuePair> nameValueServicesParams = new ArrayList<NameValuePair>();
        servicePost(listener, vo, url, nameValueServicesParams);
    }

    public void servicePost(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url, List<NameValuePair> params)
    {
        WebServiceConnector connector = new WebServiceConnector(this,_context);
        connector.servicePost(connector.new ServiceDataInfo(listener, responseVO, url, params, Method.Post));
        _connectors.add(connector);
    }

    public void servicePost(OnWebServiceResponseListener listener, IWebServiceResponseVO responseVO, String url, JSONObject json)
    {
        WebServiceConnector connector = new WebServiceConnector(this,_context);
        connector.servicePost(connector.new ServiceDataInfo(listener, responseVO, url, json));
        _connectors.add(connector);
    }


    @Override
    public void serviceComplete(WebServiceConnector connector, WebServiceConnector.ServiceDataInfo serviceInfo)
    {
        OnWebServiceResponseListener listener = serviceInfo.getListener().get();
        if(listener != null)
        {
            if(serviceInfo.isSuccess())
            {
                listener.onWebServiceResponseSuccess(serviceInfo.getResponseVO());
            }
            else
            {
                listener.onWebServiceResponseFailed(serviceInfo.getErrorMesg(), serviceInfo.getErrorCode());
            }
        }

        _connectors.remove(connector);
        connector.destroy();
    }
}
