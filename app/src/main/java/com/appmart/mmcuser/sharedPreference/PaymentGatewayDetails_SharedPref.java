package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PaymentGatewayDetails_SharedPref {
    private static PaymentGatewayDetails_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "gatewayDetails";
    private String GATEWAY_MERCHANT_ID = "strmerchantID";
    private String REGISTRATION_FEES = "strRegFees";

    private PaymentGatewayDetails_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized PaymentGatewayDetails_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PaymentGatewayDetails_SharedPref(context);
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
    public boolean saveGatewayDetails( String merchant_id, String fees) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GATEWAY_MERCHANT_ID, merchant_id);
        editor.putString(REGISTRATION_FEES, fees);
        editor.apply();
        return true;
    }

    public String getGATEWAY_MERCHANT_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(GATEWAY_MERCHANT_ID, "0");
    }

    public String getREGISTRATION_FEES() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REGISTRATION_FEES, "0");
    }




}