package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.ChangeSponsor_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.GET_LINK_REQUEST_FOR_ME;


public class GetLinkRequestDetails extends Service {

private String link_requests,change_sponsor_request;
    private String reject_reason,isApprovedByAdmin, isApproved,is_training_completed;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        getdatafromServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void getdatafromServer() {

        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();



                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_LINK_REQUEST_FOR_ME)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        link_requests = object.getString("link_requests");
                        change_sponsor_request = object.getString("change_sponsor_request");
                        reject_reason = object.getString("reject_reason");
                        isApprovedByAdmin = object.getString("isApprovedByAdmin");
                        is_training_completed = object.getString("is_training_completed");
                        isApproved = object.getString("isApproved");
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
                General_SharedPref.getInstance(getApplicationContext()).savePendingReason(isApprovedByAdmin, reject_reason,is_training_completed,isApproved);
                ChangeSponsor_SharedPref.getInstance(getApplicationContext()).saveMessege(link_requests,change_sponsor_request);
                GetLinkRequestDetails.super.onDestroy();
            }
        };

        task.execute();
    }

}