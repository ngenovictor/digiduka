package com.digiduka.digiduka.databaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.digiduka.digiduka.models.Category;

/**
 * Created by waracci on 10/12/17.
 */
//this class extends the DatabaseHelper class we created to access the database
public class TableControllerCategory extends DatabaseHandler {
    public TableControllerCategory(Context context) {
        super(context);
    }


    //the following method is responsible for creating a new record inthe database
    public boolean create (Category category) {
        ContentValues values = new ContentValues();

        values.put("title", category.getCategoryTitle());
        values.put("description", category.getCategoryDescription());
        values.put("shopId", category.getShopId());
        values.put("date", category.getDate().toString()); //workout the date method here


        SQLiteDatabase db = getWritableDatabase();

        boolean createSuccessful = db.insert("categories", null, values) > 0;

        db.close();

        return createSuccessful;
    }

    /**
     * count method, retrieves the number of records in sql database
     * **/
//    public int count() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String sql = "SELECT * FROM categories";
//        int recordCount = db.rawQuery(sql, null).getCount();
//        db.close();
//
//        return recordCount;
//    }
}
