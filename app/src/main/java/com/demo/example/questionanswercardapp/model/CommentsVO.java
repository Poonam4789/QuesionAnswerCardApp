package com.demo.example.questionanswercardapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by poonampatel on 27/02/18.
 */

public class CommentsVO
{
    private String _comment;

    public String getComment()
    {
        return _comment;
    }

    public CommentsVO(JSONObject obj)
    {
        try
        {
            _comment = obj.getString("message");
            Log.d("RESPONSE", "_comment" + _comment);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
