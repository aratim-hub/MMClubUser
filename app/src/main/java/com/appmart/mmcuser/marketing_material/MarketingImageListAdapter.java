package com.appmart.mmcuser.marketing_material;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.appmart.mmcuser.activities.FullImageShare;
import com.appmart.mmcuser.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingImageListAdapter extends RecyclerView.Adapter<MarketingImageListAdapter.ViewHolder> {

    private Context context;
    private List<MarketingImageListData> my_data;
    private MarketingImageList marketingImageList;

    public MarketingImageListAdapter(Context context, List<MarketingImageListData> dataReceived, MarketingImageList marketingImageList) {
        this.context = context;
        this.my_data = dataReceived;
        this.marketingImageList = marketingImageList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.maketing_images_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String url = "https://manageadmin.mmclub.in/" + my_data.get(position).getImage_url();

//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.bg_overlay)
//                .fitCenter()
//                .into(holder.imgMarketingImage);


        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)

                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.eventLoadProgress.setVisibility(View.INVISIBLE);
                        holder.imgMarketingImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        holder.imgMarketingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FullImageShare.class);
                i.putExtra("imageUrl", url);
                i.putExtra("imageTitle",  my_data.get(position).getImage_title());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgMarketingImage;
        public ProgressBar eventLoadProgress;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgMarketingImage = itemView.findViewById(R.id.imgMarketingImage);
            eventLoadProgress = itemView.findViewById(R.id.eventLoadProgress);
        }
    }

}
