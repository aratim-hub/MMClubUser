package com.appmart.mmcuser;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appmart.mmcuser.multiSuccess.fragment.HHI;
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

public class GetMyReferalLinks {
    private Context context;
    public static HashMap<String,String> MY_REFERAL_LINKS=new HashMap<>();

    public GetMyReferalLinks(Context context) {
        this.context = context;
        requestToServer();
    }


    private void requestToServer() {
        //This Methods is same as GetHostsReferal link class
        // Dont Confuse here,  Passing parameter to POST is different
        MY_REFERAL_LINKS.clear();
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
                        .add("host_id", User_id)
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
                        MY_REFERAL_LINKS.put(object.getString("company"), object.getString("ref_link"));
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
                My_Links_SharedPref.getInstance(context).clearSharedPref();
                String RenatusNova_myLink = MY_REFERAL_LINKS.get("RenatusNova");
                String NexMoey_myLink = MY_REFERAL_LINKS.get("NexMoney");
                String OkLifeCare_myLink = MY_REFERAL_LINKS.get("OkLifeCare");
                String HHI_myLink = MY_REFERAL_LINKS.get("HHI");

                String GIG_myLink = MY_REFERAL_LINKS.get("GIG");
                My_Links_SharedPref.getInstance(context).saveMyLInks(RenatusNova_myLink,NexMoey_myLink,OkLifeCare_myLink, HHI_myLink, GIG_myLink);
            }
        };

        task.execute();

    }
}
