package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.annotation.Nullable;


import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.GET_HOST_DETAILS;


public class GetHostDetails extends Service {

private String rec_host_id,firstname,lastname,email,mobile_number;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        getHostDetails();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void getHostDetails() {

        final String host_id = Profile_SharedPref.getInstance(getApplicationContext()).getREFERAL_HOST_ID();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("host_id", host_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_HOST_DETAILS)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        rec_host_id=object.getString("id");
                        firstname = object.getString("firstname");
                        lastname = object.getString("lastname");
                        email = object.getString("email");
                        mobile_number=object.getString("mobile_no");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Host_Details_SharedPref.getInstance(getApplicationContext()).clearSharedPref();
                Host_Details_SharedPref.getInstance(getApplicationContext()).saveHostDetails(rec_host_id,firstname,lastname,email,mobile_number);
                GetHostDetails.super.onDestroy();
            }
        };

        task.execute();
    }

}