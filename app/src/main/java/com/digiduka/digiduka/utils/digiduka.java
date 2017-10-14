package com.digiduka.digiduka.utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by waracci on 10/14/17.
 */

public class digiduka extends Application{
    public void onCreate(){
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
