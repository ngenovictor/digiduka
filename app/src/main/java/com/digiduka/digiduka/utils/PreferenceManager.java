package com.digiduka.digiduka.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by waracci on 10/18/17.
 */

public class PreferenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;


    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(Constants.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(Constants.IS_FIRST_TIME_LAUNCH, true);
    }
}
