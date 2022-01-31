package com.appmart.mmcuser.events;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.EventActivity;
import com.appmart.mmcuser.activities.Home;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_EVENT_LIST;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class EventList extends Fragment {

    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private List<EventListData> data_list = null;
    private Fragment menu_Frag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_coupon, container, false);
        ((EventActivity) getActivity()).setActionBarTitle("Gurukul");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new EventListAdapter(getContext(), data_list, EventList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());

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
                        .url(GET_EVENT_LIST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Log.i("TAG", "doInBackground: "+object.toString());


                        EventListData data = new EventListData(
                                object.getString("event_id"),
                                object.getString("event_img_url"),
                                object.getString("event_description"),
                                object.getString("event_date"),
                                object.getString("event_time"),
                                object.getString("event_link"));
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
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    @Override
    public void onDetach() {
        super.onDetach();
    }


}