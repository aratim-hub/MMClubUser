package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class ChangeSponsor_SharedPref {
    private String SHARED_PREF_NAME = "TESTMESSEGE";
    private String LINK_REQUEST = "LINK_REQUEST";
    private String CHANGE_SPONSOR_REQUEST = "CHANGE_SPONSOR_REQUEST";

    private static ChangeSponsor_SharedPref mInstance;
    private static Context mCtx;

    private ChangeSponsor_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized ChangeSponsor_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ChangeSponsor_SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveMessege(String linkrequest, String change_sponsor_request) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LINK_REQUEST, linkrequest);
        editor.putString(CHANGE_SPONSOR_REQUEST, change_sponsor_request);
        editor.apply();
        return true;
    }

    public String getLINK_REQUEST() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LINK_REQUEST, "no");
    }
    public String getCHANGE_SPONSOR_REQUEST() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CHANGE_SPONSOR_REQUEST, "no");
    }
    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }
}