package com.appmart.mmcuser.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.TeamApprovalList;
import com.appmart.mmcuser.models.TeamApprovalListData;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TeamApprovalListAdapter extends RecyclerView.Adapter<TeamApprovalListAdapter.ViewHolder> {
    public int progressStatus3 = 0;
    TeamApprovalList teamApprovalList;
    private Context context;
    private List<TeamApprovalListData> my_data;
    private Handler handler = new Handler();

    public TeamApprovalListAdapter(Context context, List<TeamApprovalListData> dataReceived, TeamApprovalList teamApprovalList) {
        this.context = context;
        this.my_data = dataReceived;
        this.teamApprovalList = teamApprovalList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_approval_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtCompanyName.setText(my_data.get(position).getCompany());
        holder.txtRef_link.setText(my_data.get(position).getRef_link());
        holder.txtIsTrainingComplete.setText(my_data.get(position).getIs_training_completed());

        //Set text to HOST Approval Status
        if (my_data.get(position).getIsApproved().equals("1")) {
            holder.txtApproveStatus.setText("Approved");
            holder.txtApproveStatus.setTextColor(Color.parseColor("#47d400"));
        } else {
            holder.txtApproveStatus.setText("Approval Pending");
            holder.txtApproveStatus.setTextColor(Color.parseColor("#FF0000"));
        }

        //Set text to MMC Admin Approval Status
        if (my_data.get(position).getIsApprovedByAdmin().equals("1")) {
            holder.txtMMCApproveStatus.setText("Approved");
            holder.txtMMCApproveStatus.setTextColor(Color.parseColor("#47d400"));
        } else {
            holder.txtMMCApproveStatus.setText("Approval Pending");
            holder.txtMMCApproveStatus.setTextColor(Color.parseColor("#FF0000"));
        }

        //Set text to Training is completed or not
        if (my_data.get(position).getIs_training_completed().equals("1")) {
            holder.txtIsTrainingComplete.setText("Training Completed");
            holder.txtIsTrainingComplete.setTextColor(Color.parseColor("#47d400"));
        } else {
            holder.txtIsTrainingComplete.setText("Training not completed");
            holder.txtIsTrainingComplete.setTextColor(Color.parseColor("#FF0000"));
        }
        //***************************************************************************************
        holder.txtBtnTapTViewProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("link_id", my_data.get(position).getLink_id());
                bundle.putString("payment_proof", my_data.get(position).getPayment_proof());
                teamApprovalList.showPaymentProof(bundle);
            }
        });

        holder.txtBtnTrainingComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
loadExitDialog(my_data.get(position).getLink_id(), my_data.get(position).getCompany());
            }
        });

        //***************************************************************************************
        //Set Action Buttons Visibility
        if (my_data.get(position).getIsApproved().equals("1")) {
            holder.txtBtnTapTViewProof.setVisibility(View.INVISIBLE);
            holder.txtBtnTrainingComplete.setVisibility(View.VISIBLE);
        } else {
            holder.txtBtnTapTViewProof.setVisibility(View.VISIBLE);
            holder.txtBtnTrainingComplete.setVisibility(View.INVISIBLE);
        }

        if (my_data.get(position).getIs_training_completed().equals("1")) {
            holder.txtBtnTapTViewProof.setVisibility(View.INVISIBLE);
            holder.txtBtnTrainingComplete.setVisibility(View.INVISIBLE);
        }
        //***************************************************************************************
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCompanyName, txtRef_link, txtApproveStatus, txtBtnTapTViewProof, txtIsTrainingComplete, txtMMCApproveStatus, txtBtnTrainingComplete;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtRef_link = itemView.findViewById(R.id.txtRef_link);
            txtApproveStatus = itemView.findViewById(R.id.txtApproveStatus);
            txtMMCApproveStatus = itemView.findViewById(R.id.txtMMCApproveStatus);
            txtBtnTapTViewProof = itemView.findViewById(R.id.txtBtnTapTViewProof);
            txtIsTrainingComplete = itemView.findViewById(R.id.txtIsTrainingComplete);
            txtBtnTrainingComplete = itemView.findViewById(R.id.txtBtnTrainingComplete);
        }
    }

    private void loadExitDialog(final String link_id, final String company) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        Button btnExit = dialog.findViewById(R.id.btnExit);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        txtTitle.setText("Confirmation");
        txtMessage.setText("Are you sure this Success Partner's Training is Completed ?");
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamApprovalList.updateAsTrainingCompled(link_id, company);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
