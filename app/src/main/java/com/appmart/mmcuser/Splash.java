package com.appmart.mmcuser;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.appmart.mmcuser.activities.ChangeSponsor;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.activities.Login;
import com.appmart.mmcuser.activities.PreLoginActivity;
import com.appmart.mmcuser.fragments.SupportTicketList;
import com.appmart.mmcuser.sharedPreference.Language_SharedPref;

public class Splash extends AppCompatActivity {
    private int PERMISSION_REQUEST_CODE = 123;
    String selectedLanguage;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        selectedLanguage = Language_SharedPref.getInstance(Splash.this).getSELECTED_LANGUAGE();
        getSupportActionBar().hide();
        Intent i = new Intent(Splash.this, PreLoginActivity.class);
        startActivity(i);
        finish();

    }

}
