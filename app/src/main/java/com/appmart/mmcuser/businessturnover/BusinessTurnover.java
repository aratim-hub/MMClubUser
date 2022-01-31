package com.appmart.mmcuser.businessturnover;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.approval.ApprovalList;
import com.appmart.mmcuser.businessturnover.pastturnoverlist.PastTurnOverList;
import com.appmart.mmcuser.sharedPreference.CompanySequence_SharedPref;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.ADD_NEW_BUSINESS_TURNOVER;
import static com.appmart.mmcuser.utils.ServerAddress.GET_CHILD_TURNOVER_COUNT;
import static com.appmart.mmcuser.utils.ServerAddress.GET_CHILD_TURNOVER_STATUS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_PARENT_TURNOVER;

public class BusinessTurnover extends AppCompatActivity {

    TextView actionBarTitle;
    EditText edtCompany1RS, edtCompany1BV, edtCompany2RS, edtCompany2BV, edtCompany3RechargeRS, edtCompany3OnlineBV, edtCompany3OnlineRS, edtCompany3RechargeBV, edtCompany4RS, edtCompany4BV;
    Button btnAddBusinessTurnover,btnPastTurnover,btnApproval;
    TextView tvMonth, tvYear, tvCompany1, tvCompany2, tvCompany3Recharge, tvCompany3Online, tvCompany4;
    TextView tvspCompany1, tvspCompany2, tvspCompany3R, tvspCompany3O, tvspCompany4;
    TextView tvspCompany1RS, tvspCompany1BV, tvspCompany2RS, tvspCompany2BV, tvspCompany3RRS, tvspCompany3OBV, tvspCompany3ORS, tvspCompany3RBV, tvspCompany4RS, tvspCompany4BV;
    LinearLayout  layoutCompany1,layoutCompany2,layoutCompany3,layoutCompany4,layoutCompany5,headerLayout;
    int iMonth, iYear;
    String messege;

