package com.example.kaktysig.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kaktysig on 23.06.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    // constant for update DB
    public static final int DATABESE_VERSION = 1;
    public static final String DATABASE_NAME = "MarvelAPI";
    public static final String TABLE_NAME = "heroes";

    // create field of table
    public static final String KEY_ID = "_id";
    public static final String KEY_JSON = "json";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABESE_VERSION);
    }

    // call for create DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + KEY_ID + " integer primary key,"
                + KEY_JSON + " text"
                + ")");
    }


    // call when you need to update db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(db);
    }
}
