package com.demo.example.questionanswercardapp.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.example.questionanswercardapp.question.model.IResponseVO;
import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.WebserviceController;
import com.demo.example.questionanswercardapp.appBase.BaseActivity;
import com.demo.example.questionanswercardapp.model.ResultDataVO;

import java.util.ArrayList;

/**
 * Created by poonampatel on 27/02/18.
 */

public class QuestionFragment extends Fragment implements IResponseVO
{
    public static final String TAG = "QuestionFragment";
    RecyclerView _questionRecyclerView;
    private QuestionDataAdapter _questionDataAdapter;
    private ProgressBar _progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.question_fragment_layout, container, false);
        ((BaseActivity) getActivity()).toggleDrawerIcon(true);
        _progressBar = (ProgressBar) view.findViewById(R.id.page_progress);

        _questionRecyclerView = (RecyclerView) view.findViewById(R.id.question_listview);

        _questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _questionRecyclerView.setItemAnimator(null);

        _progressBar.setVisibility(View.VISIBLE);
        WebserviceController.getInstance().QuestionsApi(this);
        return view;
    }

    @Override
    public void SetAdapter(ArrayList<ResultDataVO> resultDataVOArrayList)
    {
        _questionRecyclerView.setVisibility(View.VISIBLE);
        _questionDataAdapter = new QuestionDataAdapter(getContext(),TAG,getActivity().getSupportFragmentManager(), resultDataVOArrayList);
        _questionRecyclerView.setAdapter(_questionDataAdapter);
        _progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _questionRecyclerView = null;
        _questionDataAdapter = null;
    }
}
