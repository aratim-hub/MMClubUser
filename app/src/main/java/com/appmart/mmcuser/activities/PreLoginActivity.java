package com.appmart.mmcuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

public class PreLoginActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
        getSupportActionBar().hide();
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        String CheckUser = Profile_SharedPref.getInstance(this).getUserId();
        try {
            if ((!CheckUser.equals(""))) {
//                Intent i = new Intent(Login.this, HomeMain.class);
//                Intent i = new Intent(Login.this, MainActivity.class);
                Intent i = new Intent(PreLoginActivity.this, Home.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            } else {
                Intent i = new Intent(PreLoginActivity.this, PreLoginActivity.class);
                startActivity(i);

            }
        } catch (Exception e) {

        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreLoginActivity.this, Login.class);
                startActivity(i);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreLoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
    }
}