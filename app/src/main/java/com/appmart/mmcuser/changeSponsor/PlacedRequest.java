package com.appmart.mmcuser.changeSponsor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.multiSuccess.model.NexMoneyListData;
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

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEX_MONEY_MULTISUCCESS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_PLACED_SPONSOR_CHANGE_REQUEST;
import static com.appmart.mmcuser.utils.ServerAddress.PLACE_CHANGE_SPONSOR_REQUEST;

public class PlacedRequest extends Fragment {
    RecyclerView recycler_view_Placed_request;
    private PlacedRequestAdapter adapter;
    private List<PlacedRequestData> data_list = null;
    private Button btnChangeMySponsor;
    private boolean success;
    private String messege;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_placed_request, container, false);
        recycler_view_Placed_request = (RecyclerView) view.findViewById(R.id.recycler_view_Placed_request);

        data_list = new ArrayList<>();

        recycler_view_Placed_request.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlacedRequestAdapter(getContext(), data_list);
        recycler_view_Placed_request.setAdapter(adapter);

        btnChangeMySponsor = view.findViewById(R.id.btnChangeMySponsor);
        btnChangeMySponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // Set a title for alert dialog
                builder.setTitle("Cofirm?");
                // Show a message on alert dialog
                String sourceString = "Are you sure You want to change your sponsor?";

                builder.setMessage(Html.fromHtml(sourceString));
                // Set the positive button
                builder.setPositiveButton("Yes, I am Sure", null);
                builder.setNegativeButton("No", null);

                // Set the negative button
                // Create the alert dialog
                final AlertDialog dialog = builder.create();
                // Finally, display the alert dialog
                dialog.show();
                // Get the alert dialog buttons reference
                Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                // Change the alert dialog buttons text and background color
                Yes.setTextColor(Color.parseColor("#b90d86"));
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkSponsorAndPlaceRequest();
                        dialog.dismiss();
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


                dialog.setCancelable(false);

            }
        });
        getPlacedRequestFromServer();
        return view;
    }

    private void getPlacedRequestFromServer() {
        ConstantMethods.loaderDialog(getContext());
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
                        .url(GET_PLACED_SPONSOR_CHANGE_REQUEST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        PlacedRequestData data = new PlacedRequestData(object.getString("req_id"),
                                object.getString("sent_to"),
                                object.getString("sent_to_mobile_Number"),
                                object.getString("current_sponsor"),
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
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();

    }

    private void checkSponsorAndPlaceRequest() {
        ConstantMethods.loaderDialog(getContext());

        final String userid = Profile_SharedPref.getInstance(getContext()).getUserId();
        final String host_id = Profile_SharedPref.getInstance(getContext()).getREFERAL_HOST_ID();

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
                        .add("host_id", host_id)
                        .build();

                Request request = new Request.Builder()
                        .url(PLACE_CHANGE_SPONSOR_REQUEST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject array = new JSONObject(response.body().string());
                    success = array.getBoolean("success");
                    messege = array.getString("messege");

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
                getPlacedRequestFromServer();
                if (success) {
                    ConstantMethods.showAlertMessege(getContext(),"Reply from MMC",messege);
                }
            }
        };

        task.execute();
    }

}
