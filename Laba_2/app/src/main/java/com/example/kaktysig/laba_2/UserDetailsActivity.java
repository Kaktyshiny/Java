package com.example.kaktysig.laba_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kaktysig on 29.03.18.
 */

public class UserDetailsActivity extends AppCompatActivity{

    private TextView text_user_name;
    private TextView text_result;
    private TextView text_answers;

    DBHelper dbHelper;

    public Questions[] questions_array = TestActivity.questions_array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String login = intent.getStringExtra("login");

        text_user_name = findViewById(R.id.text_user_name);
        text_result = findViewById(R.id.text_result);
        text_answers = findViewById(R.id.text_answers);

        dbHelper = new DBHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DBHelper.TABLE_USERS, null, "login = ?", new String[]{login}, null, null, null);

        if(c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(DBHelper.KEY_NAME));
            String surname = c.getString(c.getColumnIndex(DBHelper.KEY_SURNAME));
            String second_name = c.getString(c.getColumnIndex(DBHelper.KEY_SECOND_NAME));
            String rating = c.getString(c.getColumnIndex(DBHelper.KEY_RESULT_TEST));
            String answers = c.getString(c.getColumnIndex(DBHelper.KEY_ARR_ANSWERS));

            text_user_name.setText(name + " " + surname + " " + second_name);
            text_result.setText("Результат: " + rating + "%");

            String[] subStr = answers.split("\\|"); // Разделения строки str с помощью метода split()

            String prepstr = "";

            for (int i = 0; i < 7; i++){
                prepstr += "Вопрос: " + questions_array[i].getText() + "\r\n" + "Ответ: " + subStr[i] + "\r\n" + "\r\n";
            }

            text_answers.setText(prepstr);

        }
        c.close();
        dbHelper.close();

    }
}
