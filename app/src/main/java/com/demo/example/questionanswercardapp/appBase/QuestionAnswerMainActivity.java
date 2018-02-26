package com.demo.example.questionanswercardapp.appBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.WebserviceController;

public class QuestionAnswerMainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer_main);
        WebserviceController.getInstance().QuestionsApi();
       // WebserviceController.getInstance().AnswersApi();
    }
}
