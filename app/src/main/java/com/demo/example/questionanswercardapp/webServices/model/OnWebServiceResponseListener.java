package com.demo.example.questionanswercardapp.webServices.model;

/**
 * Created by poonampatel on 25/02/18.
 */

public interface OnWebServiceResponseListener
{
    public void onWebServiceResponseSuccess(IWebServiceResponseVO response);
    public void onWebServiceResponseFailed(String errorMesg, int errorCode);
}
