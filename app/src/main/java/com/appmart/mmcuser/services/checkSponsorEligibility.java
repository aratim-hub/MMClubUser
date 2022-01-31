package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.SponsorEligibility_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.GET_SPONSOR_LEVEL;


public class checkSponsorEligibility extends Service {

private String isSponsorCount, isGreatSponsorCount;
    private boolean isSponsor, isGreatSponsor=false;

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

        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_SPONSOR_LEVEL)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        isSponsorCount=object.getString("isSponsor");
                        isGreatSponsorCount = object.getString("isGreatSponsor");

                        if (isSponsorCount.equals("false")) isSponsorCount = "0";
                        if (isGreatSponsorCount.equals("false")) isGreatSponsorCount = "0";

                        if (Integer.parseInt(isSponsorCount)>0){
                            isSponsor=true;
                        }
                        if (Integer.parseInt(isGreatSponsorCount)>0){
                            isGreatSponsor = true;
                        }
                        SponsorEligibility_SharedPref.getInstance(getApplicationContext()).clearSharedPref();
                        SponsorEligibility_SharedPref.getInstance(getApplicationContext()).saveData(isSponsor,isGreatSponsor);
                        checkSponsorEligibility.super.onDestroy();
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
                try {
                    if (Integer.parseInt(isSponsorCount)>0){
                        isSponsor=true;
                    }
                    if (Integer.parseInt(isGreatSponsorCount)>0){
                        isGreatSponsor = true;
                    }
                } catch (Exception e) {

                }

                SponsorEligibility_SharedPref.getInstance(getApplicationContext()).clearSharedPref();
                SponsorEligibility_SharedPref.getInstance(getApplicationContext()).saveData(isSponsor,isGreatSponsor);
                checkSponsorEligibility.super.onDestroy();
            }
        };

        task.execute();
    }

}