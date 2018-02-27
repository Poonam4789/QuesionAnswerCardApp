package com.demo.example.questionanswercardapp.question;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.model.CommentsVO;

import java.util.ArrayList;

/**
 * Created by poonampatel on 27/02/18.
 */

public class CommentAdapter extends ArrayAdapter<CommentsVO>
{
    private ArrayList<CommentsVO> _commentsVOArrayList;
    Context mContext;

    public CommentAdapter(ArrayList<CommentsVO> data, Context context)
    {
        super(context, R.layout.comment_item, data);
        this._commentsVOArrayList = data;
        Log.d("Comments","Comments = "+_commentsVOArrayList.size());
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CommentsVO commentsVO = getItem(position);
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comment_item, parent, false);
            TextView txtName = (TextView) convertView.findViewById(R.id.commentText);
            txtName.setText(commentsVO.getComment());
        }
        return convertView;
    }

    @Override
    public int getCount()
    {
        return _commentsVOArrayList.size();
    }
}
