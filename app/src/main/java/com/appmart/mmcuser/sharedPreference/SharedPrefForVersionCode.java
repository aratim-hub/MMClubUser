package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.appmart.mmcuser.activities.Home;

public class SharedPrefForVersionCode {

    private static final String SHARED_PREF_NAME = "VersionCodeSharedPref";
    private static final String VERSION_CODE = "version_code";
    private static final String SHARE_MESSAGE = "share_message";

    private static SharedPrefForVersionCode mInstance;
    private static Context mCtx;

    private SharedPrefForVersionCode(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefForVersionCode getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefForVersionCode(context);
        }
        return mInstance;
    }


    public boolean saveVersionCode(String version_code) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VERSION_CODE, version_code);
        editor.apply();
        return true;
    }

    public boolean saveShareMessage(String share_message) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARE_MESSAGE, share_message);
        editor.apply();
        return true;
    }

    public static String getShareMessage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARE_MESSAGE, "");
    }

    public static String getVersionCode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(VERSION_CODE, "");
    }
    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }

}
