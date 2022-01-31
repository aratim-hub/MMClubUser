package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.multiSuccess.fragment.OkLifeCare.NEW_SPONSOR_ID;
import static com.appmart.mmcuser.utils.ServerAddress.CHANGE_SPONSOR_DIRECTLY;
import static com.appmart.mmcuser.utils.ServerAddress.GET_HOST_DETAILS;


public class changeSponsorDirectly extends Service {

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

        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
        final String newSponsor_id = NEW_SPONSOR_ID;

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .add("newSponsor_id", newSponsor_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(CHANGE_SPONSOR_DIRECTLY)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startService(new Intent(getApplicationContext(), GetProfileDetails.class));

            }
        };

        task.execute();
    }

}