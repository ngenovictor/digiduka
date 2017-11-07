package com.digiduka.digiduka.databaseHandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by waracci on 10/12/17.
 */

//This is the class that handles all Sqlite database connnections

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "digiduka";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String sql = "CREATE TABLE categories " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "description TEXT, " +
                "categoryId TEXT, " +
                "shopId TEXT, " +
                "date TEXT )";

        Log.v("sql string createdb", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion) {
        String sql = "DROP TABLE IF EXISTS categories";
        db.execSQL(sql);

        onCreate(db);

    }
}
