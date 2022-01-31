package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Language_SharedPref {
    private String SHARED_PREF_NAME = "LANGUAGE";
    private String SELECTED_LANGUAGE = "SELECTED_LANGUAGE";

    private static Language_SharedPref mInstance;
    private static Context mCtx;

    private Language_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized Language_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Language_SharedPref(context);
        }
        return mInstance;
    }

    public boolean savelanguage(String selected_language) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_LANGUAGE, selected_language);
        editor.apply();
        return true;
    }

    public String getSELECTED_LANGUAGE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_LANGUAGE, "en");
    }
    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }
}