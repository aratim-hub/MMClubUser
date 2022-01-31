package com.appmart.mmcuser.businessturnover;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.events.EventList;
import com.appmart.mmcuser.events.EventListData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TurnoverListAdapter extends RecyclerView.Adapter<TurnoverListAdapter.ViewHolder> {

    TurnoverList turnoverList;
    private Context context;
    private List<TurnoverListData> my_data;
    private Handler handler = new Handler();

    public TurnoverListAdapter(Context context, List<TurnoverListData> dataReceived, TurnoverList turnoverList) {
        this.context = context;
        this.my_data = dataReceived;
        this.turnoverList = turnoverList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.txtEventDescription.setText(Html.fromHtml(my_data.get(position).getEvent_description()));
//        holder.txtEventDescription.setText(my_data.get(position).getEvent_description());
//        holder.txtDate.setText(my_data.get(position).getEvent_date());
//        holder.txtTime.setText(my_data.get(position).getEvent_time());
//        holder.txtJoiningLink.setText(my_data.get(position).getEvent_link());

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventDescription,txtDate,txtTime, txtJoiningLink;
        public ImageView imgEventBanner;
        public ProgressBar eventLoadProgress;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtEventDescription = itemView.findViewById(R.id.txtEventDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtEventDescription = itemView.findViewById(R.id.txtEventDescription);
            txtJoiningLink = itemView.findViewById(R.id.txtJoiningLink);
            imgEventBanner = itemView.findViewById(R.id.imgEventBanner);
            eventLoadProgress = itemView.findViewById(R.id.eventLoadProgress);
        }
    }
}
