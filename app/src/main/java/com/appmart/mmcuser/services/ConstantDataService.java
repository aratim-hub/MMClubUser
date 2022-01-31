package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;


import com.appmart.mmcuser.sharedPreference.SharedPrefForVersionCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;

import static com.appmart.mmcuser.utils.ServerAddress.GET_VERSION_CODE;


public class ConstantDataService extends Service {

     String VERSION_CODE = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        getConstantFields();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    private void getConstantFields() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_VERSION_CODE)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        VERSION_CODE = object.getString("version_code");

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

                SharedPrefForVersionCode.getInstance(getApplicationContext()).saveVersionCode(VERSION_CODE);
                ConstantDataService.super.onDestroy();
            }
        };

        task.execute();
    }

}
