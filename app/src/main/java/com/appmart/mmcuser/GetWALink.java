package com.appmart.mmcuser;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.appmart.mmcuser.utils.ServerAddress.GET_WA_LINK;

public class GetWALink {
    public static String WHATSAPP_LINK = "";
    private Context context;
    private String MULTISUCCESS_VIDEO_LINK;
    public GetWALink(Context context) {
        this.context = context;
        getWALink();
    }


    private void getWALink() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_WA_LINK)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        WHATSAPP_LINK = object.getString("wa_group_link");
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
