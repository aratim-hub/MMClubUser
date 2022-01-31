package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SponsorEligibility_SharedPref {
    private static SponsorEligibility_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "sponsorEligibility";
    private String IS_SPONSOR = "IS_SPONSOR";
    private String IS_GREAT_SPONSOR = "IS_GREAT_SPONSOR";

    private SponsorEligibility_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized SponsorEligibility_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SponsorEligibility_SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveData(boolean isSponsor, boolean isGreatSponsor) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_SPONSOR, String.valueOf(isSponsor));
        editor.putString(IS_GREAT_SPONSOR, String.valueOf(isGreatSponsor));
        editor.apply();
        return true;
    }
    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;
    }

    public String getIS_SPONSOR() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_SPONSOR, "false");
    }

    public String getIS_GREAT_SPONSOR() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_GREAT_SPONSOR, "false");
    }
}