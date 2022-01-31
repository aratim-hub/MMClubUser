package com.appmart.mmcuser.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_LINK_REEQUST_DETAILS;

public class FragLinkRequests extends Fragment {
    String request_to, request_to_mobile, request_by_mobile, request_by, req_date_time, step_count, company;
    private TextView txtRequestTo, txtRequestBy,txtRequestByMobile, txtRequestToMobile, txtRequestComapny, txtRequestDateTime, txtStepCount,txtInfoMessege;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_link_requests, container, false);
        ((Home) getActivity()).setActionBarTitle("Link Request");
        setHasOptionsMenu(true);
        txtRequestTo = view.findViewById(R.id.txtRequestTo);
        txtRequestBy = view.findViewById(R.id.txtRequestBy);
        txtRequestByMobile = view.findViewById(R.id.txtRequestByMobile);
        txtRequestToMobile = view.findViewById(R.id.txtRequestToMobile);
        txtRequestComapny = view.findViewById(R.id.txtRequestComapny);
        txtRequestDateTime = view.findViewById(R.id.txtRequestDateTime);
        txtStepCount = view.findViewById(R.id.txtStepCount);
        txtInfoMessege = view.findViewById(R.id.txtInfoMessege);

        getRequests();
        return view;
    }

    private void getRequests() {
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
                        .url(GET_LINK_REEQUST_DETAILS)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        request_to = object.getString("request_to");
                        request_to_mobile = object.getString("request_to_mobile");
                        request_by = object.getString("request_by");
                        request_by_mobile = object.getString("request_by_mobile");
                        req_date_time = object.getString("req_date_time");
                        company = object.getString("company");
                        step_count = object.getString("step_count");
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
                try {
                    txtRequestBy.setText("Request from: " + request_by);
                    txtRequestByMobile.setText("Contact No. : " + request_by_mobile);
                    txtRequestTo.setText("Request To: " + request_to);
                    txtRequestToMobile.setText("Contact No. : " + request_to_mobile);
                    txtRequestComapny.setText("Company Name: " + company);
                    txtRequestDateTime.setText("Request Date: " + req_date_time);
                    txtStepCount.setText("Downline step count: " + step_count);

                    Date d1 = null;
                    try {
                        d1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(req_date_time);
                    } catch (ParseException e) {
                    }
                    Calendar cl = Calendar.getInstance();
                    cl.setTime(d1);
                    cl.add(Calendar.HOUR, 48);
                    Date date_after_36_hours = cl.getTime();

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String strDate = dateFormat.format(date_after_36_hours);


                    txtInfoMessege.setText("If "+request_to+" is not updated their "+company+" referal link or Sponsor Id, then your link will automatically send to "+request_by+" after "+strDate+".");
                } catch (Exception e) {

                }
            }
        };

        task.execute();
    }

}
