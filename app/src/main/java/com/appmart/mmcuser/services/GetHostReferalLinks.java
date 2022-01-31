package com.appmart.mmcuser.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.appmart.mmcuser.sharedPreference.Host_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.GET_REFERAL_LINKS;


public class GetHostReferalLinks extends Service {

    public static HashMap<String,String> HOST_LINKS=new HashMap<>();
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
        final String Host_id = Profile_SharedPref.getInstance(getApplicationContext()).getREFERAL_HOST_ID();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("host_id", Host_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_REFERAL_LINKS)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        HOST_LINKS.put(object.getString("company"),object.getString("ref_link"));
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
                Host_Links_SharedPref.getInstance(getApplicationContext()).clearSharedPref();

                String RenatusNova_hostLink = HOST_LINKS.get("RenatusNova");
                String NexMoey_hostLink = HOST_LINKS.get("NexMoney");
                String OkLifeCare_hostLink = HOST_LINKS.get("OkLifeCare");
                String HHI_hostLink = HOST_LINKS.get("HHI");
                Host_Links_SharedPref.getInstance(getApplicationContext()).saveHostLInks(RenatusNova_hostLink,NexMoey_hostLink,OkLifeCare_hostLink, HHI_hostLink);

            }
        };

        task.execute();

    }

}