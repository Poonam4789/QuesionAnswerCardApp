package com.demo.example.questionanswercardapp;

import android.content.Context;
import android.util.Log;

import com.demo.example.questionanswercardapp.WebServices.Util.ApplicationURL;
import com.demo.example.questionanswercardapp.WebServices.model.IWebServiceResponseVO;
import com.demo.example.questionanswercardapp.WebServices.model.OnWebServiceResponseListener;
import com.demo.example.questionanswercardapp.model.QuestionsDataProcessorVO;

/**
 * Created by poonampatel on 27/02/18.
 */

public class WebserviceController implements OnWebServiceResponseListener
{
    private static final String TAG = "WebserviceController";
    private static WebserviceController _instance;
    private Context _context;

    public static WebserviceController getInstance()
    {
        return _instance;
    }

    public static void initializeLoginController(Context context)
    {
        if (_instance == null)
        {
            _instance = new WebserviceController(context);
        }
    }

    private WebserviceController(Context context)
    {
        _context = context;
    }

    public void QuestionsApi()
    {
        Log.d("Called", "QuestionsApi Called");
        QuestionAnswerApplication.getApplicationInstance().getWebServicesManager().servicePost(this, new QuestionsDataProcessorVO(), ApplicationURL.QUESTIONS_URL);
    }

    public void AnswersApi()
    {
        Log.d("Called", "AnswersApi Called");
        QuestionAnswerApplication.getApplicationInstance().getWebServicesManager().servicePost(this, new QuestionsDataProcessorVO(), ApplicationURL.ANSWERS_URL);
    }

    @Override
    public void onWebServiceResponseSuccess(IWebServiceResponseVO response)
    {
        Log.d("RESPONSE", "success = " + response.toString());
    }

    @Override
    public void onWebServiceResponseFailed(String errorMesg, int errorCode)
    {
        Log.d("RESPONSE", "failed = " + errorMesg.toString());
    }
}
