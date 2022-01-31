package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appmart.mmcuser.sharedPreference.MMC_Referal_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.GET_MMC_REFERAL_LINKS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_USERID_NAMES;


public class GetUserIdAndNames extends Service {

    public static HashMap<String,String> USERID_NAMES=new HashMap<>();
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
                        .url(GET_USERID_NAMES)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        USERID_NAMES.put(object.getString("id"),object.getString("firstname")+" "+object.getString("lastname"));

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
                MMC_Referal_Links_SharedPref.getInstance(getApplicationContext()).clearSharedPref();
                String id = USERID_NAMES.get("1132");
                String name = USERID_NAMES.get("1145");

                GetUserIdAndNames.super.onDestroy();
            }
        };

        task.execute();
    }


}