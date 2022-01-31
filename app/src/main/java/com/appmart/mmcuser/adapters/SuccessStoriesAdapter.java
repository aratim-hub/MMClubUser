package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.models.SuccessStoriesData;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SuccessStoriesAdapter extends RecyclerView.Adapter<SuccessStoriesAdapter.ViewHolder> {

    String GRAPHICS_URL = null;
    private Context context;
    private List<SuccessStoriesData> my_data;
    private int lastPosition;
    private int pos;

    public SuccessStoriesAdapter(Context context, List<SuccessStoriesData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_video_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        pos = position;

        final String msv_url = "https://img.youtube.com/vi/" + my_data.get(position).getSuccess_story_url_id() + "/0.jpg";

        Glide.with(context)
                .load(msv_url)
                .fitCenter()
                .into(holder.imgVideoThumbnail);

        holder.txtVideoTitle.setText(my_data.get(position).getSuccess_story_title());
        holder.txtVideoDesc.setText(my_data.get(position).getSuccess_story_desc());

        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, YoutubePlayVideoTraining.class);
                i.putExtra("youtube_video_id", my_data.get(position).getSuccess_story_url_id());
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
        public TextView txtVideoDesc;

        public ViewHolder(View itemView) {
            super(itemView);
//            videoWebView = itemView.findViewById(R.id.videoWebView);
//            txtViewFullScreen = itemView.findViewById(R.id.txtViewFullScreen);
            txtVideoTitle = itemView.findViewById(R.id.txtVideoTitle);
            txtVideoDesc = itemView.findViewById(R.id.txtVideoDesc);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
            Animation animation = AnimationUtils.loadAnimation(context,
                    (pos > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
            itemView.startAnimation(animation);
            lastPosition = pos;
        }
    }
}
