package com.appmart.mmcuser.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.CompanyShopeeAddress;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.companyshopee.CompanyShopeeList;
import com.appmart.mmcuser.companyshopee.CompanyShopeeListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_COMPANY_SHOPEES;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class CompanyShops extends Fragment {

    private RecyclerView recyclerView;
    private CompanyShopeeListAdapter adapter;
    private List<CompanyShopeeList> data_list = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_shops_list, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.company_shopee));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        load_data_from_server();
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
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
                adapter = new CompanyShopeeListAdapter(getContext(), data_list, new CompanyShopeeListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent i = new Intent(getContext(), CompanyShopeeAddress.class);
                        i.putExtra("index",String.valueOf(position));
                        startActivity(i);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        };

        task.execute();

    }

}