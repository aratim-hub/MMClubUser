package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.WithdrawHistoryList;
import com.appmart.mmcuser.models.WithdrawHistoryData;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class WithdrawHistoryListAdapter extends RecyclerView.Adapter<WithdrawHistoryListAdapter.ViewHolder> {

    private Context context;
    private List<WithdrawHistoryData> my_data;
    private List<WithdrawHistoryData> my_data_filtered;
    private WithdrawHistoryList withdrawHistoryList;

    public WithdrawHistoryListAdapter(Context context, List<WithdrawHistoryData> OrphanageListData, WithdrawHistoryList withdrawHistoryList) {
        this.context = context;
        this.my_data = OrphanageListData;
        this.my_data_filtered = OrphanageListData;
        this.withdrawHistoryList = withdrawHistoryList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_history_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtWithdrawId.setText("Withdraw id: " + my_data_filtered.get(position).getWithdrawal_id());
        holder.txtMobile.setText("Mobile Number: " + my_data_filtered.get(position).getMobile_number());
        holder.txtWithdrawOn.setText("Withdraw on: " + my_data_filtered.get(position).getWithdraw_on());
        holder.txtWithdrawAmount.setText("Withdraw Amount: " + my_data_filtered.get(position).getWithdrawal_amount());
        holder.txtDateTime.setText("Date: " + my_data_filtered.get(position).getTime_in_millis());
        holder.txtUserComment.setText("Comment: " + my_data_filtered.get(position).getUser_comment());
//        holder.txtWithdrawStatus.setText("Status: " + my_data_filtered.get(position).getWithdrawal_status());

        if (my_data_filtered.get(position).getWithdrawal_status().equals("0")) {
            holder.txtWithdrawStatus.setText("Status: Pending");
            holder.txtWithdrawStatus.setTextColor(Color.parseColor("#FFFF00"));
        } else if (my_data_filtered.get(position).getWithdrawal_status().equals("1")) {
            holder.txtWithdrawStatus.setText("Status: Accepted");
            holder.txtWithdrawStatus.setTextColor(Color.parseColor("#008000"));

        } else if (my_data_filtered.get(position).getWithdrawal_status().equals("2")) {
            holder.txtWithdrawStatus.setText("Status: Rejected");
            holder.txtWithdrawStatus.setTextColor(Color.parseColor("#FF0000"));

        }

    }


    @Override
    public int getItemCount() {
        return my_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtWithdrawId, txtMobile, txtWithdrawAmount, txtWithdrawOn, txtDateTime, txtWithdrawStatus,txtUserComment;
        public CardView card_view;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtWithdrawId = (TextView) itemView.findViewById(R.id.txtWithdrawId);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtWithdrawAmount = (TextView) itemView.findViewById(R.id.txtWithdrawAmount);
            txtWithdrawOn = (TextView) itemView.findViewById(R.id.txtWithdrawOn);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            txtUserComment = (TextView) itemView.findViewById(R.id.txtUserComment);
            txtWithdrawStatus = (TextView) itemView.findViewById(R.id.txtWithdrawStatus);

        }
    }


}
