package com.example.kaktysig.laba_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kaktysig on 29.03.18.
 */

public class RatingActivity extends AppCompatActivity{
    ArrayList<User> users = new ArrayList<User>();
    AdapterListView adapterListView;

    DBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        dbHelper = new DBHelper(this);

        adapterListView = new AdapterListView(this, users);

        GetData();
        final ListView listOfRating = (ListView)findViewById(R.id.users_rating);
        listOfRating.setAdapter(adapterListView);

        listOfRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView currentLogin = (TextView) view.findViewById(R.id.textLogin);

                Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
                intent.putExtra("login", currentLogin.getText());
                startActivity(intent);
            }


        });
    }

    protected void GetData() {

        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_USERS,
                null,
                null,
                null,
                null,
                null,
                DBHelper.KEY_RESULT_TEST + " DESC");

        if(cursor.moveToFirst()){
            int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RESULT_TEST);

            do{
                users.add(new User(null, null, null, cursor.getString(loginIndex),
                        String.valueOf(cursor.getString(ratingIndex) + "%")));
            }while(cursor.moveToNext());
        }else{
            Log.d("ratingListView", "0 rows");
        }

    }
}
