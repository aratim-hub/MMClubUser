package com.appmart.mmcuser.utils;

import android.os.Build;

public class BuildUtils {

    public static boolean isAtLeast24Api() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isAtLeast17Api() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

}
