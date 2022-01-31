package com.appmart.mmcuser.trainingvideo;

import android.content.Context;
import android.os.AsyncTask;

import com.appmart.mmcuser.GetWALink;
import com.appmart.mmcuser.sharedPreference.My_Links_SharedPref;
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
import static com.appmart.mmcuser.utils.ServerAddress.GET_USER_STATUS_TABLE;
import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_VIDEO_SEEN_STATUS;

public class UpdateVideoSeenStatus {
    String tv_id;
    String video_category;
    private Context context;

    public UpdateVideoSeenStatus(Context context, String tv_id, String video_category) {
        this.context = context;
        this.tv_id = tv_id;
        this.video_category = video_category;
        getUserStatusFromServer();
    }


    private void getUserStatusFromServer() {
        //This Methods is same as GetHostsReferal link class
        // Dont Confuse here,  Passing parameter to POST is different
        final String User_id = Profile_SharedPref.getInstance(context).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("tv_id", tv_id)
                        .add("user_id", User_id)
                        .add("video_category", video_category)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(UPDATE_VIDEO_SEEN_STATUS)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
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
            }
        };

        task.execute();

    }
}
