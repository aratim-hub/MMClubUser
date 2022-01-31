package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.models.SupportTicketListData;
import com.appmart.mmcuser.fragments.SupportTicketList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SupportTicketListAdapter extends RecyclerView.Adapter<SupportTicketListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<SupportTicketListData> my_data;
    private List<SupportTicketListData> my_data_filtered;
    private SupportTicketList supportTicketList;
    public SupportTicketListAdapter(Context context, List<SupportTicketListData> OrphanageListData, SupportTicketList supportTicketList) {
        this.context = context;
        this.my_data = OrphanageListData;
        this.my_data_filtered = OrphanageListData;
        this.supportTicketList = supportTicketList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_ticket_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtSupportTicketId.setText("Ticket Id: "+my_data_filtered.get(position).getSupport_id());
        holder.txtTicketSubject.setText("Subject: "+my_data_filtered.get(position).getTicket_subject());
        holder.txtTicketBody.setText("Messege: "+my_data_filtered.get(position).getTicket_msg());
//        holder.txtIdentyNumber.setText(my_data_filtered.get(position).getDm_identy_no());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("support_ticket_id",my_data_filtered.get(position).getSupport_id());
                bundle.putString("support_subject",my_data_filtered.get(position).getTicket_subject());
                bundle.putString("support_messege",my_data_filtered.get(position).getTicket_msg());
                supportTicketList.openFullChat(bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return my_data_filtered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    my_data_filtered = my_data;
                } else {
                    List<SupportTicketListData> filteredList = new ArrayList<>();
                    for (SupportTicketListData row : my_data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTicket_subject().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTicket_msg().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    my_data_filtered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = my_data_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                my_data_filtered = (ArrayList<SupportTicketListData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgProfilePic;
        public TextView txtSupportTicketId, txtTicketSubject, txtTicketBody;
        public CardView card_view;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtSupportTicketId = (TextView) itemView.findViewById(R.id.txtSupportTicketId);
            txtTicketSubject = (TextView) itemView.findViewById(R.id.txtTicketSubject);
            txtTicketBody = (TextView) itemView.findViewById(R.id.txtTicketBody);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


}
