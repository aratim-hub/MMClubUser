package com.appmart.mmcuser.marketing_material;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingVideoListAdapter extends RecyclerView.Adapter<MarketingVideoListAdapter.ViewHolder> {

    private Context context;
    private List<MarketingVideoListData> my_data;
    private MarketingVideoList marketingVideoList;

    public MarketingVideoListAdapter(Context context, List<MarketingVideoListData> dataReceived, MarketingVideoList marketingVideoList) {
        this.context = context;
        this.my_data = dataReceived;
        this.marketingVideoList = marketingVideoList;
    }



    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketing_video_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.imgMarketingImage.setImageDrawable(LoadImageFromWebOperationsSSSS(url));

//        Picasso.with(context)  //Here, this is context.
//                .load(url)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .placeholder(R.drawable.bg_overlay)
//                .fit()
//                .into(holder.imgMarketingImage);

//        String url = "https://manageadmin.mmclub.in/" + my_data.get(position).getVideo_url() + "?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
//
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.bg_overlay)
////                .fitCenter()
//                .into(holder.imgVideoThumbnail);
//
//        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, PlayTrainingVideo.class);
//                i.putExtra("video_url", "https://manageadmin.mmclub.in/" + my_data.get(position).getVideo_url());
//                context.startActivity(i);
//            }
//        });


        String video_humbnail_url =  "https://img.youtube.com/vi/"+my_data.get(position).getVideo_url_id()+"/0.jpg";

        Glide.with(context)
                .load(video_humbnail_url)
                .fitCenter()
                .into(holder.imgVideoThumbnail);

//        Picasso.with(context)
//                .load(video_humbnail_url)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .placeholder(R.drawable.bg_overlay)
//                .fit()
//                .into(holder.imgVideoThumbnail);

        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, YoutubePlayVideoMultisuccess.class);
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

        public ViewHolder(final View itemView) {
            super(itemView);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
        }
    }

}
