package com.appmart.mmcuser.changeSponsor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.UpdateReferenceLink;
import com.appmart.mmcuser.multiSuccess.adapters.NexMoneyListAdapter;
import com.appmart.mmcuser.multiSuccess.model.NexMoneyListData;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEX_MONEY_MULTISUCCESS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_PLACED_SPONSOR_CHANGE_REQUEST;
import static com.appmart.mmcuser.utils.ServerAddress.GET_RECEIVED_SPONSOR_CHANGE_REQUEST;

public class ReceivedRequest extends Fragment {
    RecyclerView recycler_view_recived_request;
    private ReceivedRequestAdapter adapter;
    private List<ReceivedRequestData> data_list = null;
    private Button btnUploadPaymentReceipt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_received_request, container, false);
        recycler_view_recived_request = (RecyclerView) view.findViewById(R.id.recycler_view_recived_request);

        data_list = new ArrayList<>();

        recycler_view_recived_request.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ReceivedRequestAdapter(getContext(), data_list,this);
        recycler_view_recived_request.setAdapter(adapter);





        load_data_from_server();

        return view;
    }

    public void load_data_from_server() {
        data_list.clear();
        final String userid = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
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
                        .url(GET_RECEIVED_SPONSOR_CHANGE_REQUEST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        ReceivedRequestData data = new ReceivedRequestData(object.getString("req_id"),
                                object.getString("request_from"),
                                object.getString("request_from_mobile_Number"),
                                object.getString("current_sponsor"),
                                object.getString("current_sponsor_mobile_number"),
                                object.getString("req_status"),
                                object.getString("req_date"));
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
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();

    }

    public void reLoadData() {
        data_list.clear();
        load_data_from_server();
    }
}