    boolean success;
    int pending_count;
    int pending_count_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbusiness_turnover);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Business Turnover");
        Window window = BusinessTurnover.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(BusinessTurnover.this, R.color.colorPrimaryDark));
        }

        tvMonth = findViewById(R.id.tvMonth);
        tvYear = findViewById(R.id.tvYear);

        layoutCompany1 = (LinearLayout)findViewById(R.id.layoutCompany1);
        layoutCompany2 = (LinearLayout)findViewById(R.id.layoutCompany2);
        layoutCompany3 = (LinearLayout)findViewById(R.id.layoutCompany3);
        layoutCompany4 = (LinearLayout)findViewById(R.id.layoutCompany4);
        layoutCompany5 = (LinearLayout)findViewById(R.id.layoutCompany5);
        headerLayout = (LinearLayout)findViewById(R.id.headerLayout);

        tvCompany1 = findViewById(R.id.tvCompany1);
        tvCompany2 = findViewById(R.id.tvCompany2);
        tvCompany3Recharge = findViewById(R.id.tvCompany3Recharge);
        tvCompany3Online = findViewById(R.id.tvCompany3Online);
        tvCompany4 = findViewById(R.id.tvCompany4);
        tvspCompany1 = findViewById(R.id.tvspCompany1);
        tvspCompany2 = findViewById(R.id.tvspCompany2);
        tvspCompany3R = findViewById(R.id.tvspCompany3R);
        tvspCompany3O = findViewById(R.id.tvspCompany3O);
        tvspCompany4 = findViewById(R.id.tvspCompany4);

        tvspCompany1RS = findViewById(R.id.tvspCompany1RS);
        tvspCompany1BV = findViewById(R.id.tvspCompany1BV);
        tvspCompany2RS = findViewById(R.id.tvspCompany2RS);
        tvspCompany2BV = findViewById(R.id.tvspCompany2BV);
        tvspCompany4RS = findViewById(R.id.tvspCompany4RS);
        tvspCompany4BV = findViewById(R.id.tvspCompany4BV);

        tvspCompany3RRS = findViewById(R.id.tvspCompany3RRS);
        tvspCompany3OBV = findViewById(R.id.tvspCompany3OBV);
        tvspCompany3ORS = findViewById(R.id.tvspCompany3ORS);
        tvspCompany3RBV = findViewById(R.id.tvspCompany3RBV);

        String companySequence=  CompanySequence_SharedPref.getInstance(getApplicationContext()).getCOMPANY_SEQUENCE_ARRAY();
        //JSONParser obj = new JSONParser();
        try {
            JSONArray json = new JSONArray(companySequence);
            JSONObject obj = (JSONObject) json.get(0);
            tvCompany1.setText(obj.getString("company_name"));
            tvspCompany1.setText(obj.getString("company_name"));
            obj = (JSONObject) json.get(1);
            tvCompany2.setText(obj.getString("company_name"));
            tvspCompany2.setText(obj.getString("company_name"));
            obj = (JSONObject) json.get(2);
            tvCompany3Recharge.setText(obj.getString("company_name") + " Recharge");
            tvCompany3Online.setText(obj.getString("company_name") + " Online");
            tvspCompany3R.setText(obj.getString("company_name") + " Recharge");
            tvspCompany3O.setText(obj.getString("company_name") + " Online");
            obj = (JSONObject) json.get(3);
            tvCompany4.setText(obj.getString("company_name"));
            tvspCompany4.setText(obj.getString("company_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //JSONArray arrCompany = companySequence;


        DateFormat dateFormatMonth = new SimpleDateFormat("MMM");
        DateFormat dateFormatMonthId = new SimpleDateFormat("MM");
        DateFormat dateFormatYear = new SimpleDateFormat("yyyy");

        Date date = new Date();
        tvMonth.setText("Month: " + dateFormatMonth.format(date));
        tvYear.setText("Year: " + dateFormatYear.format(date));
        iMonth = Integer.parseInt(dateFormatMonthId.format(date));
        iYear = Integer.parseInt(dateFormatYear.format(date));

        edtCompany1RS = findViewById(R.id.edtCompany1RS);
        edtCompany1BV = findViewById(R.id.edtCompany1BV);
        edtCompany2RS = findViewById(R.id.edtCompany2RS);
        edtCompany2BV = findViewById(R.id.edtCompany2BV);
        edtCompany3RechargeRS = findViewById(R.id.edtCompany3RechargeRS);
        edtCompany3RechargeBV = findViewById(R.id.edtCompany3RechargeBV);
        edtCompany3OnlineRS = findViewById(R.id.edtCompany3OnlineRS);
        edtCompany3OnlineBV = findViewById(R.id.edtCompany3OnlineBV);
        edtCompany4RS = findViewById(R.id.edtCompany4RS);
        edtCompany4BV = findViewById(R.id.edtCompany4BV);

        btnAddBusinessTurnover = findViewById(R.id.btnAddBusinessTurnover);
        btnAddBusinessTurnover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        btnPastTurnover = findViewById(R.id.btnPastTurnover);
        btnPastTurnover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessTurnover.this, PastTurnOverList.class);
                startActivity(intent);
            }
        });

//        if (SponsorEligibility_SharedPref.getInstance(BusinessTurnover.this).getIS_SPONSOR().equals("true")) {
//            Toast.makeText(this, "IS_SPONSOR()", Toast.LENGTH_SHORT).show();
//        }
//        if (SponsorEligibility_SharedPref.getInstance(BusinessTurnover.this).getIS_GREAT_SPONSOR().equals("true")) {
//            Toast.makeText(this, "IS_GREAT_SPONSOR()", Toast.LENGTH_SHORT).show();
//        }

        btnApproval = findViewById(R.id.btnApproval);
        btnApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessTurnover.this, ApprovalList.class);
                startActivity(intent);
            }
        });
        String status = General_SharedPref.getInstance(BusinessTurnover.this).getJoingStatus();
        Log.d("status",""+status);
        if (Integer.parseInt(status) == 10 || Integer.parseInt(status) == 12) {
            btnApproval.setVisibility(View.VISIBLE);
        }else {
            btnApproval.setVisibility(View.GONE);
        }
