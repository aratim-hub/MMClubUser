package com.appmart.mmcuser.changeSponsor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.services.GetLinkRequestDetails;
import com.appmart.mmcuser.sharedPreference.ChangeSponsor_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.REQUEST_ACTION_URL;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class ReceivedRequestAdapter extends RecyclerView.Adapter<ReceivedRequestAdapter.ViewHolder> {

    private Context context;
    private List<ReceivedRequestData> my_data;
    private ReceivedRequest receivedRequest;

    public ReceivedRequestAdapter(Context context, List<ReceivedRequestData> dataReceived, ReceivedRequest receivedRequest) {
        this.context = context;
        this.my_data = dataReceived;
        this.receivedRequest = receivedRequest;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_request_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtRequestFrom.setText(my_data.get(position).getRequest_from());
        holder.txtRequestFromMobileNumber.setText("Contact Number: " + my_data.get(position).getRequest_from_mobile_number());
        holder.txtCurrentSponsor.setText(my_data.get(position).getCurrent_sponsor());
        holder.txtCurrentSponsorMobileNumber.setText(my_data.get(position).getCurrent_sponsor_mobile_number());
        holder.txtReqDate.setText("Request Date: " + my_data.get(position).getReq_date());
        if (my_data.get(position).getReq_status().equals("1")) {
            holder.txtReqStatus.setText("Pending, Not Accepted");
        } else {
            holder.txtReqStatus.setText("Accepted by Requested Sponsor");
        }

        holder.txtBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                1: Pending
//                2: Accepted
//                3: Rejected
                acceptRejectRequest(my_data.get(position).getReq_id(), "2");
            }
        });

        holder.txtBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRejectRequest(my_data.get(position).getReq_id(), "3");

            }
        });
    }

    private void acceptRejectRequest(final String req_id, final String action) {


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("req_id", req_id)
                        .add("action", action)
                        .build();

                Request request = new Request.Builder()
                        .url(REQUEST_ACTION_URL)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


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
                receivedRequest.reLoadData();
                context.startService(new Intent(context, GetLinkRequestDetails.class));
                ConstantMethods.showAlertMessege(context, "Done", "Update Successfully");
                Toast.makeText(context, "Update Successfully", Toast.LENGTH_LONG).show();
                ChangeSponsor_SharedPref.getInstance(context).clearSharedPref();
            }
        };

        task.execute();

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtRequestFrom, txtRequestFromMobileNumber, txtCurrentSponsor, txtReqDate, txtReqStatus, txtCurrentSponsorMobileNumber;
        TextView txtBtnAccept, txtBtnReject;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtRequestFromMobileNumber = itemView.findViewById(R.id.txtRequestFromMobileNumber);
            txtRequestFrom = itemView.findViewById(R.id.txtRequestFrom);
            txtCurrentSponsor = itemView.findViewById(R.id.txtCurrentSponsor);
            txtCurrentSponsorMobileNumber = itemView.findViewById(R.id.txtCurrentSponsorMobileNumber);
            txtReqDate = itemView.findViewById(R.id.txtReqDate);
            txtReqStatus = itemView.findViewById(R.id.txtReqStatus);
            txtBtnAccept = itemView.findViewById(R.id.txtBtnAccept);
            txtBtnReject = itemView.findViewById(R.id.txtBtnReject);
        }
    }

}
