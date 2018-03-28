package com.example.kaktysig.laba_2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_name;
    private EditText edit_surname;
    private EditText edit_second_name;
    private EditText edit_date;
    private EditText edit_login;
    private EditText edit_password;
    private EditText edit_second_password;
    private Button button_register;

    private SharedPreferences preferences;

    DBHelper dbHelper;
    final String LOG_TAG = "Logs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_surname = (EditText) findViewById(R.id.edit_surname);
        edit_second_name = (EditText) findViewById(R.id.edit_second_name);
        edit_date = (EditText) findViewById(R.id.edit_date);
        edit_login = (EditText) findViewById(R.id.edit_login);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_second_password = (EditText) findViewById(R.id.edit_second_password);

        button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        String pass_1 = edit_password.getText().toString().trim();
        String pass_2 = edit_second_password.getText().toString().trim();

        if (pass_1.equalsIgnoreCase(pass_2)){

            ContentValues cv = new ContentValues();

            String name = edit_name.getText().toString().trim();
            String surname = edit_surname.getText().toString().trim();
            String second_name = edit_second_name.getText().toString().trim();
            String date = edit_date.getText().toString().trim();
            String login = edit_login.getText().toString().trim();


            SQLiteDatabase db = dbHelper.getWritableDatabase();

            cv.put("name", name);
            cv.put("surname", surname);
            cv.put("secondname", second_name);
            cv.put("date", date);
            cv.put("login", login);
            cv.put("password", pass_1);

            long rowID = db.insert("users", null, cv);
            Log.d(LOG_TAG, "row inserted, ID = " + rowID);
            dbHelper.close();

            preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("is_auth", 1);
            editor.putString("login", login);
            editor.apply();

            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
        }
    }
}
