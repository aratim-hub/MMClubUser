package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class My_Links_SharedPref {
    private static My_Links_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "MyReferalLinksLinks";
    private String RENATUSNOVA_MYLINK = "RENATUSNOVA_MYLINK";
    private String NEXMONEY_MY_LINK = "strNexMoney";
    private String OKLIFECARE_MY_LINK = "strOkLifeCare";
    private String HHI_MYLINK = "HHI_MYLINK";
    private String GIG_MYLINK = "GIG_MYLINK";

    private My_Links_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized My_Links_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new My_Links_SharedPref(context);
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
    public boolean saveMyLInks(String RenatusNova_myLink, String NexMoney, String okLifeCare_myLink, String HHI_myLink, String GIG_myLink) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RENATUSNOVA_MYLINK, RenatusNova_myLink);
        editor.putString(NEXMONEY_MY_LINK, NexMoney);
        editor.putString(OKLIFECARE_MY_LINK, okLifeCare_myLink);
        editor.putString(HHI_MYLINK, HHI_myLink);
        editor.putString(GIG_MYLINK, GIG_myLink);
        editor.apply();
        return true;
    }

    public String getGIG_MYLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(GIG_MYLINK, "");
    }



    public String getRENATUSNOVA_MYLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(RENATUSNOVA_MYLINK, "");
    }
    public String getNEXMONEY_MY_LINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NEXMONEY_MY_LINK, "");
    }
    public String getOKLIFECARE_MY_LINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(OKLIFECARE_MY_LINK, "");
    }
 public String getHHI_MYLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HHI_MYLINK, "");
    }

}