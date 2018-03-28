package com.example.kaktysig.laba_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Kaktysig on 28.02.18.
 */

public class Activity_result extends AppCompatActivity{

    private TextView text_result;
    private TextView text_right_answers;
    private TextView text_wrong_answers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        text_result = (TextView) findViewById(R.id.text_result);
        text_right_answers = (TextView) findViewById(R.id.text_right_answers);
        text_wrong_answers = (TextView) findViewById(R.id.text_wrong_answers);

        Intent intent = getIntent();
        int wrong_answers = Integer.parseInt(intent.getExtras().get("wrong_answers").toString());
        int right_answers = 5 - wrong_answers;

        if (right_answers * 100 / 5 >= 71){
            text_result.setText("Тест пройден");
        }
        else {
            text_result.setText("Тест не пройден");
        }
        text_right_answers.setText("Правильных ответов: " + right_answers);
        text_wrong_answers.setText("Неправильных ответов: " + wrong_answers);

    }
}
