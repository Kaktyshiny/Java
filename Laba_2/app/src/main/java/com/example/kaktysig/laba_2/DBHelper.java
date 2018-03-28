package com.example.kaktysig.laba_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    // constant for update DB
    public static final int DATABESE_VERSION = 1;
    public static final String DATABASE_NAME = "LabaTwo";
    public static final String TABLE_USERS = "users";

    // create field of table
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_SECOND_NAME = "secondname";
    public static final String KEY_DATE = "date";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASS = "password";
    public static final String KEY_RESULT_TEST = "result_test";
    public static final String KEY_ARR_ANSWERS = "all_answers_user";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABESE_VERSION);
    }

    // call for create DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + "("
                + KEY_ID + " integer primary key,"
                + KEY_NAME + " text,"
                + KEY_SURNAME + " text,"
                + KEY_SECOND_NAME + " text,"
                + KEY_DATE + " text,"
                + KEY_LOGIN + " text,"
                + KEY_PASS + " text,"
                + KEY_RESULT_TEST + " integer,"
                + KEY_ARR_ANSWERS + " text" + ")");
    }


    // call when you need to update db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);

        onCreate(db);
    }
}