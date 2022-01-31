package com.appmart.mmcuser.businessturnover;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.EventActivity;
import com.appmart.mmcuser.events.EventListAdapter;
import com.appmart.mmcuser.events.EventListData;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_EVENT_LIST;
import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_OVERALL_TURNOVER;


public class TurnoverList extends AppCompatActivity {

    TextView actionBarTitle;
    private RecyclerView recyclerView;
    private TurnoverListAdapter adapter;
    private List<TurnoverListData> data_list = null;
    private Fragment menu_Frag;
    boolean success;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbusiness_turnover);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Past Turnovers");
        Window window = TurnoverList.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(TurnoverList.this, R.color.colorPrimaryDark));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TurnoverListAdapter(this, data_list, TurnoverList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();


    }

    private void load_data_from_server() {
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
                        .add("user_id", userid)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_MY_OVERALL_TURNOVER)
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
                ConstantMethods.hideLoaderDialog(TurnoverList.this);

                if (success) {
                    try {
                        JSONArray arr = object.getJSONArray("data");
                        JSONObject obj = arr.getJSONObject(0);
                        JSONArray arrTurnOver = obj.getJSONArray("turnover");
                        for (int i = 0; i < arrTurnOver.length(); i++) {
                            obj = arrTurnOver.getJSONObject(i);

                            TurnoverListData  data = new TurnoverListData();
                            data.turnover_id = obj.getString("turnover_id");
                            data.turnover_month = obj.getString("turnover_month");
                            data.turnover_year = obj.getString("turnover_year");
                            data.turnover_amount = obj.getString("turnover_amount");
                            data.turnover_bv = obj.getString("turnover_bv");
                            data.turnover_date = obj.getString("turnover_date");
                            data.turnover_company = obj.getString("turnover_company");
                            data.company_name = obj.getString("company_name");
                            data.turnover_level = obj.getString("turnover_level");
                            data.turnover_approval_user = obj.getString("turnover_approval_user");
                            data.turnover_approval_status = obj.getString("turnover_approval_status");

                            data.turnover_approval_level = obj.getString("turnover_approval_level");
                            data.turnover_approval_date = obj.getString("turnover_approval_date");
                            data.turnover_admin_approval = obj.getString("turnover_admin_approval");
                            data.turnover_admin_approval_date = obj.getString("turnover_admin_approval_date");
                            data.turnover_for = obj.getString("turnover_for");
                            data.created_at = obj.getString("created_at");

                            data_list.add(data);

                        }
                    } catch (Exception e) {

                    }

                    adapter.notifyDataSetChanged();
                }

                if (data_list.size() <= 0) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TurnoverList.this);
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




}