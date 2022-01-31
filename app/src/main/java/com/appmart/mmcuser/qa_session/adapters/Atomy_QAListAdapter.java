package com.appmart.mmcuser.qa_session.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.qa_session.MMC_QA;
import com.appmart.mmcuser.qa_session.NexMoney_QA;
import com.appmart.mmcuser.qa_session.model.Atomy_QAListData;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class Atomy_QAListAdapter extends RecyclerView.Adapter<Atomy_QAListAdapter.ViewHolder> {

    private Context context;
    private List<Atomy_QAListData> my_data;
    MMC_QA mmc_qa;
    NexMoney_QA nexMoney_qa;
    public Atomy_QAListAdapter(Context context, List<Atomy_QAListData> dataReceived,MMC_QA mmc_qa) {
        this.context = context;
        this.my_data = dataReceived;
        this.mmc_qa = mmc_qa;
    }

    public Atomy_QAListAdapter(Context context, List<Atomy_QAListData> dataReceived, NexMoney_QA nexMoney_qa) {
        this.context = context;
        this.my_data = dataReceived;
        this.nexMoney_qa = nexMoney_qa;

    }

    public Atomy_QAListAdapter(Context context, List<Atomy_QAListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qa_card , parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtQuestion.setText(my_data.get(position).getQuestion());
        holder.txtAnswer.setText(my_data.get(position).getAnswer());

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
                Intent i = new Intent(context, YoutubePlayVideoTraining.class);
                i.putExtra("youtube_video_id", my_data.get(position).getYoutube_video_id());
                i.putExtra("call_from", "mmcQA");
                context.startActivity(i);
                try {
                    mmc_qa.CloseActivity();
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

        public ImageView  imgVideoThumbnail;
        public TextView txtAnswer, txtQuestion;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtAnswer = itemView.findViewById(R.id.txtAnswer);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
        }
    }

}
