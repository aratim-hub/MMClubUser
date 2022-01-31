package com.appmart.mmcuser.sponsor_training.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingContentListData;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingVideoListData;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SponsorTrainingVideoListAdapter extends RecyclerView.Adapter<SponsorTrainingVideoListAdapter.ViewHolder> {

    private Context context;
    private List<SponsorTrainingVideoListData> my_data;

    public SponsorTrainingVideoListAdapter(Context context, List<SponsorTrainingVideoListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_guide_video_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtVideoTitle.setText(my_data.get(position).getVideo_title());


        final String msv_url = "https://img.youtube.com/vi/" + my_data.get(position).getVideo_url_id() + "/0.jpg";

        Glide.with(context)
                .load(msv_url)
                .fitCenter()
                .into(holder.imgVideoThumbnail);
        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Home.this, PlayTrainingVideo.class);
                Intent i = new Intent(context, YoutubePlayVideoTraining.class);
                i.putExtra("youtube_video_id", my_data.get(position).getVideo_url_id());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgVideoThumbnail;
        public TextView txtVideoTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtVideoTitle = itemView.findViewById(R.id.txtVideoTitle);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
        }
    }

}
