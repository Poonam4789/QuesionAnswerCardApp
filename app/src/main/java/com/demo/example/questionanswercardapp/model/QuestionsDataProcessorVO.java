package com.demo.example.questionanswercardapp.model;

import android.util.Log;

import com.demo.example.questionanswercardapp.WebServices.Util.DataList;
import com.demo.example.questionanswercardapp.WebServices.model.IWebServiceResponseVO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by poonampatel on 27/02/18.
 */

public class QuestionsDataProcessorVO implements IWebServiceResponseVO
{
    public DataList<ResultDataVO> getResultDataVOS()
    {
        return _resultDataVOS;
    }

    private DataList<ResultDataVO> _resultDataVOS;

    @Override
    public void processResponse(Object data)
    {
        _resultDataVOS = new DataList<>();
        if (data instanceof JSONObject)
        {
            try
            {
                JSONObject obj = (JSONObject) data;
                Log.d("RESPONSE", "status" + obj.getString("status"));
//                ResultDataVO resultDataVO = new ResultDataVO(obj);
//                _resultDataVOS.add(resultDataVO);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
