package com.demo.example.questionanswercardapp.question;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.databinding.CommentItemBinding;
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
        Log.d("Comments", "Comments = " + _commentsVOArrayList.size());
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CommentsVO commentsVO = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        CommentItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.comment_item, parent, false);
        binding.setCommentsVO(commentsVO);
        return binding.getRoot();
    }

    @Override
    public int getCount()
    {
        return _commentsVOArrayList.size();
    }
}
