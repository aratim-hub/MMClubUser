package com.appmart.mmcuser.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.models.GoMultiincomeMemberListData;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sponsor_training.adapter.SponsorTrainingContentListAdapter;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingContentListData;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingVideoListData;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_GO_MULTIINCOME_JOINING;
import static com.appmart.mmcuser.utils.ServerAddress.LOGIN_USER;
import static com.appmart.mmcuser.utils.ServerAddress.PLACE_CHANGE_SPONSOR_REQUEST;
import static com.appmart.mmcuser.utils.ServerAddress.PLACE_GO_MULTI_INCOME_REQUEST;

public class GoMultiIncome extends AppCompatActivity {
    TextView actionBarTitle;
    Button txtCompleteCount, txtTobeJoined;

    private List<GoMultiincomeMemberListData> data_list = null;
    Button btnPlaceMultiIncomeRequest;
    RecyclerView recycler_view;
    private GoMultiIncomeAdapter adapter;
    private boolean success;
    private String messege, status, OkLifeCare, NexMoney;
    TextView txtIsNexMoneyJoined, txtIsOklifeCareJoined;
//    int wallBalanceAdjust = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_multiincome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtCompleteCount = findViewById(R.id.txtCompleteCount);
        txtTobeJoined = findViewById(R.id.txtTobeJoined);
        txtIsNexMoneyJoined = findViewById(R.id.txtIsNexMoneyJoined);
        txtIsOklifeCareJoined = findViewById(R.id.txtIsOklifeCareJoined);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Performance Gift");
        Window window = GoMultiIncome.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(GoMultiIncome.this, R.color.colorPrimaryDark));
        }

        recycler_view = findViewById(R.id.recycler_view);
        btnPlaceMultiIncomeRequest = findViewById(R.id.btnPlaceMultiIncomeRequest);
        data_list = new ArrayList<>();

        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GoMultiIncomeAdapter(this, data_list);
        recycler_view.setAdapter(adapter);
        getData();

        btnPlaceMultiIncomeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NexMoney.contains("In Process") ||NexMoney.contains("Rejected due to") && OkLifeCare.contains("In Process")) {
                    if (data_list.size() >= 5) {
                        try {
                            placeRequest("NexMoney");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ConstantMethods.showAlertMessege(GoMultiIncome.this, "Warning", "You can't Place Request right now because of you have not completed Joining Criteria ");
                    }
                } else if (NexMoney.contains("Joining Completed") && OkLifeCare.contains("In Process")) {
                    if (data_list.size() >= 5) {
                        try {
                            placeRequest("OkLifeCare");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ConstantMethods.showAlertMessege(GoMultiIncome.this, "Warning", "You can't Place Request right now because of you have not completed Joining Criteria ");
                    }
                }
            }
        });

    }

    private void placeRequest(final String requestCompany) throws JSONException,IndexOutOfBoundsException {

        final AlertDialog progress = new SpotsDialog.Builder()
                .setContext(GoMultiIncome.this)
                .setTheme(R.style.Custom)
                .setMessage("Please Wait While Processing Your Request")
                .setCancelable(false)
                .build();
        progress.show();
        int cc = 0;
        if (requestCompany.equals("NexMoney")) {
            cc = 5;
        } else if (requestCompany.equals("OkLifeCare")) {
            cc = 5;
        }

        JSONObject jsObject = new JSONObject();
        for (int i = 0; i < cc; i++) {
            try {
                jsObject.put("du_id" + i, data_list.get(i).getDownline_id());
            } catch (Exception e) {
            }
        }

        final String downlineidForClearCounter = jsObject.toString();//mark as is paid
        Toast.makeText(GoMultiIncome.this, "" + downlineidForClearCounter, Toast.LENGTH_SHORT).show();


        final String userId = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
        final String sponsorID = Profile_SharedPref.getInstance(getApplicationContext()).getREFERAL_HOST_ID();
        Toast.makeText(this, "" + downlineidForClearCounter, Toast.LENGTH_SHORT).show();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", userId)
                        .add("sponsor_id", sponsorID)
                        .add("requested_company", requestCompany)
                        .add("downlineidForClearCounter", downlineidForClearCounter)
                        .build();

                Request request = new Request.Builder()
                        .url(PLACE_GO_MULTI_INCOME_REQUEST)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    messege = object.getString("messege");

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
                ConstantMethods.showAlertMessege(GoMultiIncome.this, "Messege", messege);

            }
        };

        task.execute();

    }

    private void getData() {
        final android.app.AlertDialog progress = new SpotsDialog.Builder()
                .setContext(GoMultiIncome.this)
                .setTheme(R.style.Custom)
                .setMessage("Please wait while Checking . . .")
                .setCancelable(false)
                .build();
        progress.show();

        final String userId = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", userId)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_GO_MULTIINCOME_JOINING)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    JSONObject objectq = array.getJSONObject(array.length() - 1);
                    NexMoney = objectq.getString("NexMoney");
                    OkLifeCare = objectq.getString("OkLifeCare");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        GoMultiincomeMemberListData data = new GoMultiincomeMemberListData(
                                object.getString("downline_id"),
                                object.getString("downline_name")
                        );
                        data_list.add(data);

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
                progress.dismiss();
                adapter.notifyDataSetChanged();
                txtIsNexMoneyJoined.setText("NexMoney: " + NexMoney);
                txtIsOklifeCareJoined.setText("Ok Life Care: " + OkLifeCare);
                txtCompleteCount.setText("Joining Completed: " + (data_list.size()));
                txtTobeJoined.setText("To be Join: " + (5 - (data_list.size())));

                if (NexMoney.contains("Joining Completed") || NexMoney.contains("Request Pending")) {
                    btnPlaceMultiIncomeRequest.setText("Request Performance Gift");
                    txtCompleteCount.setText("Joining Completed: " + (data_list.size()));
                    txtTobeJoined.setText("To be Join: " + (5 - (data_list.size())));

                }
            }
        };

        task.execute();

    }


}
