package com.appmart.mmcuser.payment_gateway;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.PaymentGatewayDetails_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.appmart.mmcuser.utils.ServerAddress.GENERATE_CHECKSUM_URL;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;


public class PaymentRequest extends AppCompatActivity {
    public String M_ID = null;
    public String CHANNEL_ID = "WAP";
    public String WEBSITE = "DEFAULT";
    public String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback";
    public String INDUSTRY_TYPE_ID = "Retail";
    Button btnPaymentProceed;
    ProgressDialog progress;
    String customer_id, email_id, mobile_number, total_cost, payment_status;
    String txn_amount, txn_id, payment_datee;
    boolean success = false;
    private String chechsumReceived;
    private String orderid;
    private String REG_FEES;
    private ImageView imgHowtoUseThumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);
        btnPaymentProceed = findViewById(R.id.btnPaymentProceed);
        imgHowtoUseThumbnail = findViewById(R.id.imgHowtoUseThumbnail);
//        M_ID = "gQiLgv28767319604035";//Aniruddha
        M_ID = "UUcrln05314236264643";
        M_ID = PaymentGatewayDetails_SharedPref.getInstance(PaymentRequest.this).getGATEWAY_MERCHANT_ID();
        REG_FEES = PaymentGatewayDetails_SharedPref.getInstance(PaymentRequest.this).getREGISTRATION_FEES();
        btnPaymentProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid = generateString();
                generateCheckSum();

            }
        });
        String video_humbnail_url =  "https://img.youtube.com/vi/"+ General_SharedPref.getInstance(getContext()).getSTART_BUSINESS_VIDEO_ID() +"/0.jpg";
        Picasso.get()  //Here, this is context.
                .load(video_humbnail_url)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.bg_overlay)
                .fit()
                .into(imgHowtoUseThumbnail);

        imgHowtoUseThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentRequest.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", General_SharedPref.getInstance(PaymentRequest.this).getSTART_BUSINESS_VIDEO_ID());
                startActivity(i);
            }
        });

    }

    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    private void generateCheckSum() {
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Wait for some time...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        total_cost = REG_FEES;   //this is installatio fees
//        total_cost = "1";   //this is installatio fees
        customer_id = Profile_SharedPref.getInstance(PaymentRequest.this).getUserId();
        email_id = Profile_SharedPref.getInstance(PaymentRequest.this).getEmailId();
        mobile_number = Profile_SharedPref.getInstance(PaymentRequest.this).getMobile();


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("MID", M_ID)
                        .add("ORDER_ID", orderid)
                        .add("CUST_ID", customer_id)
                        .add("CHANNEL_ID", CHANNEL_ID)
                        .add("EMAIL", email_id)
                        .add("MOBILE_NO", mobile_number)
                        .add("TXN_AMOUNT", total_cost)
                        .add("WEBSITE", WEBSITE)
                        .add("CALLBACK_URL", CALLBACK_URL)
                        .add("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID)
                        .build();

                Request request = new Request.Builder()
                        .url(GENERATE_CHECKSUM_URL)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    chechsumReceived = object.getString("CHECKSUMHASH");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progress.dismiss();
                initializePaytmPayment(chechsumReceived);
            }
        };
        task.execute();
    }

    private void initializePaytmPayment(String chechsumReceived) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", M_ID);
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("CUST_ID", customer_id);
        paramMap.put("MOBILE_NO", mobile_number);
        paramMap.put("EMAIL", email_id);
        paramMap.put("CHANNEL_ID", CHANNEL_ID);
        paramMap.put("TXN_AMOUNT", total_cost);
        paramMap.put("WEBSITE", WEBSITE);
        paramMap.put("CALLBACK_URL", CALLBACK_URL);
        paramMap.put("CHECKSUMHASH", chechsumReceived);
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                payment_status = bundle.getString("STATUS");
                txn_amount = bundle.getString("TXNAMOUNT");
                payment_datee = bundle.getString("TXNDATE");
                txn_id = bundle.getString("TXNID");


                if (payment_status.equals("TXN_SUCCESS")) {
//                    addTransactionDetails();
                    Toast.makeText(PaymentRequest.this, "Transaction Success,", Toast.LENGTH_LONG).show();
                    UpdateStatus updateStatus = new UpdateStatus(PaymentRequest.this, "7");
                    General_SharedPref.getInstance(PaymentRequest.this).saveJoiningStatus("7");

                    PaymentDoneUpdate paymentDoneUpdate=new PaymentDoneUpdate(getApplicationContext(),payment_status, txn_amount, payment_datee, txn_id);
               finish();
                    PaymentRequest.this.sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));

                }
            }

            @Override
            public void networkNotAvailable() {
                Toast.makeText(getContext(), "network Not Available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Toast.makeText(getContext(), "client Authentication Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Toast.makeText(getContext(), "some UI Error Occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Toast.makeText(getContext(), "on Error Loading WebPage", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Toast.makeText(getContext(), "Back Pressed Cancel Transaction", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Toast.makeText(getContext(), "onTransactionCancel", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
