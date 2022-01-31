package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class InstallReferals_SharedPref {
    private String SHARED_PREF_NAME = "INSTALL_REFERER";
    private String REF_ID = "LINK_REQUEST";

    private static InstallReferals_SharedPref mInstance;
    private static Context mCtx;

    private InstallReferals_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized InstallReferals_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InstallReferals_SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveREFERER(String linkrequest) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REF_ID, linkrequest);
        editor.apply();
        return true;
    }

    public String getLINK_REQUEST() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REF_ID, "");
    }

    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }
}