package com.demo.example.questionanswercardapp.answer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.WebserviceController;
import com.demo.example.questionanswercardapp.appBase.BaseActivity;
import com.demo.example.questionanswercardapp.model.ResultDataVO;
import com.demo.example.questionanswercardapp.question.QuestionDataAdapter;
import com.demo.example.questionanswercardapp.question.model.IResponseVO;

import java.util.ArrayList;

/**
 * Created by poonampatel on 27/02/18.
 */

public class AnswerFragment extends Fragment implements IResponseVO
{
    public static final String TAG = "AnswerFragment";
    public static final String QUESTION_TEXT = "question_text";

    RecyclerView _answerRecyclerView;
    TextView _questionTitle;
    private QuestionDataAdapter _answerDataAdapter;
    private ProgressBar _progressBar;
    private String _questionText;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).settoolbarTitle(getString(R.string.answers));
        ((BaseActivity)getActivity()).toggleDrawerIcon(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.answer_fragment_layout, container, false);
        ((BaseActivity) getActivity()).toggleDrawerIcon(true);
        _progressBar = (ProgressBar) view.findViewById(R.id.page_progress);
        _questionTitle = view.findViewById(R.id.questionTitle);
        _answerRecyclerView = (RecyclerView) view.findViewById(R.id.answer_listview);
        _questionText = getArguments().getString(QUESTION_TEXT);
        _answerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _answerRecyclerView.setItemAnimator(null);

        _progressBar.setVisibility(View.VISIBLE);
        WebserviceController.getInstance().AnswersApi(this);
        return view;
    }

    @Override
    public void SetAdapter(ArrayList<ResultDataVO> resultDataVOArrayList)
    {
        _answerRecyclerView.setVisibility(View.VISIBLE);
        _questionTitle.setText(_questionText);
        _answerDataAdapter = new QuestionDataAdapter(getContext(),TAG, getActivity().getSupportFragmentManager(), resultDataVOArrayList);
        _answerRecyclerView.setAdapter(_answerDataAdapter);
        _progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _answerRecyclerView = null;
        _answerDataAdapter = null;
    }
}
