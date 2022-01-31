package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Host_Links_SharedPref {
    private static Host_Links_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "HostLinks";
    private String NEXMONEY_HOSTLINK = "strNexMoney";
    private String OKLIFE_CARE_HOSTLINK = "strOklifeCare";
    private String RENATUSNOVA_HOSTLINK = "RENATUSNOVA_HOSTLINK";
    private String HHI_HOSTLINK = "HHI_HOSTLINK";

    private Host_Links_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized Host_Links_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Host_Links_SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveHostLInks(String renatusNova_hostLink, String NexMoney, String okLifeCare_hostLink, String HHI_hostLink) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NEXMONEY_HOSTLINK, NexMoney);
        editor.putString(OKLIFE_CARE_HOSTLINK, okLifeCare_hostLink);
        editor.putString(RENATUSNOVA_HOSTLINK, renatusNova_hostLink);
        editor.putString(HHI_HOSTLINK, HHI_hostLink);
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


    public String getNEXMONEY_HOSTLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NEXMONEY_HOSTLINK, "");
    }
  public String getOKLIFE_CARE_HOSTLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(OKLIFE_CARE_HOSTLINK, "");
    }

    public String getRENATUSNOVA_HOSTLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(RENATUSNOVA_HOSTLINK, "");
    }

    public String getHHI_HOSTLINK() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HHI_HOSTLINK, "");
    }
}