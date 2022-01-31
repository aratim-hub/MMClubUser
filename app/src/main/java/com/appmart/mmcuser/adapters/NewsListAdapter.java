package com.appmart.mmcuser.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.fragments.News;
import com.appmart.mmcuser.models.NewsListData;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

private Context context;
private List<NewsListData> my_data;
private News news;

public NewsListAdapter(Context context,List<NewsListData> dataReceived,News news){
        this.context=context;
        this.my_data=dataReceived;
        this.news=news;
        }

@Override
public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType){

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_card,parent,false);

        return new ViewHolder(itemView);
        }

@Override
public void onBindViewHolder(ViewHolder holder,final int position){

        holder.txtNewsTitle.setText(my_data.get(position).getNews_title());
        holder.txtNewsBody.setText(my_data.get(position).getNews_body());
        holder.txtPublishedAt.setText(my_data.get(position).getCreated_at());

        if(my_data.get(position).getNews_url().contains(".jpg")||my_data.get(position).getNews_url().contains(".png")){
final String url="https://manageadmin.mmclub.in/"+my_data.get(position).getNews_url();

            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .into(holder.imgNewsBanner);


//        Picasso.with(context).invalidate(url);
//        Picasso.with(context)  //Here, this is context.
//        .load(url)
//        .networkPolicy(NetworkPolicy.NO_CACHE)
////                    .placeholder(R.mipmap.icon_user)
//        .fit()
//        .into(holder.imgNewsBanner);

        }else{

        holder.imgNewsBanner.getLayoutParams().height=00;

        }


//
//        holder.imgMarketingImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, FullImageView.class);
//                i.putExtra("imageUrl", url);
//                context.startActivity(i);
//            }
//        });
        }

@Override
public int getItemCount(){
        return my_data.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgNewsBanner;
    public TextView txtNewsTitle, txtNewsBody, txtPublishedAt;

    public ViewHolder(final View itemView) {
        super(itemView);
        imgNewsBanner = itemView.findViewById(R.id.imgNewsBanner);
        txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
        txtNewsBody = itemView.findViewById(R.id.txtNewsBody);
        txtPublishedAt = itemView.findViewById(R.id.txtPublishedAt);
    }
}

}
