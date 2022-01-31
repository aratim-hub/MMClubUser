package com.appmart.mmcuser.changeSponsor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.appmart.mmcuser.multiSuccess.model.NexMoneyListData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class PlacedRequestAdapter extends RecyclerView.Adapter<PlacedRequestAdapter.ViewHolder> {

    private Context context;
    private List<PlacedRequestData> my_data;

    public PlacedRequestAdapter(Context context, List<PlacedRequestData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.placed_request_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtSentTo.setText(my_data.get(position).getSent_to());
        holder.txtSentToMobileNumber.setText("Contact Number: "+ my_data.get(position).getSent_to_mobile_number());
        holder.txtCurrentSponsor.setText(my_data.get(position).getCurrent_sponsor());
        holder.txtReqDate.setText("Request Date: "+ my_data.get(position).getReq_date());

        if (my_data.get(position).getReq_status().equals("1")) {
            holder.txtReqStatus.setText("Pending, Not Accepted");
        } else if(my_data.get(position).getReq_status().equals("2")) {
            holder.txtReqStatus.setText("Accepted by Requested Sponsor");
        } else if (my_data.get(position).getReq_status().equals("3")) {
            holder.txtReqStatus.setText("Accepted by Requested Sponsor");

        } else if (my_data.get(position).getReq_status().equals("4")) {
            holder.txtReqStatus.setText("Request forward to Upline Sponsor");

        }

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSentToMobileNumber, txtSentTo, txtCurrentSponsor, txtReqDate, txtReqStatus;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtSentToMobileNumber = itemView.findViewById(R.id.txtSentToMobileNumber);
            txtSentTo = itemView.findViewById(R.id.txtSentTo);
            txtCurrentSponsor = itemView.findViewById(R.id.txtCurrentSponsor);
            txtReqDate = itemView.findViewById(R.id.txtReqDate);
            txtReqStatus = itemView.findViewById(R.id.txtReqStatus);
        }
    }

}
