package com.appmart.mmcuser.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.UpdateReferenceLinkAgain;
import com.appmart.mmcuser.fragments.MyPaymentProofList;
import com.appmart.mmcuser.models.MyPaymentProofListData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyPaymentProofListAdapter extends RecyclerView.Adapter<MyPaymentProofListAdapter.ViewHolder> {

    private Context context;
    private List<MyPaymentProofListData> my_data;
    private MyPaymentProofList myPaymentProofList;

    public MyPaymentProofListAdapter(Context context, List<MyPaymentProofListData> dataReceived, MyPaymentProofList news) {
        this.context = context;
        this.my_data = dataReceived;
        this.myPaymentProofList = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_payment_proof_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtRefLinkId.setText(my_data.get(position).getLink_id());
        holder.txtCompanyName.setText(my_data.get(position).getCompany());
        holder.txtPaymentProofDate.setText(my_data.get(position).getPayment_proof_date());
        holder.txtAdminApproved.setText(my_data.get(position).getIsApprovedByAdmin());
        holder.txtIstrainingComplete.setText(my_data.get(position).getIs_training_completed());
        holder.txtPendingReason.setText(my_data.get(position).getReject_reason());
        holder.txtRef_link.setText(my_data.get(position).getRef_link());

        if (my_data.get(position).getIsApproved().equals("1")) {
            holder.txtSponsorApproved.setText("YES");
        } else {
            holder.txtSponsorApproved.setText("NO, Please wait for Approval");
        }

        if (my_data.get(position).getIsApprovedByAdmin().equals("1")) {
            holder.txtAdminApproved.setText("YES");
            holder.txtUploadAgain.setVisibility(View.INVISIBLE);

        } else {
            holder.txtAdminApproved.setText("NO, Please wait for Approval");
        }

        if (my_data.get(position).getIs_training_completed().equals("1")) {
            holder.txtIstrainingComplete.setText("YES");
        } else {
            holder.txtIstrainingComplete.setText("NO, Please wait for Approval");
        }


        holder.txtUploadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdateReferenceLinkAgain.class);
                i.putExtra("link_id", my_data.get(position).getLink_id());
                i.putExtra("CompanyName", my_data.get(position).getCompany());
                context.startActivity(i);
            }
        });
        holder.txtViewProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("link_id", my_data.get(position).getLink_id());
                bundle.putString("payment_proof", my_data.get(position).getPayment_proof());
                bundle.putString("hideButton", "true");
                myPaymentProofList.showPaymentProof(bundle);
            }
        });

        holder.txtRef_link.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", my_data.get(position).getRef_link());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgNewsBanner;
        public TextView txtRefLinkId, txtCompanyName, txtRef_link, txtPaymentProofDate, txtSponsorApproved, txtAdminApproved, txtIstrainingComplete;
        public TextView txtPendingReason, txtUploadAgain, txtViewProof;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtRefLinkId = itemView.findViewById(R.id.txtRefLinkId);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtRef_link = itemView.findViewById(R.id.txtRef_link);
            txtPaymentProofDate = itemView.findViewById(R.id.txtPaymentProofDate);
            txtSponsorApproved = itemView.findViewById(R.id.txtSponsorApproved);
            txtAdminApproved = itemView.findViewById(R.id.txtAdminApproved);
            txtIstrainingComplete = itemView.findViewById(R.id.txtIstrainingComplete);
            txtPendingReason = itemView.findViewById(R.id.txtPendingReason);
            txtUploadAgain = itemView.findViewById(R.id.txtUploadAgain);
            txtViewProof = itemView.findViewById(R.id.txtViewProof);
        }
    }

}
