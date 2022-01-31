package com.appmart.mmcuser.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.BuildConfig;
import com.appmart.mmcuser.ContextWrapper;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.fragments.frag_enter_otp;
import com.appmart.mmcuser.sharedPreference.Language_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import java.util.Locale;

public class Login extends AppCompatActivity {

    public final static String MOBILE_NUMBER = null;
    BroadcastReceiver receiver;
    private Button btnGetOTP;
    private EditText edtMobileNumber;
    private Fragment menu_Frag;
    private Dialog choose_language;
    TextView txtVersion, txtRegisterHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        btnGetOTP = findViewById(R.id.btnGetOTP);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        txtVersion = findViewById(R.id.txtVersion);
        txtRegisterHere = findViewById(R.id.txtRegisterHere);
        txtVersion.setText("version: " + BuildConfig.VERSION_NAME);


        String selectedLanguage = Language_SharedPref.getInstance(this).getSELECTED_LANGUAGE();
        try {
            if ((selectedLanguage == null)) {
                selectLanguageDialog();

            }
        } catch (Exception e) {

        }
        txtRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtMobileNumber.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Mobile number should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkValidation()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile_number", edtMobileNumber.getText().toString());
                    menu_Frag = new frag_enter_otp();
                    menu_Frag.setArguments(bundle);
                    loadFragment();
                }


            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected == false) {
                    ConstantMethods.showAlertMessege(Login.this, "Warning", "No internet");
                }

            }
        };
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void selectLanguageDialog() {
        choose_language = new Dialog(Login.this);
        choose_language.requestWindowFeature(Window.FEATURE_NO_TITLE);
        choose_language.setContentView(R.layout.choose_language);
        final RadioButton rdbtnEnglish = choose_language.findViewById(R.id.rdbtnEnglish);
        final RadioButton rdbtnHindi = choose_language.findViewById(R.id.rdbtnHindi);
        Button btnSubmitLanguage = choose_language.findViewById(R.id.btnSubmitLanguage);

        btnSubmitLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_language = "";
                if (rdbtnEnglish.isChecked()) {
                    selected_language = "en";
                } else if (rdbtnHindi.isChecked()) {
                    selected_language = "hi";
                }
                Language_SharedPref.getInstance(Login.this).savelanguage(selected_language);
                choose_language.dismiss();

                startActivity(new Intent(Login.this, Login.class));
            }
        });
        choose_language.setCancelable(false);
        choose_language.show();
    }

    public void loadFragment() {
        if (menu_Frag != null) {
            FragmentManager FM = getSupportFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.loginContainer, menu_Frag)
                    .addToBackStack(String.valueOf(FM))
                    .commit();
        }
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isPhoneNumber(edtMobileNumber, true)) ret = false;
        return ret;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = null;
        try {
            String lan = ConstantMethods.getSelectedLanguage();
            // .. create or get your new Locale object here.
            if (lan == null) {
                lan = "en";
            }
            newLocale = new Locale(lan);
            Locale.setDefault(newLocale);

        } catch (Exception e) {
        }

        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }
}
