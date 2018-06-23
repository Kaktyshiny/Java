package com.example.kaktysig.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final String ADMIN_PASSWORD = "admin";

    private EditText input_password;
    private Button button_login;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE);
        int is_auth = preferences.getInt("is_auth", 0);

        if (is_auth == 1){

            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);

        }

        input_password = (EditText) findViewById(R.id.editPassword);
        button_login = (Button) findViewById(R.id.buttonLogin);

        button_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String input_text = input_password.getText().toString().trim();

        if (input_text.equals(ADMIN_PASSWORD)){

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("is_auth", 1);
            editor.apply();

            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "Правильный пароль: admin", Toast.LENGTH_LONG).show();
        }
    }
}
