package com.appmart.mmcuser.companyshopee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.CompanyShopeeAddress;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.trainingvideo.model.AtomyTrainingVideoListData;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class CompanyShopeeListAdapter extends RecyclerView.Adapter<CompanyShopeeListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private Context context;
    private List<CompanyShopeeList> my_data;
    private final OnItemClickListener listener;

    public CompanyShopeeListAdapter(Context context, List<CompanyShopeeList> dataReceived, OnItemClickListener listener) {
        this.context = context;
        this.my_data = dataReceived;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_shopee_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtTitle.setText(my_data.get(position).getShop_name());

        holder.mainDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
             }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        CardView mainDiv;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            mainDiv = itemView.findViewById(R.id.card_view);
        }
    }

}
