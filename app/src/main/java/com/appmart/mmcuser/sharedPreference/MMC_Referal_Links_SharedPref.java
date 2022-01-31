package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MMC_Referal_Links_SharedPref {
    private static MMC_Referal_Links_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "MMCReferalLinksLinks";
    private String NEXMONEY_MMC_LINK = "strNexMoney";
    private String OKLIFECARE_MMC_LINK = "strOkLifeCare";

    private MMC_Referal_Links_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized MMC_Referal_Links_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MMC_Referal_Links_SharedPref(context);
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
    public boolean saveMMCLinks(String NexMoney, String okLifeCare_myLink) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NEXMONEY_MMC_LINK, NexMoney);
        editor.putString(OKLIFECARE_MMC_LINK, okLifeCare_myLink);
        editor.apply();
        return true;
    }



    public String getNEXMONEY_MMC_LINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NEXMONEY_MMC_LINK, "");
    }
    public String getOKLIFECARE_MMC_LINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(OKLIFECARE_MMC_LINK, "");
    }

}