package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class CompanySequence_SharedPref {
    private static CompanySequence_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "companySequence";

    private String COMPANYSEQUENCEARRAY="strCOMPANYSEQUENCEARRAY";

    private CompanySequence_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized CompanySequence_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CompanySequence_SharedPref(context);
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

    public boolean saveHostDetails(String companySequenceArray) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMPANYSEQUENCEARRAY, companySequenceArray);

        editor.apply();
        return true;
    }

    public String getCOMPANY_SEQUENCE_ARRAY() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COMPANYSEQUENCEARRAY, "");
    }

}