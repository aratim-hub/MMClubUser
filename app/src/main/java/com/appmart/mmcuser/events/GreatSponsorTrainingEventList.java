package com.appmart.mmcuser.events;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.SponsorEligibility_SharedPref;
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

import static com.appmart.mmcuser.utils.ServerAddress.CANCEL_ONLINE_EVENT_BY_EVENT_ID;
import static com.appmart.mmcuser.utils.ServerAddress.GET_ONLINE_EVENT_LIST;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class GreatSponsorTrainingEventList extends Fragment {

    private RecyclerView recyclerView;
    private TextView txtWarningText;
    private GreatSponsorTrainingEventListAdapter adapter;
    private List<GreatSponsorTrainingEventListData> data_list = null;
    private Fragment menu_Frag;
    Button btnAddNewEvent;
    boolean success;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_online_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        txtWarningText =  view.findViewById(R.id.txtWarningText);
        btnAddNewEvent = view.findViewById(R.id.btnAddNewEvent);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GreatSponsorTrainingEventListAdapter(getContext(), data_list, GreatSponsorTrainingEventList.this);
        recyclerView.setAdapter(adapter);
        if (SponsorEligibility_SharedPref.getInstance(getContext()).getIS_SPONSOR().equals("true")) {
            load_data_from_server();
            txtWarningText.setVisibility(View.GONE);

        } else {

        }
        btnAddNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SponsorEligibility_SharedPref.getInstance(getContext()).getIS_GREAT_SPONSOR().equals("true")) {
                    Intent i = new Intent(getContext(), AddNewOnlineEvents.class);
                    i.putExtra("eventFor", "Great Sponsor Training Revision");
                    startActivity(i);

                } else {
ConstantMethods.showAlertMessege(getContext(),"Messege","You are not Eligible to Add new Event");
                }
            }
        });
        return view;
    }

    private void load_data_from_server() {

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
                        .add("eventFor", "Great Sponsor Training Revision")
                        .build();

                Request request = new Request.Builder()
                        .url(GET_ONLINE_EVENT_LIST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        GreatSponsorTrainingEventListData data = new GreatSponsorTrainingEventListData(
                                object.getString("online_event_id"),
                                object.getString("controller_id"),
                                object.getString("controller"),
                                object.getString("monitor"),
                                object.getString("host"),
                                object.getString("event_title"),
                                object.getString("event_date"),
                                object.getString("event_time"),
                                object.getString("event_zoom_link"),
                                object.getString("event_status"),
                                object.getString("event_for"));
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void cancelEventDialog(final String online_event_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set a title for alert dialog
        builder.setTitle("Confirm Cancel?");
        // Show a message on alert dialog
        builder.setMessage("Are you sure you want to Cancel Event?");
        // Set the positive button
        builder.setPositiveButton("Yes", null);
        // Set the negative button
        builder.setNegativeButton("No", null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEventRequest(online_event_id);
                dialog.dismiss();

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void cancelEventRequest(final String online_event_id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("online_event_id", online_event_id)
                        .build();

                Request request = new Request.Builder()
                        .url(CANCEL_ONLINE_EVENT_BY_EVENT_ID)
                        .post(requestBody)
                        .build();
                try {

                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
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
                if (success) {
                    ConstantMethods.showAlertMessege(getContext(),"Done","Event Cancelled Successfully");
                    Toast.makeText(getContext(), "Event Cancelled Successfully", Toast.LENGTH_SHORT).show();
                    data_list.clear();
                    load_data_from_server();
                } else {
                    ConstantMethods.showAlertMessege(getContext(),"Failed to cancel","Event Cancellation failed, Please Try Again Later");

                }

            }
        };

        task.execute();
    }

}