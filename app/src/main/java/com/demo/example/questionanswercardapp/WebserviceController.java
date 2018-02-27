package com.demo.example.questionanswercardapp;

import android.content.Context;
import android.util.Log;

import com.demo.example.questionanswercardapp.question.model.IResponseVO;
import com.demo.example.questionanswercardapp.webServices.Util.ApplicationURL;
import com.demo.example.questionanswercardapp.webServices.model.IWebServiceResponseVO;
import com.demo.example.questionanswercardapp.webServices.model.OnWebServiceResponseListener;
import com.demo.example.questionanswercardapp.model.QuestionsDataProcessorVO;

/**
 * Created by poonampatel on 27/02/18.
 */

public class WebserviceController implements OnWebServiceResponseListener
{
    private static final String TAG = "WebserviceController";
    private static WebserviceController _instance;
    private IResponseVO _iResponseVOWeakReference;
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

    public void QuestionsApi(IResponseVO listener)
    {
        _iResponseVOWeakReference = listener;
        Log.d("Called", "QuestionsApi Called");
        QuestionAnswerApplication.getApplicationInstance().getWebServicesManager().serviceGet(this, new QuestionsDataProcessorVO(), ApplicationURL.QUESTIONS_URL);
    }

    public void AnswersApi(IResponseVO listener)
    {
        _iResponseVOWeakReference = listener;
        Log.d("Called", "AnswersApi Called");
        QuestionAnswerApplication.getApplicationInstance().getWebServicesManager().serviceGet(this, new QuestionsDataProcessorVO(), ApplicationURL.ANSWERS_URL);
    }

    @Override
    public void onWebServiceResponseSuccess(IWebServiceResponseVO response)
    {
        Log.d("RESPONSE", "success = " + response.toString());
        if (response instanceof QuestionsDataProcessorVO)
        {
            if (response != null)
            {
                _iResponseVOWeakReference.SetAdapter(((QuestionsDataProcessorVO) response).getResultDataVOSList());
                Log.d("RESPONSE", "status = " + ((QuestionsDataProcessorVO) response).getStatus().toString());
                Log.d("RESPONSE", "message = " + ((QuestionsDataProcessorVO) response).getMessage().toString());
                Log.d("RESPONSE", "result = " + ((QuestionsDataProcessorVO) response).getResultDataVOSList().toString());
                Log.d("RESPONSE", "comments = " + ((QuestionsDataProcessorVO) response).getResultDataVOSList().get(0).getCommentsVOArrayList().toString());
                Log.d("RESPONSE", "comment message = " + ((QuestionsDataProcessorVO) response).getResultDataVOSList().get(0).getCommentsVOArrayList().get(0).getComment().toString());
            }
        }
    }

    @Override
    public void onWebServiceResponseFailed(String errorMesg, int errorCode)
    {
        Log.d("RESPONSE", "failed = " + errorMesg.toString());
    }
}
