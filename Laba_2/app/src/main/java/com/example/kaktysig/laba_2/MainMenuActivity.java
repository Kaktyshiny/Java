package com.example.kaktysig.laba_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_test;
    private Button button_rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        button_test = (Button) findViewById(R.id.button_test);
        button_rating = (Button) findViewById(R.id.button_rating);

        button_test.setOnClickListener(this);
        button_rating.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_test:
                Intent intent1 = new Intent(this, TestActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_rating:
                Intent intent2 = new Intent(this, RatingActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
