package com.example.kaktysig.laba_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_entry;
    private Button button_register;
    private EditText edit_password;
    private EditText edit_login;

    DBHelper dbHelper;
    SQLiteDatabase db;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_login = (EditText) findViewById(R.id.edit_login);
        edit_password = (EditText) findViewById(R.id.edit_password);
        button_entry = (Button) findViewById(R.id.button_entry);
        button_entry.setOnClickListener(this);

        button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        int is_auth = preferences.getInt("is_auth", 0);
        if (is_auth == 1) {
            String pass = preferences.getString("key_pass", "");

            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("key_pass", pass);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_entry:
                String login = edit_login.getText().toString().trim();
                String pass = edit_password.getText().toString().trim();
                db = dbHelper.getWritableDatabase();

                Cursor c = db.query("users", null, null, null, null, null, null);

                int db_login = c.getColumnIndex("login");
                int db_pass = c.getColumnIndex("password");

                if(c.moveToFirst()) {
                    if (login.equals(c.getString(db_login)) && pass.equals(c.getString(db_pass))) {
                        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("is_auth", 1);
                        editor.putString("login", login);
                        editor.apply();

                        Intent intent = new Intent(this, MainMenuActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                    }
                }

                c.close();
                dbHelper.close();
                break;
            case R.id.button_register:
                Intent intent2 = new Intent(this, RegistrationActivity.class);
                startActivity(intent2);
                break;

        }
    }
}

