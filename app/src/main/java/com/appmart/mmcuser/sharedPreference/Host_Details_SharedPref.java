package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Host_Details_SharedPref {
    private static Host_Details_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "HostDetails";

    private String HOST_ID="strHost_id";
    private String HOST_NAME="strHostName";
    private String HOST_EMAIL="strHostEmail";
    private String HOST_MOBILE_NUMBER="strHostMobileNumber";

    private Host_Details_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized Host_Details_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Host_Details_SharedPref(context);
        }
        return mInstance;
    }

    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;
    }

    public boolean saveHostDetails(String rec_host_id, String firstname, String lastname, String email, String mobile_number) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HOST_ID, rec_host_id);
        editor.putString(HOST_NAME, firstname+" "+lastname);
        editor.putString(HOST_EMAIL, email);
        editor.putString(HOST_MOBILE_NUMBER, mobile_number);
        editor.apply();
        return true;
    }



    public String getHOST_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HOST_ID, "");
    }

    public String getHOST_NAME() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HOST_NAME, "");
    }

    public String getHOST_EMAIL() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HOST_EMAIL, "");
    }

    public String getHOST_MOBILE_NUMBER() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HOST_MOBILE_NUMBER, "");
    }

}