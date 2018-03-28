package com.example.kaktysig.laba_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_pass;
    private Button button_entry;

    private final String ADMIN_PASSWORD = "admin";

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_pass = (EditText) findViewById(R.id.edit_pass);
        button_entry = (Button) findViewById(R.id.button_entry);
        button_entry.setOnClickListener(this);

        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        int is_auth = preferences.getInt("is_auth",0);
        if (is_auth == 1){
            String pass = preferences.getString("key_pass", "");

            Intent intent = new Intent(this, Activity_questions.class);
            intent.putExtra("key_pass", pass);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        if (!edit_pass.getText().toString().trim().equalsIgnoreCase(ADMIN_PASSWORD)){
            Toast.makeText(this, "Вы ввели неверный пароль", Toast.LENGTH_LONG).show();
        }
        else {
            String pass = preferences.getString("key_pass","");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("is_auth",1);
            editor.putString("key_pass", pass);
            editor.apply();


            Intent intent = new Intent( this, Activity_questions.class);
            startActivity(intent);
        }
    }
}
