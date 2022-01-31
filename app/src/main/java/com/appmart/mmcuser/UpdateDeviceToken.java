package com.appmart.mmcuser;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.appmart.mmcuser.fcm.SharedPrefManager;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_DEVICE_TOKEN;

/**
 * Created by Aniruddha on 10/11/2017.
 */

public class UpdateDeviceToken extends Service {


    private boolean success;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            updateDeviceToken();

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateDeviceToken() {
        final String CheckUser = Profile_SharedPref.getInstance(this).getUserId();
        final String token = SharedPrefManager.getInstance(this).getDeviceToken();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", CheckUser)
                        .add("token", token)
                        .build();


                Request request = new Request.Builder()
                        .url(UPDATE_DEVICE_TOKEN)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
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

            }
        };

        task.execute();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
