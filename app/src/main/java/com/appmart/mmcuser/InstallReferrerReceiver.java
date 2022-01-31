package com.appmart.mmcuser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.appmart.mmcuser.sharedPreference.InstallReferals_SharedPref;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        Toast.makeText(context, "ref:"+referrer, Toast.LENGTH_SHORT).show();
        //Use the referrer
        InstallReferals_SharedPref.getInstance(context).saveREFERER(referrer);
    }
}