package com.appmart.mmcuser.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.companyshopee.CompanyShopeeAddressListAdapter;
import com.appmart.mmcuser.companyshopee.CompanyShopeeList;
import com.appmart.mmcuser.companyshopee.CompanyShopeeListAdapter;
import com.appmart.mmcuser.events.AddNewOnlineEvents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_COMPANY_SHOPEES;

public class CompanyShopeeAddress extends AppCompatActivity {

    RecyclerView recycler_view_atomy;
    private CompanyShopeeAddressListAdapter adapter;
    private List<CompanyShopeeList> data_list = null;
    private List<String> address_list = null;

    int currentIndex = 0;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_shopee_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Company Address Shopee");
        Window window = CompanyShopeeAddress.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(CompanyShopeeAddress.this, R.color.colorPrimaryDark));
        }

        currentIndex = Integer.valueOf(getIntent().getExtras().getString("index"));

        recycler_view_atomy = findViewById(R.id.recycler_view_atomy);
        data_list = new ArrayList<>();
        address_list = new ArrayList<>();

        recycler_view_atomy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        load_data_from_server();
    }




    private void load_data_from_server() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_COMPANY_SHOPEES)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        CompanyShopeeList data = new CompanyShopeeList();
                        data.setCompany_shoppee_id(object.getString("company_shoppee_id"));
                        data.setShop_name(object.getString("shop_name"));

                        JSONArray addressArr = new JSONArray(object.getString("address").toString());
                        List<String> adr = new ArrayList<>();
                        for (int k=0; k<addressArr.length(); k++){
                            adr.add(addressArr.get(k).toString());
                        }
                        data.setAddress(adr);
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
                if (data_list!= null){
                    List<String> address_list = data_list.get(currentIndex).getAddress();
                    actionBarTitle.setText(data_list.get(currentIndex).getShop_name());
                    adapter = new CompanyShopeeAddressListAdapter(CompanyShopeeAddress.this, address_list);
                    recycler_view_atomy.setAdapter(adapter);
                }
            }
        };

        task.execute();

    }
}