package com.appmart.mmcuser.utils;

import android.content.Context;

import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

public class ShareUtils {

    public static String getShareLink(Context context) {
        String ShareMsg = "Kindly visit link below to join with MMC\n";
        String User_id = Profile_SharedPref.getInstance(context).getUserId();
        String Registration_form_link = "Enter My Sponsor id while Registration: "+User_id;

        String PlaystoreAppLink = "\nDownload App & Register Now \n https://play.google.com/store/apps/details?id=com.appmart.mmcuser&referrer="+User_id;
        String RegisterFormLink = "\nClick To Register With MMC \n https://mmclub.in/register/?sp="+User_id;
        String ShareLink = ShareMsg+Registration_form_link + " \n \n "+PlaystoreAppLink+ " \n \n "+RegisterFormLink;
        return ShareLink;
    }
}
