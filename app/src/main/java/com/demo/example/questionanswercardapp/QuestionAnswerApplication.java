package com.demo.example.questionanswercardapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.demo.example.questionanswercardapp.webServices.connector.WebServicesManager;
import com.demo.example.questionanswercardapp.network.NetworkChangeReceiver;

/**
 * Created by poonampatel on 27/02/18.
 */

public class QuestionAnswerApplication extends Application
{
    public static final String DEVELOPMENT_BUILD = "development", PRODUCTION_BUILD = "production";
    private static QuestionAnswerApplication _questionAnswerApplication;
    private WebServicesManager _webServicesManager;
    public static QuestionAnswerApplication getApplicationInstance()
    {
        return _questionAnswerApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("Called",  "Application Called");
        _questionAnswerApplication = this;
        _webServicesManager = new WebServicesManager(getApplicationContext());
        NetworkChangeReceiver.initNetworkChange(getApplicationContext());
        WebserviceController.initializeLoginController(getApplicationContext());
    }

    public Context getApplicationContext()
    {
        return this;
    }

    public WebServicesManager getWebServicesManager()
    {
        return _webServicesManager;
    }

}
