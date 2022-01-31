package com.appmart.mmcuser.payment_gateway;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_TRANSACTION_DETAILS;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class PaymentDoneUpdate {
    private Context context;
    private String payment_status, txn_amount, payment_datee, txn_id;
    private boolean success;
    public PaymentDoneUpdate(Context context, String payment_status, String txn_amount, String payment_datee, String txn_id) {
        this.context = context;
        this.payment_status = payment_status;
        this.txn_amount = txn_amount;
        this.payment_datee = payment_datee;
        this.txn_id = txn_id;

        updateToServer();
    }


    private void updateToServer() {


        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .add("payment_status", payment_status)
                        .add("txn_amount", txn_amount)
                        .add("payment_datee", payment_datee)
                        .add("txn_id", txn_id)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_TRANSACTION_DETAILS)
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
                    Toast.makeText(context, "Transaction details Update to server ", Toast.LENGTH_SHORT).show();

                }
            }
        };

        task.execute();

    }
}
