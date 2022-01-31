package com.appmart.mmcuser.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.NewsListAdapter;
import com.appmart.mmcuser.models.NewsListData;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEWS;


public class News extends Fragment {

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<NewsListData> data_list = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.news));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsListAdapter(getContext(), data_list, News.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_ALL_NEWS)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        NewsListData data = new NewsListData(object.getString("news_id"),
                                object.getString("news_title"),
                                object.getString("news_body"),
                                object.getString("news_url"),
                                object.getString("created_at"),
                                object.getString("status"),
                                object.getString("publish_by"));
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
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();
                if (data_list.size() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Sorry, unable to load data..!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                }
            }
        };

        task.execute();

    }

    @Override
    public void onDetach() {
        ((Home) getActivity()).setActionBarTitle(getString(R.string.app_name));
        super.onDetach();
    }


}