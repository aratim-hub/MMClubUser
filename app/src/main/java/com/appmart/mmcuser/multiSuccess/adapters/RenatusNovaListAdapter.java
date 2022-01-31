package com.appmart.mmcuser.multiSuccess.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.appmart.mmcuser.multiSuccess.model.RenatusNovaListData;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class RenatusNovaListAdapter extends RecyclerView.Adapter<RenatusNovaListAdapter.ViewHolder> {

    private Context context;
    private List<RenatusNovaListData> my_data;

    public RenatusNovaListAdapter(Context context, List<RenatusNovaListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_success_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if(my_data.get(position).getMsuccess_image_url().contains(".jpg")||my_data.get(position).getMsuccess_image_url().contains(".png")){
            final String url="https://manageadmin.mmclub.in/"+my_data.get(position).getMsuccess_image_url();

            Picasso.get().invalidate(url);
            Picasso.get() //Here, this is context.
                    .load(url)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .fit()
                    .into(holder.imgMultiSuccess);

        }else{
            holder.imgMultiSuccess.getLayoutParams().height=00;

        }

        holder.txtTitle.setText(my_data.get(position).getMsuccess_title());
        holder.txtDescription.setText(my_data.get(position).getMsuccess_desc());

     String video_humbnail_url =  "https://img.youtube.com/vi/"+my_data.get(position).getYoutube_video_id()+"/0.jpg";
        Picasso.get()  //Here, this is context.
                .load(video_humbnail_url)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.bg_overlay)
                .fit()
                .into(holder.imgVideoThumbnail);

        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", my_data.get(position).getYoutube_video_id());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgMultiSuccess, imgVideoThumbnail;
        public TextView txtTitle, txtDescription;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgMultiSuccess = itemView.findViewById(R.id.imgMultiSuccess);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
        }
    }
}
