package com.appmart.mmcuser.start_business;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.activities.StartBusiness;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.MMC_Referal_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.My_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.CHECK_IF_BUSINESS_STARTED_OR_NOT;
import static com.appmart.mmcuser.utils.ServerAddress.REQUEST_HOST_LINK;
import static com.appmart.mmcuser.utils.ServerAddress.VALIDATE_COUPON;
//import static com.zipow.videobox.confapp.ConfMgr.getApplicationContext;

public class start extends Fragment {
    Button btnSubmitCoupon, btnJoinNexMoney;
    EditText edtCouponSRNumber, edtCouponCode;
    String messege, mmc_link,dateTime;
    TextView txtHostName, txtHostMobileNumber, txtHostNexMoneyLink;
    Boolean success = false;
    private CardView cardHostDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_start_business, container, false);

        cardHostDetails = view.findViewById(R.id.cardHostDetails);
        edtCouponSRNumber = view.findViewById(R.id.edtCouponSRNumber);
        edtCouponCode = view.findViewById(R.id.edtCouponCode);
        btnSubmitCoupon = view.findViewById(R.id.btnSubmitCoupon);
        btnJoinNexMoney =view.findViewById(R.id.btnJoinNexMoney);
        txtHostName = view.findViewById(R.id.txtHostName);
        txtHostMobileNumber = view.findViewById(R.id.txtHostMobileNumber);
        txtHostNexMoneyLink = view.findViewById(R.id.txtHostNexMoneyLink);
        mmc_link = MMC_Referal_Links_SharedPref.getInstance(getContext()).getNEXMONEY_MMC_LINK();

        btnJoinNexMoney.setVisibility(View.INVISIBLE);

        btnSubmitCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    requestForRedemption();
                }
            }
        });

        btnJoinNexMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK().equals("") || Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK().equals(null)) {
//                    sendWarningToHost();
                    return;
                }
                String url = Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                ((StartBusiness) getActivity()).finish();


            }
        });

        txtHostName.setText(Host_Details_SharedPref.getInstance(getContext()).getHOST_NAME());
        txtHostMobileNumber.setText(Host_Details_SharedPref.getInstance(getContext()).getHOST_MOBILE_NUMBER());
        txtHostNexMoneyLink.setText(Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK());

        checkIfBusinessStarted();

        if (My_Links_SharedPref.getInstance(getContext()).getNEXMONEY_MY_LINK().equals("")) {
            btnJoinNexMoney.setEnabled(true);
        } else {
            btnJoinNexMoney.setEnabled(false);
            btnJoinNexMoney.setText("Already Joined");
        }

        return view;
    }

//    private void sendWarningToHost() {
//
//        ConstantMethods.loaderDialog(getContext());
//        final String host_id = Host_Details_SharedPref.getInstance(getContext()).getHOST_ID();
//        final String host_mobile = Host_Details_SharedPref.getInstance(getContext()).getHOST_MOBILE_NUMBER();
//        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();
//
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        Date dateobj = new Date();
//        final String CurrentDateTime = df.format(dateobj);
//        System.out.println(df.format(dateobj));
//
//
//        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
//            @Override
//            protected Void doInBackground(Integer... integers) {
//
//                OkHttpClient client = new OkHttpClient();
//
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("request_to_id", host_id)
//                        .add("request_to_mobile", host_mobile)
//                        .add("request_by", user_id)
//                        .add("company", "NexMoney")
//                        .add("dateTime", CurrentDateTime)
//                        .build();
//
//                Request request = new Request.Builder()
//                        .url(REQUEST_HOST_LINK)
//                        .post(requestBody)
//                        .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    JSONObject object = new JSONObject(response.body().string());
//                    success = object.getBoolean("success");
//                    messege = object.getString("messege");
//                    dateTime = object.getString("dateTime");
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    System.out.println("End of content");
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
////******************Add 36 Hours in Date received from server I.E link reqest date and time****************************
//                Date d1 = null;
//                try {
//                    d1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateTime);
//                } catch (ParseException e) {
//                }
//                Calendar cl = Calendar.getInstance();
//                cl.setTime(d1);
//                cl.add(Calendar.HOUR, 36);
//                Date date_after_36_hours = cl.getTime();
//
////*******************************************************************************************************
//
//                Date CurDate = null;
//                try {
//                    CurDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(CurrentDateTime);
//                } catch (ParseException e) {
//                }
//                Calendar calen1 = Calendar.getInstance();
//                Calendar calen2 = Calendar.getInstance();
//                calen1.setTime(CurDate);
//                calen2.setTime(date_after_36_hours);
//
//
//                // Compare the dates
//                if (calen1.after(calen2)) {
//                    //This condition will be TRUE when 36 hours are completed after request Date(Received from Server)
//                    Toast.makeText(getContext(), "Proceed with MMC Link", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(mmc_link));
//                    startActivity(i);
//                } else if (calen1.before(calen2)) {
//                    //This condition will TRUE if 36 Hours are not Completed after Request date(Received from Server)
//                    Toast.makeText(getContext(), "Please wait for Host Link", Toast.LENGTH_SHORT).show();
//                    ConstantMethods.showAlertMessege(getContext(), "Information:", messege);
//                }
//                ConstantMethods.hideLoaderDialog(getContext());
//
//            }
//        };
//
//        task.execute();
//
//    }

    private void checkIfBusinessStarted() {

        ConstantMethods.loaderDialog(getContext());
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(CHECK_IF_BUSINESS_STARTED_OR_NOT)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    messege = object.getString("messege");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                if (success) {
                    btnSubmitCoupon.setText(messege);
                    btnSubmitCoupon.setEnabled(false);
                    edtCouponCode.setEnabled(false);
                    edtCouponSRNumber.setEnabled(false);
                    btnJoinNexMoney.setVisibility(View.VISIBLE);
                    Profile_SharedPref.getInstance(getContext()).saveIsBusinessStarted("1");
                }
            }
        };

        task.execute();

    }

    private void requestForRedemption() {

        ConstantMethods.loaderDialog(getContext());
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();
        final String Cpupon_sr_number = edtCouponSRNumber.getText().toString();
        final String coupon_code = edtCouponCode.getText().toString();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("redeem_by", user_id)
                        .add("coupon_sr_number", Cpupon_sr_number)
                        .add("coupon_Code", coupon_code)
                        .build();

                Request request = new Request.Builder()
                        .url(VALIDATE_COUPON)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
                    messege = object.getString("messege");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                ConstantMethods.showAlertMessege(getContext(), getString(R.string.done), messege);
                ConstantMethods.showAlertMessege(getContext(), getString(R.string.done), getString(R.string.coupon_redeem_success));
                checkIfBusinessStarted();
                if (messege.equalsIgnoreCase("Coupon redemption Successfully done")) {

                    UpdateStatus updateStatus = new UpdateStatus(getContext(), "8");
                    General_SharedPref.getInstance(getContext()).saveJoiningStatus("8");

                }

            }
        };

        task.execute();

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtCouponCode)) ret = false;
        if (!Validation.hasText(edtCouponSRNumber)) ret = false;
        return ret;
    }
}
