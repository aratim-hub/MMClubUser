package com.appmart.mmcuser;

import android.content.Context;
import android.os.AsyncTask;

import com.appmart.mmcuser.sharedPreference.General_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.appmart.mmcuser.utils.ServerAddress.GET_VIDEO_IMAGES;

public class GetVideoImages {
    private Context context;
    private HashMap<String,String> VIDEO_IMAGES_HASHMAP=new HashMap<>();
    public GetVideoImages(Context context) {
        this.context = context;
        getVideoImages();
    }


    private void getVideoImages() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_VIDEO_IMAGES)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        VIDEO_IMAGES_HASHMAP.put(object.getString("category"), object.getString("image_url"));
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
                String multisuccess = VIDEO_IMAGES_HASHMAP.get("Multisuccess");
                String training= VIDEO_IMAGES_HASHMAP.get("Training");
                String introduction= VIDEO_IMAGES_HASHMAP.get("Introduction");
                General_SharedPref.getInstance(context).saveImageBelowVideo(multisuccess,training,introduction);
            }
        };

        task.execute();

    }
}
