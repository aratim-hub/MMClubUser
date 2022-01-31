package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.MyTeamList;
import com.appmart.mmcuser.models.MyTeamListData;
import com.appmart.mmcuser.models.SupportTicketListData;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyTeamListAdapter extends RecyclerView.Adapter<MyTeamListAdapter.ViewHolder> implements Filterable {

    public int progressStatus3 = 0;
    MyTeamList myTeamList;
    private Context context;
    private List<MyTeamListData> my_data;
    private List<MyTeamListData> my_data_filtered;
    private Handler handler = new Handler();

    public MyTeamListAdapter(Context context, List<MyTeamListData> dataReceived, MyTeamList myTeamList) {
        this.context = context;
        this.my_data = dataReceived;
        this.my_data_filtered = dataReceived;
        this.myTeamList = myTeamList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.downlink_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtName.setText(my_data_filtered.get(position).getFirstname() + " " + my_data_filtered.get(position).getLastname());
        holder.txtEmail.setText(my_data_filtered.get(position).getEmail());
        holder.txtMobile.setText(my_data_filtered.get(position).getMobile_no());
        holder.txtWhatsApp.setText(my_data_filtered.get(position).getWhatsapp_no());
        holder.txtJoiningDate.setText(my_data_filtered.get(position).getCreated_at());
        final String stat = my_data_filtered.get(position).getStatus();
        int percentage = (100 * Integer.parseInt(my_data_filtered.get(position).getStatus())) / 12;
        holder.txtCurrentStatus.setText(percentage + "% Profile completed");

        holder.pb3.setProgress(Integer.parseInt(my_data_filtered.get(position).getStatus()));

//        holder.card_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProfileProgressDetails(stat);
//
//            }
//        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("downlink_user_id", my_data_filtered.get(position).getDownlink_userId());
                bundle.putString("name", my_data_filtered.get(position).getFirstname() + " " + my_data_filtered.get(position).getLastname());
                bundle.putString("email", my_data_filtered.get(position).getEmail());
                bundle.putString("mobile_number", my_data_filtered.get(position).getMobile_no());
                bundle.putString("wa_number", my_data_filtered.get(position).getWhatsapp_no());
                bundle.putString("status", my_data_filtered.get(position).getStatus());
                myTeamList.loadDetails(bundle);
            }
        });
    }

//    private void showProfileProgressDetails(String stat) {
//
//        Dialog dialogProfileProgress = new Dialog(context);
//        dialogProfileProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogProfileProgress.setContentView(R.layout.dialog_profile_progress_details);
//        dialogProfileProgress.show();
//        LinearLayout layout_profileProgress = dialogProfileProgress.findViewById(R.id.layout_profileProgress);
//
//        final TextView[] myTextViews = new TextView[USER_STATUS.size()]; // create an empty array;
//
//        for (int i = 0; i < USER_STATUS.size(); i++) {
//            // create a new textview
//            final TextView rowTextView = new TextView(context);
//
//            // set some properties of rowTextView or something
//            USER_STATUS.get(i + 1);
//            int percentage = (100 * (i+1))/9;
//
//
//            rowTextView.setText( i + 1+". "+ USER_STATUS.get(i + 1)+" ("+percentage+"%)");
//
//            rowTextView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
//
//            // add the textview to the linearlayout
//            layout_profileProgress.addView(rowTextView);
//
//            // save a reference to the textview for later
//            myTextViews[i] = rowTextView;
//        }
//    }

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
                    List<MyTeamListData> filteredList = new ArrayList<>();
                    for (MyTeamListData row : my_data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFirstname().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getLastname().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getMobile_no().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getWhatsapp_no().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCreated_at().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getStatus_per().toLowerCase().contains(charString.toLowerCase())
                        ) {
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
                my_data_filtered = (ArrayList<MyTeamListData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtEmail, txtMobile, txtWhatsApp, txtCurrentStatus, txtJoiningDate;
        public ProgressBar pb3;
        public CardView card_view;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtWhatsApp = itemView.findViewById(R.id.txtWhatsAppNo);
            txtCurrentStatus = itemView.findViewById(R.id.txtCurrentStatus);
            txtJoiningDate = itemView.findViewById(R.id.txtJoiningDate);
            pb3 = itemView.findViewById(R.id.pb3);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
