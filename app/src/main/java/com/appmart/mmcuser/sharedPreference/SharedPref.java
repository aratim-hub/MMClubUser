package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static SharedPreferences prefs;

    public static void putString(Context ctx, String key, String score) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        prefs.edit().putString(key, score).commit();

    }

    public static String getString(Context ctx, String key) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

}