//        if (SponsorEligibility_SharedPref.getInstance(BusinessTurnover.this).getIS_SPONSOR().equals("false")
//                && SponsorEligibility_SharedPref.getInstance(BusinessTurnover.this).getIS_GREAT_SPONSOR().equals("false")) {
//            //Toast.makeText(this, "IS_COSTOMER()", Toast.LENGTH_SHORT).show();
//            btnApproval.setVisibility(View.GONE);
//        }else {
//            btnApproval.setVisibility(View.VISIBLE);
//        }


        getMyParentTurnover();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        hideLayout();
        getMyTurnoverStatus();
        load_child_turnover_count();
    }

    private void addEventToDatabase() {

        ConstantMethods.loaderDialog(BusinessTurnover.this);

        final String u_id = Profile_SharedPref.getInstance(BusinessTurnover.this).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                String val1RS = "0", val1BV = "0", val2RS = "0", val2BV = "0", val3RRS = "0", val3RBV = "0";
                String val3ORS = "0", val3OBV = "0", val4RS = "0", val4BV = "0";
                if (edtCompany1RS.getText() != null)
                    val1RS = edtCompany1RS.getText().toString();
                if (edtCompany1BV.getText() != null)
                    val1BV = edtCompany1BV.getText().toString();

                if (edtCompany2RS.getText() != null)
                    val2RS = edtCompany2RS.getText().toString();

                if (edtCompany3RechargeRS.getText() != null)
                    val3RRS = edtCompany3RechargeRS.getText().toString();

                if (edtCompany3RechargeBV.getText() != null)
                    val3RBV = edtCompany3RechargeBV.getText().toString();

                if (edtCompany3OnlineRS.getText() != null)
                    val3ORS = edtCompany3OnlineRS.getText().toString();

                if (edtCompany3OnlineBV.getText() != null)
                    val3OBV = edtCompany3OnlineBV.getText().toString();

                if (edtCompany4RS.getText() != null)
                    val4RS = edtCompany4RS.getText().toString();

                if (edtCompany4BV.getText() != null)
                    val4BV = edtCompany4BV.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", u_id)
                        .add("level", "1")
                        .add("month", String.valueOf(iMonth))
                        .add("year", String.valueOf(iYear))
                        .add("company1RS", val1RS)
                        .add("company1BV", val1BV)
                        .add("company2RS", val2RS)
                        .add("company2BV", val2BV)
                        .add("company3RechargeRS", val3RRS)
                        .add("company3RechargeBV", val3RBV)
                        .add("company3OnlineRS", val3ORS)
                        .add("company3OnlineBV", val3OBV)
                        .add("company4RS", val4RS)
                        .add("company4BV", val4BV)
                        .build();

                Request request = new Request.Builder()
                        .url(ADD_NEW_BUSINESS_TURNOVER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    messege = object.getString("message");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(BusinessTurnover.this);
                if (success) {
                    edtCompany1RS.setEnabled(false);
                    edtCompany1BV.setEnabled(false);
                    edtCompany2RS.setEnabled(false);
                    edtCompany2BV.setEnabled(false);
                    edtCompany3RechargeRS.setEnabled(false);
                    edtCompany3RechargeBV.setEnabled(false);
                    edtCompany3OnlineRS.setEnabled(false);
                    edtCompany3OnlineBV.setEnabled(false);
                    edtCompany4RS.setEnabled(false);
                    edtCompany4BV.setEnabled(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTurnover.this);
                    builder.setMessage(messege);
                    builder.setPositiveButton("OK", null);
                    builder.setCancelable(false);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Yes.setTextColor(Color.parseColor("#b90d86"));
                    Yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { dialog.hide(); }
                    });
                    dialog.setCancelable(false);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTurnover.this);
                    builder.setMessage(messege);
                    builder.setPositiveButton("OK", null);
                    builder.setCancelable(false);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Yes.setTextColor(Color.parseColor("#b90d86"));
                    Yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { dialog.hide(); }
                    });
                    dialog.setCancelable(false);
                }
            }
        };

        task.execute();

    }


    private void checkValidation() {
        boolean status1 = true, status2 = true, status3 = true, status4 = true, status5 = true;
        int val1 = 0, val2 = 0, val3 = 0,val4 = 0, val5 = 0;
        String error_msg = "";

        if (Validation.hasText(edtCompany1RS) && Validation.hasText(edtCompany1BV) &&
                Validation.hasText(edtCompany2RS) && Validation.hasText(edtCompany2BV) &&
                Validation.hasText(edtCompany3RechargeRS) && Validation.hasText(edtCompany3OnlineBV) &&
                Validation.hasText(edtCompany3OnlineRS) && Validation.hasText(edtCompany3RechargeBV) &&
                Validation.hasText(edtCompany4RS) && Validation.hasText(edtCompany4BV)){

//            if ((Integer.valueOf(edtCompany2RS.getText().toString()) == 0) && (Integer.valueOf(edtCompany2BV.getText().toString()) == 0)){
//                val1 = Integer.valueOf(edtCompany1RS.getText().toString());
//                if (val1 > 2399){
//                    status2 = true;
//                }else{
//                    error_msg = "Either "+tvCompany2.getText().toString()+" INR value or "+tvCompany2.getText().toString()+" BV value need to be more than 0";
//                    status2 = false;
//                }
//            }else if ((Integer.valueOf(edtCompany2RS.getText().toString()) != 0) && (Integer.valueOf(edtCompany2BV.getText().toString()) != 0)){
//                error_msg = "Either "+tvCompany2.getText().toString()+" INR value or "+tvCompany2.getText().toString()+" BV value need to be 0";
//                status2 = false;
//            }else{
//                val1 = Integer.valueOf(edtCompany1RS.getText().toString());
//                if (val1 > 2399){
//                    status2 = true;
//                }else if ((Integer.valueOf(edtCompany2BV.getText().toString()) == 0)){
//                    val2 = Integer.valueOf(edtCompany2RS.getText().toString());
//                    if (val2 > 1199){
//                        status2 = true;
//                    }else{
//                        error_msg = tvCompany2.getText().toString()+" INR value need to be greater than 1199";
//                        status2 = false;
//                    }
//
//                }
//                if ((Integer.valueOf(edtCompany2RS.getText().toString()) == 0)){
//                    val2 = Integer.valueOf(edtCompany2BV.getText().toString());
//                    if (val2 > 299){
//                        status2 = true;
//                    }else{
//                        error_msg = tvCompany2.getText().toString()+" BV value need to be greater than 299";
//                        status2 = false;
//                    }
//                }
//            }
//
//            if ((Integer.valueOf(edtCompany1RS.getText().toString()) == 0) && (Integer.valueOf(edtCompany1BV.getText().toString()) == 0)){
//                val2 = Integer.valueOf(edtCompany2RS.getText().toString());
//                if (val2 > 2399){
//                    status1 = true;
//                }else{
//                    error_msg = "Either "+tvCompany1.getText().toString()+" INR value or "+tvCompany1.getText().toString()+" BV value need to be more than 0";
//                    status1 = false;
//                }
//            }else if ((Integer.valueOf(edtCompany1RS.getText().toString()) != 0) && (Integer.valueOf(edtCompany1BV.getText().toString()) != 0)){
//                error_msg = "Either "+tvCompany1.getText().toString()+" INR value or "+tvCompany1.getText().toString()+" BV value need to be 0";
//                status1 = false;
//            }else{
//                val2 = Integer.valueOf(edtCompany2RS.getText().toString());
//                if (val2 > 2399){
//                    status1 = true;
//                }else if ((Integer.valueOf(edtCompany1BV.getText().toString()) == 0)){
//                    val1 = Integer.valueOf(edtCompany1RS.getText().toString());
//                    if (val1 > 1199){
//                        status1 = true;
//                    }else{
//                        error_msg = tvCompany1.getText().toString()+" INR value need to be greater than 1199";
//                        status1 = false;
//                    }
//
//                }
//                if ((Integer.valueOf(edtCompany1RS.getText().toString()) == 0)){
//                    val1 = Integer.valueOf(edtCompany1BV.getText().toString());
//                    if (val1 > 499){
//                        status1 = true;
//                    }else{
//                        error_msg = tvCompany1.getText().toString()+" BV value need to be greater than 499";
//                        status1 = false;
//                    }
//                }
//            }
//            if (status1 && status2){
//                ConstantMethods.showAlertMessege(BusinessTurnover.this,
//                        "done", messege);
                addEventToDatabase();
//            }else{
//                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTurnover.this);
//                builder.setMessage(error_msg);
//                builder.setPositiveButton("OK", null);
//                builder.setCancelable(false);
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//                Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                Yes.setTextColor(Color.parseColor("#b90d86"));
//                Yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.hide();
//                    }
//                });
//                dialog.setCancelable(false);
//            }
        }

//        if (!edtCompany4RS.getText().toString().isEmpty() && !edtCompany4BV.getText().toString().isEmpty()){
//            error_msg = "Only any one from "+tvCompany4.getText().toString()+" INR value or "+tvCompany4.getText().toString()+" BV value is to be filled";
//            status5 = false;
//        }else
//        if (edtCompany4RS.getText().toString().isEmpty()){
//            status5 = false;
//            edtCompany4RS.setError("Required");
//        }else if (edtCompany4BV.getText().toString().isEmpty()){
//            status5 = false;
//            edtCompany4BV.setError("Required");
//        }else{
//            if (!edtCompany4RS.getText().toString().isEmpty()){
//                val5 = Integer.valueOf(edtCompany4RS.getText().toString());
//                status5 = true;
//            }
//            if (!edtCompany4BV.getText().toString().isEmpty()){
//                val5 = Integer.valueOf(edtCompany4BV.getText().toString());
//                status5 = true;
//            }
//        }

//        if (!edtCompany3OnlineRS.getText().toString().isEmpty() && !edtCompany3OnlineBV.getText().toString().isEmpty()){
//            error_msg = "Only any one from "+tvCompany3Online.getText().toString()+" INR value or "+tvCompany3Online.getText().toString()+" BV value is to be filled";
//            status4 = false;
//        }else
//        if (edtCompany3OnlineRS.getText().toString().isEmpty()){
//            status4 = false;
//            edtCompany3OnlineRS.setError("Required");
//        }else if (edtCompany3OnlineBV.getText().toString().isEmpty()){
//            status4 = false;
//            edtCompany3OnlineBV.setError("Required");
//        }else{
//            if (!edtCompany3OnlineRS.getText().toString().isEmpty()){
//                val4 = Integer.valueOf(edtCompany3OnlineRS.getText().toString());
//                status4 = true;
//            }
//            if (!edtCompany3OnlineBV.getText().toString().isEmpty()){
//                val4 = Integer.valueOf(edtCompany3OnlineBV.getText().toString());
//                status4 = true;
//            }
//        }

//        if (!edtCompany3RechargeRS.getText().toString().isEmpty() && !edtCompany3RechargeBV.getText().toString().isEmpty()){
//            error_msg = "Only any one from "+tvCompany3Recharge.getText().toString()+" INR value or "+tvCompany3Recharge.getText().toString()+" BV value is to be filled";
//            status3 = false;
//        }else
//        if (edtCompany3RechargeRS.getText().toString().isEmpty()){
//            status3 = false;
//            edtCompany3RechargeRS.setError("Required");
//        }else if (edtCompany3RechargeBV.getText().toString().isEmpty()){
//            status3 = false;
//            edtCompany3RechargeBV.setError("Required");
//        }else {
//            if (!edtCompany3RechargeRS.getText().toString().isEmpty()){
//                val3 = Integer.valueOf(edtCompany3RechargeRS.getText().toString());
//                status3 = true;
//            }
//            if (!edtCompany3RechargeBV.getText().toString().isEmpty()){
//                val3 = Integer.valueOf(edtCompany3RechargeBV.getText().toString());
//                status3 = true;
//            }
//        }

//        if (!edtCompany2RS.getText().toString().isEmpty() && !edtCompany2BV.getText().toString().isEmpty()){
//            error_msg = "Only any one from "+tvCompany2.getText().toString()+" INR value or "+tvCompany2.getText().toString()+" BV value is to be filled";
//            status2 = false;
//        }else
//        if (edtCompany2RS.getText().toString().isEmpty()){
//            status2 = false;
//            edtCompany2RS.setError("Required");
//        }else if (edtCompany2BV.getText().toString().isEmpty()){
//            status2 = false;
//            edtCompany2BV.setError("Required");
//        }
//        else
//            if (!edtCompany2RS.getText().toString().equals("0") || !edtCompany2BV.getText().toString().equals("0")){
//            error_msg = "Either "+tvCompany2.getText().toString()+" INR value or "+tvCompany2.getText().toString()+" BV value need to be 0";
//            status2 = false;
//        }else
//            {
//
//
//        }

//        if (!edtCompany1RS.getText().toString().isEmpty() && !edtCompany1BV.getText().toString().isEmpty()){
//            error_msg = "Only any one from "+tvCompany1.getText().toString()+" INR value or "+tvCompany1.getText().toString()+" BV value is to be filled";
//            status1 = false;
//        }else
//        if (edtCompany1RS.getText().toString().isEmpty()){
//            status1 = false;
//            edtCompany1RS.setError("Required");
//        }else if (edtCompany1BV.getText().toString().isEmpty()){
//            status1 = false;
//            edtCompany1BV.setError("Required");
//        }else


    }

    private void getMyParentTurnover() {

        ConstantMethods.loaderDialog(BusinessTurnover.this);

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            JSONObject object;
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
                user_id = "17177";
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_MY_PARENT_TURNOVER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(BusinessTurnover.this);
                //object.getString("");
                if (success) {
                    try {
                        JSONArray arr = object.getJSONArray("data");
                        JSONObject obj = arr.getJSONObject(0);
                        JSONArray arrTurnOver= obj.getJSONArray("turnover");
                        for (int i=0; i< arrTurnOver.length(); i++)
                        {
                            obj = arrTurnOver.getJSONObject(i);

                            String amt = obj.getString("turnover_amount");
                            String bv = obj.getString("turnover_bv");

                            if (i==0)
                            {
                                tvspCompany1RS.setText("\u20B9 " + amt);
                                tvspCompany1BV.setText("BV: " + bv);
                            }
                            else if (i==1)
                            {
                                tvspCompany2RS.setText("\u20B9 " + amt);
                                tvspCompany2BV.setText("BV: " + bv);
                            }
                            else if (i==2)
                            {
                                tvspCompany3RRS.setText("\u20B9 " + amt);
                                tvspCompany3RBV.setText(bv + " Mobiles");
                            }
                            else if (i==3)
                            {
                                tvspCompany3ORS.setText("\u20B9 " + amt);
                                tvspCompany3OBV.setText(bv + " Deals");
                            }
                            else if (i==4)
                            {
                                tvspCompany4RS.setText("\u20B9 " + amt);
                                tvspCompany4BV.setText("QTY: " + bv);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(BusinessTurnover.this, "Please try Again later to get the sponser turnover.", Toast.LENGTH_SHORT).show();
                }

            }
        };

        task.execute();

    }

    private void getMyTurnoverStatus() {

        //ConstantMethods.loaderDialog(BusinessTurnover.this);

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            JSONObject object;
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
                //user_id = "17177";
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_CHILD_TURNOVER_STATUS)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    pending_count = object.getInt("pending_count");


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //ConstantMethods.hideLoaderDialog(BusinessTurnover.this);
                //object.getString("");
                if (success) {
                    if (pending_count == 1){
                        showLayout();
                    }else {
                        hideLayout();
                    }
                    showLayout();

                } else {
                    Toast.makeText(BusinessTurnover.this, "Please try Again later to get the sponser turnover.", Toast.LENGTH_SHORT).show();
                }

            }
        };
        task.execute();
    }

    private void showLayout(){
        layoutCompany1.setVisibility(View.VISIBLE);
        layoutCompany2.setVisibility(View.VISIBLE);
        layoutCompany3.setVisibility(View.VISIBLE);
        layoutCompany4.setVisibility(View.VISIBLE);
        layoutCompany5.setVisibility(View.VISIBLE);
        headerLayout.setVisibility(View.VISIBLE);


        tvCompany1.setVisibility(View.VISIBLE);
        tvCompany2.setVisibility(View.VISIBLE);
        tvCompany3Recharge.setVisibility(View.VISIBLE);
        tvCompany3Online.setVisibility(View.VISIBLE);
        tvCompany4.setVisibility(View.VISIBLE);

        btnAddBusinessTurnover.setVisibility(View.VISIBLE);
    }
    private void hideLayout(){
        layoutCompany1.setVisibility(View.GONE);
        layoutCompany2.setVisibility(View.GONE);
        layoutCompany3.setVisibility(View.GONE);
        layoutCompany4.setVisibility(View.GONE);
        layoutCompany5.setVisibility(View.GONE);
        headerLayout.setVisibility(View.GONE);

        tvCompany1.setVisibility(View.GONE);
        tvCompany2.setVisibility(View.GONE);
        tvCompany3Recharge.setVisibility(View.GONE);
        tvCompany3Online.setVisibility(View.GONE);
        tvCompany4.setVisibility(View.GONE);

        btnAddBusinessTurnover.setVisibility(View.GONE);
    }

    private void load_child_turnover_count() {
        // ConstantMethods.loaderDialog(this);

        final String userid = Profile_SharedPref.getInstance(this).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            JSONObject object;
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", userid)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_CHILD_TURNOVER_COUNT)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    pending_count_again= object.getInt("pending_count");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                //ConstantMethods.hideLoaderDialog(Home.this);

                if (success) {
//                    if (pending_count_again == 0){
//                        btnApproval.setVisibility(View.GONE);
//                    }else {
                        btnApproval.setVisibility(View.VISIBLE);
//                    }
                }

            }
        };

        task.execute();
    }
}