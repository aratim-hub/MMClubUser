package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.TelegramGroupLinks;
import com.appmart.mmcuser.models.MyCouponListData;
import com.appmart.mmcuser.sharedPreference.SponsorEligibility_SharedPref;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyCouponListAdapter extends RecyclerView.Adapter<MyCouponListAdapter.ViewHolder> {

    public int progressStatus3 = 0;
    private Context context;
    private List<MyCouponListData> my_data;
    private Handler handler = new Handler();
    TelegramGroupLinks myTeamList;

    public MyCouponListAdapter(Context context, List<MyCouponListData> dataReceived, TelegramGroupLinks myTeamList) {
        this.context = context;
        this.my_data = dataReceived;
        this.myTeamList = myTeamList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_coupon_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btnJoinGroup.setVisibility(View.INVISIBLE);


        if (my_data.get(position).getGroup_for().equals("0")) {

            holder.txtTelegramGroupFor.setText("MMC Member");
            holder.txtTelegramGroupDescripion.setText("Anyone who is official member of MMC Can join this group");
            holder.btnJoinGroup.setVisibility(View.VISIBLE);

        }
        else if (my_data.get(position).getGroup_for().equals("1")) {
            holder.txtTelegramGroupFor.setText("Sponsor");
            holder.txtTelegramGroupDescripion.setText("Only Sponsor can join this group");
            if (SponsorEligibility_SharedPref.getInstance(context).getIS_SPONSOR().equals("true")) {
                holder.btnJoinGroup.setVisibility(View.VISIBLE);
            }

        } else if (my_data.get(position).getGroup_for().equals("2")) {
            holder.txtTelegramGroupFor.setText("Great Sponsor");
            holder.txtTelegramGroupDescripion.setText("Only Great Sponsor can join this group");
            if (SponsorEligibility_SharedPref.getInstance(context).getIS_GREAT_SPONSOR().equals("true")) {
                holder.btnJoinGroup.setVisibility(View.VISIBLE);
            }
        }
        holder.btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = my_data.get(position).getGroup_link();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTelegramGroupDescripion, txtTelegramGroupFor;
        public Button btnJoinGroup;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtTelegramGroupDescripion = itemView.findViewById(R.id.txtTelegramGroupDescripion);
            txtTelegramGroupFor = itemView.findViewById(R.id.txtTelegramGroupFor);
            btnJoinGroup = itemView.findViewById(R.id.btnJoinGroup);
        }
    }
}
