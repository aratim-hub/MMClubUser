package com.appmart.mmcuser.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.WithdrawHistoryListAdapter;
import com.appmart.mmcuser.models.WithdrawHistoryData;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_WITHDRAW_HISTORY;

public class WithdrawHistoryList extends Fragment {
    private RecyclerView recyclerView;
    private WithdrawHistoryListAdapter adapter;
    private List<WithdrawHistoryData> data_list = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.withdraw_history_list, container, false);
        ((Home) getActivity()).setActionBarTitle("Withdraw History");


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WithdrawHistoryListAdapter(getContext(), data_list, WithdrawHistoryList.this);
        recyclerView.setAdapter(adapter);



        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        data_list.clear();

        ConstantMethods.loaderDialog(getContext());
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_WITHDRAW_HISTORY)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        WithdrawHistoryData data = new WithdrawHistoryData(object.getString("withdrawal_id"),
                                object.getString("mobile_number"),
                                object.getString("withdrawal_amount"),
                                object.getString("withdraw_on"),
                                object.getString("withdrawal_status"),
                                object.getString("user_comment"),

                                object.getString("dateTime")
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
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();

            }
        };

        task.execute();

    }



    @Override
    public void onDetach() {

        ((Home) getActivity()).setActionBarTitle("Home");
        super.onDetach();
    }



}
