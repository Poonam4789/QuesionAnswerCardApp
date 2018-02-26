package com.demo.example.questionanswercardapp.model;

import android.util.Log;

import com.demo.example.questionanswercardapp.WebServices.Util.DataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by poonampatel on 27/02/18.
 */

public class ResultDataVO
{
    private String _id;
    private String _user_id;
    private String _message;
    private String _image;
    private CommentsVO _commentsVO;
    private DataList<CommentsVO> _commentsVOArrayList;

    public ResultDataVO(JSONObject obj)
    {
        JSONArray _commentArrayList = new JSONArray();
        _commentsVOArrayList = new DataList<>();
        try
        {
            _id = obj.getString("id");
            _user_id = obj.getString("user_id");
            _message = obj.getString("message");
            _image = obj.getString("image");
            _commentArrayList = obj.getJSONArray("comments");
            int length = _commentArrayList.length();
            if(length!=0)
            {
                for(int i =0;i<length; i++)
                {
                    _commentsVO = new CommentsVO(_commentArrayList.getJSONObject(i));
                    _commentsVOArrayList.add(_commentsVO);
                }
            }
            Log.d("RESPONSE", "_commentsVOArrayList" + _commentsVOArrayList.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getId()
    {
        return _id;
    }

    public String getUser_id()
    {
        return _user_id;
    }

    public String getMessage()
    {
        return _message;
    }

    public String getImage()
    {
        return _image;
    }

    public CommentsVO getCommentsVO()
    {
        return _commentsVO;
    }

    public DataList<CommentsVO> getCommentsVOArrayList()
    {
        return _commentsVOArrayList;
    }
}
