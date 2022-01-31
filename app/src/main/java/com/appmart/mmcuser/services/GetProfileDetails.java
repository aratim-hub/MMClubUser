package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.LOGIN_USER;


public class GetProfileDetails extends Service {
    private boolean success;
    private String id,f_name,l_name,email,mobile,whatsapp_no,p_code,city, sponsor_id,status, is_business_start, created_at,multisuccess_intro_video, multisuccess_path_video;
    private String wallet_balance;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        getProfileDetails();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void getProfileDetails() {

       final String mobile_number = Profile_SharedPref.getInstance(getApplicationContext()).getMobile();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("mobile",mobile_number)
                        .build();

                Request request = new Request.Builder()
                        .url(LOGIN_USER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    id = object.getString("id");
                    f_name = object.getString("f_name");
                    l_name = object.getString("l_name");
                    mobile = object.getString("mobile");
                    email = object.getString("email");
                    whatsapp_no = object.getString("whatsapp_no");
                    city = object.getString("city");
                    p_code = object.getString("p_code");
                    sponsor_id = object.getString("sponsor_id");
                    status = object.getString("status");
                    created_at = object.getString("created_at");
                    is_business_start = object.getString("is_business_start");
                    wallet_balance = object.getString("wallet_balance");
                    multisuccess_intro_video = object.getString("multisuccess_intro_video");
                    multisuccess_path_video = object.getString("multisuccess_path_video");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (success) {
//                    Profile_SharedPref.getInstance(getContext()).saveProfile(id, f_name,l_name, mobile, email, whatsapp_no, city, p_code,host_id,created_at,createby,Introductionvideo_url,host_lyconet_link,my_lyconet_link,lyconet_ispaid);
                    Profile_SharedPref.getInstance(getApplicationContext()).saveProfile(id, f_name, l_name, mobile, email, whatsapp_no, city, p_code, sponsor_id, created_at, multisuccess_intro_video, is_business_start, wallet_balance,multisuccess_path_video);
                    General_SharedPref.getInstance(getApplicationContext()).saveJoiningStatus(status);
                } else {
                }
            }
        };

        task.execute();

    }

}