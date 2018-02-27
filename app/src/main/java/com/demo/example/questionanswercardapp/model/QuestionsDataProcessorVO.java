package com.demo.example.questionanswercardapp.model;

import android.util.Log;

import com.demo.example.questionanswercardapp.webServices.Util.DataList;
import com.demo.example.questionanswercardapp.webServices.model.IWebServiceResponseVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by poonampatel on 27/02/18.
 */

public class QuestionsDataProcessorVO implements IWebServiceResponseVO
{
    ResultDataVO _resultDataVO;
    private ArrayList<ResultDataVO> _resultDataVOSList;
    private String _status;
    private String _message;

    @Override
    public void processResponse(Object data)
    {
        _resultDataVOSList = new DataList<>();
        JSONArray _responseDataList = new JSONArray();
        if (data instanceof JSONObject)
        {
            try
            {
                JSONObject obj = (JSONObject) data;
                Log.d("RESPONSE", "status" + obj.getString("status"));
                _status = obj.optString("status");
                _message = obj.optString("message");
                _responseDataList = obj.getJSONArray("response");
                int length = _responseDataList.length();
                if(length!=0)
                {
                    for(int i =0;i<length; i++)
                    {
                        _resultDataVO = new ResultDataVO(_responseDataList.getJSONObject(i));
                        _resultDataVOSList.add(_resultDataVO);
                    }
                }
                Log.d("RESPONSE", "_resultDataVOSList" + _resultDataVOSList.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ResultDataVO> getResultDataVOSList()
    {
        return _resultDataVOSList;
    }

    public String getStatus()
    {
        return _status;
    }

    public String getMessage()
    {
        return _message;
    }
}
