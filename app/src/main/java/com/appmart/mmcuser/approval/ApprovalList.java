package com.appmart.mmcuser.approval;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.adapters.TeamApprovalListAdapter;
import com.appmart.mmcuser.businessturnover.BusinessTurnover;
import com.appmart.mmcuser.businessturnover.TurnOverModel;
import com.appmart.mmcuser.businessturnover.TurnoverListData;
import com.appmart.mmcuser.businessturnover.pastturnoverlist.PastTurnOverList;
import com.appmart.mmcuser.businessturnover.pastturnoverlist.PastTurnOverListAdapter;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_APPROVAL_LIST;
import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_PAST_TURNOVER;
import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_APPROVE_AND_REJECT_STATUS;

public class ApprovalList extends AppCompatActivity {

    TextView actionBarTitle;
    private RecyclerView recyclerView;
    private ApproveListAdapter adapter;
    List<TurnOverModel> turnOverModels;
    private List<TurnoverListData> data_list = null;
    boolean success;
    String message;
    TextView tvsponser;
    String approval_status;
    String id, name;
    int is_turnoved_approved;
    LinkedHashMap<String, List<TurnoverListData>> hashMap = new LinkedHashMap<String, List<TurnoverListData>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_turn_over_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Approval List");
        Window window = ApprovalList.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ApprovalList.this, R.color.colorPrimaryDark));
        }

        tvsponser = (TextView)findViewById(R.id.tvsponser);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        load_data_from_server();
    }

    private void load_data_for_approve_and_reject(final String month, final String year, final String approval_status,final String uid) {
        ConstantMethods.loaderDialog(this);

        final String userid = Profile_SharedPref.getInstance(this).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            JSONObject object;
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("approval_user_id", userid)
                        .add("user_id", uid)
                        .add("month", month)
                        .add("year", year)
                        .add("approval_status",approval_status)
                        .build();

                Log.d("approval_user_id",userid);
                Log.d("userid",uid);
                Log.d("approval_status",approval_status);
                Log.d("month",month);
                Log.d("year",year);

                Request request = new Request.Builder()
                        .url(UPDATE_APPROVE_AND_REJECT_STATUS)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    object = new JSONObject(response.body().string());
                    Log.i("TAG", "doInBackground: "+object);
                    success = object.getBoolean("success");
                    message = object.getString("message");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(ApprovalList.this);
                if (success) {
//                    load_data_from_server();
                    Toast.makeText(ApprovalList.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ApprovalList.class));
                }

                if (data_list.size() <= 0) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ApprovalList.this);
                        builder.setMessage("Please try again later..!")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();

                    } catch (Exception e) {

                    }
                }
            }
        };

        task.execute();
    }


    private void load_data_from_server() {
        ConstantMethods.loaderDialog(this);

        final String userid = Profile_SharedPref.getInstance(this).getUserId();
        Log.i("TAG", "load_data_from_server: "+userid);
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            JSONObject object;
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", userid)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_MY_APPROVAL_LIST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    object = new JSONObject(response.body().string());

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
                ConstantMethods.hideLoaderDialog(ApprovalList.this);

                if (success) {
                    try {
                        JSONArray arr = object.getJSONArray("data");
                        turnOverModels = new ArrayList<>();
//                        data_list.clear();
                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject obj = arr.getJSONObject(j);

                            TurnOverModel  dataModel = new TurnOverModel();
                            dataModel.id = obj.getString("id");
                            dataModel.firstname = obj.getString("firstname");
                            dataModel.lastname = obj.getString("lastname");
                            dataModel.user_name = obj.getString("user_name");
                            dataModel.mobile_no = obj.getString("mobile_no");
                            dataModel.turnover_for = obj.getString("turnover_for");
                            dataModel.is_turnoved_approved = obj.getString("is_turnoved_approved");

                            JSONArray arrTurnOver = obj.getJSONArray("turnover");

                            List<TurnoverListData> turnoverListData = new ArrayList<>();

                            for (int i = 0; i < arrTurnOver.length(); i++) {
                                JSONObject sub_obj = arrTurnOver.getJSONObject(i);

                                TurnoverListData  data = new TurnoverListData();
                                data.id = id;
                                data.turnover_id = sub_obj.getString("turnover_id");
                                data.turnover_month = sub_obj.getString("turnover_month");
                                data.turnover_year = sub_obj.getString("turnover_year");
                                data.turnover_amount = sub_obj.getString("turnover_amount");
                                data.turnover_bv = sub_obj.getString("turnover_bv");
                                data.turnover_date = sub_obj.getString("turnover_date");
                                data.turnover_company = sub_obj.getString("turnover_company");
                                data.company_name = sub_obj.getString("company_name");
                                data.turnover_level = sub_obj.getString("turnover_level");
                                data.turnover_approval_user = sub_obj.getString("turnover_approval_user");
                                data.turnover_approval_status = sub_obj.getString("turnover_approval_status");

                                data.turnover_approval_level = sub_obj.getString("turnover_approval_level");
                                data.turnover_approval_date = sub_obj.getString("turnover_approval_date");
                                data.turnover_admin_approval = sub_obj.getString("turnover_admin_approval");
                                data.turnover_admin_approval_date = sub_obj.getString("turnover_admin_approval_date");
                                data.turnover_for = sub_obj.getString("turnover_for");
                                data.created_at = sub_obj.getString("created_at");
                                data.name = id+" "+name+", "+data.turnover_for;
                                data.is_turnoved_approved = is_turnoved_approved;


                                turnoverListData.add(data);
                            }
                            dataModel.turnOver = turnoverListData;
                            turnOverModels.add(dataModel);
                        }

                        getData(turnOverModels);

                    } catch (Exception e) {
                        Log.i("TAG", "onPostExecute: "+e.getLocalizedMessage());
                    }

//                    if (data_list.size() <= 0) {
//                        try {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ApprovalList.this);
//                            builder.setMessage("Please try again later..!")
//                                    .setNegativeButton("Ok", null)
//                                    .create()
//                                    .show();
//
//                        } catch (Exception e) {
//
//                        }
//                    }
                    tvsponser.setText(name+" "+id);

                }else{
                    ConstantMethods.showAlertMessege(ApprovalList.this,
                            "You don't have any approval list", "");
                }

            }
        };

        task.execute();
    }

    private void getData(List<TurnOverModel> turnOverModels){

        if (turnOverModels != null){
            adapter = new ApproveListAdapter(this, turnOverModels);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ApproveListAdapter.OnItemClickListener() {
                @Override
                public void onApproveItemClick(TurnOverModel model) {
                    load_data_for_approve_and_reject(model.turnOver.get(0).turnover_month,model.turnOver.get(0).turnover_year,"1",model.id);
                }

                @Override
                public void onRejectItemClick(TurnOverModel model) {
                    load_data_for_approve_and_reject(model.turnOver.get(0).turnover_month,model.turnOver.get(0).turnover_year,"2",model.id);
                }
            });
        }else{
            ConstantMethods.showAlertMessege(ApprovalList.this,
                    "You don't have any approval list", "");
        }
    }
}
