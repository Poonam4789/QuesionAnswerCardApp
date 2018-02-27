package com.demo.example.questionanswercardapp.appBase;

import android.os.Bundle;
import android.util.Log;

import com.demo.example.questionanswercardapp.question.QuestionFragment;
import com.demo.example.questionanswercardapp.R;

public class QuestionAnswerMainActivity extends BaseActivity
{
    private static final String TAG = "QuestionAnswerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer_main);
        QuestionFragment pagerFragment = new QuestionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentlayout, pagerFragment, "QuestionFragment").commit();
    }

    @Override
    protected void onNetworkChange(boolean isNetworkConnected)
    {
        showNoNetworkBar(isNetworkConnected);
    }
    @Override
    public void onBackPressed()
    {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("BackPress", "Fragmentcount = " + count);
        if (count == 0)
        {
            super.onBackPressed();
        }
        else if (count == 1)
        {
            super.onBackPressed();
            toggleDrawerIcon(true);
            settoolbarTitle(getApplicationContext().getResources().getString(R.string.app_name));
        }
        else if (count > 1)
        {
            String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            Log.d("BackPress", "Fragment = " + name);
            if ("QuestionFragment".equalsIgnoreCase(name))
            {
                super.onBackPressed();
                toggleDrawerIcon(true);
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.app_name));
            }
            else if ("AnswerFragment".equalsIgnoreCase(name))
            {
                super.onBackPressed();
                toggleDrawerIcon(false);
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.answers));

            }
        }
    }
}
