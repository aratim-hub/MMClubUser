package com.appmart.mmcuser;

import android.content.Context;
import android.os.AsyncTask;

import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_USER_STATUS;

public class UpdateStatus {
    private Context context;
    private String Status;
    private boolean success;

    public UpdateStatus(Context context,String Status) {
        this.context = context;
        this.Status = Status;

        updateToServer();
    }

    private void updateToServer() {

        final String userid = Profile_SharedPref.getInstance(context).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("status",Status)
                        .add("userid",userid)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_USER_STATUS)
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
                if (success) {
//                    Toast.makeText(context, "Status update Successfully", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(context, "Unable to update Status", Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }
}
