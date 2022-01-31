package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.WalletHistoryList;
import com.appmart.mmcuser.models.WalletHistoryListData;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class WalletHistoryListAdapter extends RecyclerView.Adapter<WalletHistoryListAdapter.ViewHolder> {

    private Context context;
    private List<WalletHistoryListData> my_data;
    private WalletHistoryList walletHistoryList;
    public WalletHistoryListAdapter(Context context, List<WalletHistoryListData> OrphanageListData, WalletHistoryList walletHistoryList) {
        this.context = context;
        this.my_data = OrphanageListData;
        this.walletHistoryList = walletHistoryList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtSupportTicketId.setText("Id: "+my_data.get(position).getHistory_id());
        holder.txtTicketSubject.setText("Description: "+my_data.get(position).getDescription());
        holder.txtTicketBody.setText("Credit Amount(Rs.): "+my_data.get(position).getAmount());
        holder.txtDateTime.setText("Date: "+my_data.get(position).getDatetime());
//        holder.txtIdentyNumber.setText(my_data_filtered.get(position).getDm_identy_no());
        if (my_data.get(position).getDescription().contains("Request Rejected")) {
            holder.txtTicketSubject.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.txtTicketSubject.setTextColor(Color.parseColor("#000000"));

        }

    }


    @Override
    public int getItemCount() {
        return my_data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgProfilePic;
        public TextView txtSupportTicketId, txtTicketSubject, txtTicketBody,txtDateTime;
        public CardView card_view;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtSupportTicketId = (TextView) itemView.findViewById(R.id.txtSupportTicketId);
            txtTicketSubject = (TextView) itemView.findViewById(R.id.txtTicketSubject);
            txtTicketBody = (TextView) itemView.findViewById(R.id.txtTicketBody);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


}
