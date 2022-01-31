package com.appmart.mmcuser.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_SPONSOR_NAME;
import static com.appmart.mmcuser.utils.ServerAddress.LOGIN_USER;
import static com.appmart.mmcuser.utils.ServerAddress.REGISTER_USER;

public class RegisterActivity extends AppCompatActivity {
    InstallReferrerClient referrerClient;
    String referrerResponse;
    TextView actionbarUserName, actionBarTitle;
    EditText edtSponsorId, edtSponsorName, edtFirstName, edtLastName, edtEmail, edtMobile, edtWhatsAppNumber, edtDOB, edtCity, edtPincode;
    Button btnCheckSponsor, btnRegister;
    ImageView imgSelectDate;
    DatePicker datePicker1;
    CheckBox checkBoxTerms;

    private boolean success;
    private String msg;
    String SponsorName;
    String SimpleDate;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionbarUserName = viewCustomActionBar.findViewById(R.id.actionbarUserName);
        actionBarTitle.setText("Register Here");
//        actionbarUserName.setText("Welcome " + Profile_SharedPref.getInstance(RegisterActivity.this).getFName());


        referrerClient = InstallReferrerClient.newBuilder(this).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        getReferrerData();
                        referrerClient.endConnection();
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        Toast.makeText(RegisterActivity.this, "Feature not supported", Toast.LENGTH_SHORT).show();
                        referrerClient.endConnection();

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        Toast.makeText(RegisterActivity.this, "Service Unavailable", Toast.LENGTH_SHORT).show();
                        referrerClient.endConnection();

                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                referrerClient.startConnection(this);
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


        edtSponsorId = findViewById(R.id.edtSponsorId);
        edtSponsorName = findViewById(R.id.edtSponsorName);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtWhatsAppNumber = findViewById(R.id.edtWhatsAppNumber);
        edtDOB = findViewById(R.id.edtDOB);
        imgSelectDate = findViewById(R.id.ImgSelectDate);
        datePicker1 = findViewById(R.id.datePicker1);
        edtCity = findViewById(R.id.edtCity);
        edtPincode = findViewById(R.id.edtPincode);
        btnRegister = findViewById(R.id.btnRegister);
        btnCheckSponsor = findViewById(R.id.btnCheckSponsor);
        checkBoxTerms = findViewById(R.id.checkBoxTerms);
        String terms = "<a href=\"https://www.mmclub.in/MMC_terms_conditions.html\">Terms & Conditions</a>";
//        String privacy = "<a href=\"https://www.vmtutorials.in/privacy_policy.html\">privacy policy</a>";
//        String refund = "<a href=\"https://www.vmtutorials.in/refund_cancellation.html\">refund policy</a>";
        String value = "<html>I accepts " + terms + "</html>";
        checkBoxTerms.setText(Html.fromHtml(value));
        checkBoxTerms.setMovementMethod(LinkMovementMethod.getInstance());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = datePicker1.getDayOfMonth();
                int m = datePicker1.getMonth()+1;
                int y = datePicker1.getYear();
                SimpleDate = d + "-" + m + "-" + y;
                Toast.makeText(RegisterActivity.this, d+" "+m+" "+y, Toast.LENGTH_SHORT).show();
                if (checkValidation()) {
                    if (checkBoxTerms.isChecked()) {
                        sendDataToServer();
                    } else {
                        ConstantMethods.showAlertMessege(RegisterActivity.this, "Messege", "You should accept Terms and Conditions");
                    }
                } else {
                    ConstantMethods.showAlertMessege(RegisterActivity.this, "Something is missing or Wrong", "Check Details you have entered");
                }
            }
        });
        btnCheckSponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSponsorName(edtSponsorId.getText().toString());

            }
        });
    }

    private void sendDataToServer() {

        ConstantMethods.loaderDialog(RegisterActivity.this);

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("sponsor_id", edtSponsorId.getText().toString())
                        .add("fname", edtFirstName.getText().toString())
                        .add("lname", edtLastName.getText().toString())
                        .add("email", edtEmail.getText().toString())
                        .add("mobile", edtMobile.getText().toString())
                        .add("whatsappnumber", edtWhatsAppNumber.getText().toString())
                        .add("dob", SimpleDate)
                        .add("city", edtCity.getText().toString())
                        .add("pincode", edtPincode.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url(REGISTER_USER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    msg = object.getString("message");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(RegisterActivity.this);
                if (success) {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully...!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtSponsorName)) ret = false;
        if (!Validation.hasText(edtFirstName)) ret = false;
        if (!Validation.hasText(edtLastName)) ret = false;
        if (!Validation.isPhoneNumber(edtMobile, true)) ret = false;
        if (!Validation.isPhoneNumber(edtWhatsAppNumber, true)) ret = false;
        if (!Validation.isEmailAddress(edtEmail, true)) ret = false;
        if (!Validation.isPinCode(edtPincode, true)) ret = false;
        if (!Validation.hasText(edtCity)) ret = false;

        return ret;
    }

    private void getReferrerData() {
        ReferrerDetails response = null;
        try {
            response = referrerClient.getInstallReferrer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        referrerResponse = response.getInstallReferrer();
        if (referrerResponse.contains("utm")) {
            ConstantMethods.showAlertMessege(RegisterActivity.this, "Sponsor id not found", "Kindly Contact to your Sponsor who send you App Link to get Sponsor Id");
            edtSponsorId.setEnabled(true);
            btnCheckSponsor.setVisibility(View.VISIBLE);
            return;
        }
        edtSponsorId.setText(referrerResponse);
        getSponsorName(referrerResponse);
        Toast.makeText(RegisterActivity.this, "" + referrerResponse, Toast.LENGTH_SHORT).show();
        //  referrerClickTime = response.getReferrerClickTimestampSeconds();
        //   appInstallTime = response.getInstallBeginTimestampSeconds();
        //  instantExperienceLaunched = response.getGooglePlayInstantParam();
    }

    private void getSponsorName(final String SponsorId) {

        ConstantMethods.loaderDialog(RegisterActivity.this);

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("SponsorId", SponsorId)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_SPONSOR_NAME)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    SponsorName = object.getString("SponsorName");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(RegisterActivity.this);
                if (success) {
                    Toast.makeText(RegisterActivity.this, "" + SponsorName, Toast.LENGTH_SHORT).show();
                    edtSponsorName.setText(SponsorName);
                    btnRegister.setEnabled(true);
                } else {
                    ConstantMethods.showAlertMessege(RegisterActivity.this, "Invalid Sponsor id", "Sponsor not found, Please Contact to your sponsor who send you App Link");
                    btnRegister.setEnabled(false);

                }
            }
        };

        task.execute();

    }
}