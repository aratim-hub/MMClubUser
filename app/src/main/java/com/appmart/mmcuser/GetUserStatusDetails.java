package com.appmart.mmcuser;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.appmart.mmcuser.utils.ServerAddress.GET_USER_STATUS_TABLE;

public class GetUserStatusDetails {
    public static HashMap<Integer,String> USER_STATUS;

    private Context context;
    public GetUserStatusDetails(Context context) {
        this.context = context;
        getUserStatusFromServer();
        GetWALink getWALink = new GetWALink(context);
    }


    private void getUserStatusFromServer() {
        USER_STATUS=new HashMap<>();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_USER_STATUS_TABLE)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        USER_STATUS.put(object.getInt("status_id"), object.getString("user_status"));
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
                for (int j=0;j<USER_STATUS.size();j++) {
//                    Toast.makeText(context, "User status rec :"+USER_STATUS, Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }
}
