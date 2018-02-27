package com.demo.example.questionanswercardapp.question;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.answer.AnswerFragment;
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
    private String quesTitle;
    private String _callActivityName;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionDataAdapter.ViewHolder holder, int position)
    {
        quesTitle = _questionsList.get(position).getMessage();
        holder._question.setText(quesTitle);
        RetrieveImageTask task = new RetrieveImageTask(holder._user_image);
        task.execute(_questionsList.get(position).getImage());

        _commentAdapter = new CommentAdapter(_questionsList.get(position).getCommentsVOArrayList(), _context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private CardView _cardView;
        private TextView _question, _comments, _answers;
        private ListView _listView;
        private ImageView _user_image;

        public ViewHolder(View view)
        {
            super(view);
            _cardView = view.findViewById(R.id.card_view);
            _question = (TextView) view.findViewById(R.id.question);
            _comments = view.findViewById(R.id.comments);
            _answers = view.findViewById(R.id.answers);
            _user_image = view.findViewById(R.id.user_image);

            _listView = view.findViewById(R.id.commentlist);
            _listView.setFastScrollEnabled(true);

            _cardView.setOnClickListener(this);
            _comments.setOnClickListener(this);
            if (_callActivityName.equalsIgnoreCase(QuestionFragment.TAG))
            {
                _answers.setOnClickListener(this);
                _answers.setVisibility(View.VISIBLE);
            }
            else
            {
                _answers.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.card_view:
                    Log.d("RESPONSE", "Question CLicked = " + v.getId());
                    break;
                case R.id.comments:
                    if (_listView.getVisibility() == View.VISIBLE)
                    {
                        _listView.setVisibility(View.GONE);
                    }
                    else
                    {
                        _listView.setAdapter(_commentAdapter);
                        _listView.setVisibility(View.VISIBLE);
                    }
                    Log.d("RESPONSE", "comments CLicked = " + v.getId());
                    break;
                case R.id.answers:
                    Log.d("RESPONSE", "comments CLicked = " + v.getId());
                    FragmentTransaction ft = _fragmentManager.beginTransaction();
                    AnswerFragment answerFragment = new AnswerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(AnswerFragment.QUESTION_TEXT, quesTitle);
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

    class RetrieveImageTask extends AsyncTask<String, Void, Bitmap>
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

}
