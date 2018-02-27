package com.demo.example.questionanswercardapp.question;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.answer.AnswerFragment;
import com.demo.example.questionanswercardapp.databinding.QuestionItemBinding;
import com.demo.example.questionanswercardapp.model.ResultDataVO;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by poonampatel on 27/02/18.
 */

public class QuestionDataAdapter extends RecyclerView.Adapter<QuestionDataAdapter.ViewHolder>
{
    FragmentManager _fragmentManager;
    private ArrayList<ResultDataVO> _questionsList;
    private CommentAdapter _commentAdapter;
    private Context _context;
    private String _callActivityName;
    private String _text;


    public QuestionDataAdapter(Context context, String tag, FragmentManager fragmentManager, ArrayList<ResultDataVO> questionList)
    {
        _fragmentManager = fragmentManager;
        _questionsList = questionList;
        _context = context;
        _callActivityName = tag;
    }

    @Override
    public QuestionDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        QuestionItemBinding questionItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.question_item, parent, false);
        return new ViewHolder(questionItemBinding);
    }

    @Override
    public void onBindViewHolder(QuestionDataAdapter.ViewHolder holder, int position)
    {
        ResultDataVO resultDataVO = getItemForPosition(position);
        holder.bind(resultDataVO);

        _commentAdapter = new CommentAdapter(resultDataVO.getCommentsVOArrayList(), _context);
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, String imageUri)
    {
        RetrieveImageTask task = new RetrieveImageTask(imageView);
        task.execute(imageUri);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private QuestionItemBinding questionItemBinding;

        public ViewHolder(QuestionItemBinding binding)
        {
            super(binding.getRoot());
            this.questionItemBinding = binding;
            questionItemBinding.setListener(this);
            questionItemBinding.comments.setOnClickListener(this);
            questionItemBinding.cardView.setOnClickListener(this);

            if (_callActivityName.equalsIgnoreCase(QuestionFragment.TAG))
            {
                questionItemBinding.answers.setOnClickListener(this);
                questionItemBinding.answers.setVisibility(View.VISIBLE);
            }
            else
            {
                questionItemBinding.answers.setVisibility(View.GONE);
            }
        }

        public void bind(ResultDataVO item)
        {
            questionItemBinding.setResultDataVO(item);
            questionItemBinding.executePendingBindings();
        }


        @Override
        public void onClick(View v)
        {
            Log.d("RESPONSE", "inside CLicked = " + v.getId());
            switch (v.getId())
            {
                case R.id.card_view:
                    Log.d("RESPONSE", "Question CLicked = " + v.getId());
                    break;
                case R.id.comments:
                    if (questionItemBinding.commentlist.getVisibility() == View.VISIBLE)
                    {
                        questionItemBinding.commentlist.setVisibility(View.GONE);
                    }
                    else
                    {
                        questionItemBinding.commentlist.setAdapter(_commentAdapter);
                        questionItemBinding.commentlist.setVisibility(View.VISIBLE);
                    }
                    Log.d("RESPONSE", "comments CLicked = " + v.getId());
                    break;
                case R.id.answers:
                    Log.d("RESPONSE", "comments CLicked = " + v.getId());
                    FragmentTransaction ft = _fragmentManager.beginTransaction();
                    AnswerFragment answerFragment = new AnswerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(AnswerFragment.QUESTION_TEXT, _text);
                    answerFragment.setArguments(bundle);
                    ft.replace(R.id.contentlayout, answerFragment, "AnswerFragment").addToBackStack("AnswerFragment").commit();
                    break;
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return _questionsList.size();
    }

    public static class RetrieveImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView _imageView;

        public RetrieveImageTask(ImageView imageView)
        {
            _imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls)
        {
            try
            {
                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp;
            }
            catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            _imageView.setImageBitmap(bitmap);
        }
    }

    public ResultDataVO getItemForPosition(int position)
    {
        _text = _questionsList.get(position).getMessage();
        return _questionsList.get(position);
    }
}
