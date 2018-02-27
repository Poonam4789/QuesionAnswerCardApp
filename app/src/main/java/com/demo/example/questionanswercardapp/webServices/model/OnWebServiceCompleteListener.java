package com.demo.example.questionanswercardapp.webServices.model;


import com.demo.example.questionanswercardapp.webServices.connector.WebServiceConnector;

/**
 * Created by poonampatel on 25/02/18.
 */

public interface OnWebServiceCompleteListener
{
    void serviceComplete(WebServiceConnector connector, WebServiceConnector.ServiceDataInfo serviceInfo);
}
