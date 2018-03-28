package com.example.kaktysig.laba_2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class ResultActivity extends AppCompatActivity{

    private TextView text_result;
    private TextView text_right_answers;
    private TextView text_wrong_answers;

    DBHelper dbHelper;
    SQLiteDatabase db;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        text_result = (TextView) findViewById(R.id.text_result);
        text_right_answers = (TextView) findViewById(R.id.text_right_answers);
        text_wrong_answers = (TextView) findViewById(R.id.text_wrong_answers);

        Intent intent = getIntent();
        int wrong_answers = Integer.parseInt(intent.getExtras().get("wrong_answers").toString());
        int right_answers = 7 - wrong_answers;
        int result = right_answers * 100 / 7;

        if (result >= 71){
            text_result.setText("Тест пройден. Ваш результат: " + result + "%");
        }
        else {
            text_result.setText("Тест не пройден. Ваш результат: " + result + "%");
        }
        text_right_answers.setText("Правильных ответов: " + right_answers);
        text_wrong_answers.setText("Неправильных ответов: " + wrong_answers);

        dbHelper = new DBHelper(this);

        String answers = intent.getExtras().get("answers").toString();
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        String login = preferences.getString("login", "");

        db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.KEY_RESULT_TEST, result);
        cv.put(DBHelper.KEY_ARR_ANSWERS, answers);
        db.update(DBHelper.TABLE_USERS, cv, "login = ?", new String[]{login});
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit:
                preferences.edit().remove("is_auth").apply();
                Intent exit_intent = new Intent(this, MainActivity.class);
                startActivity(exit_intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
