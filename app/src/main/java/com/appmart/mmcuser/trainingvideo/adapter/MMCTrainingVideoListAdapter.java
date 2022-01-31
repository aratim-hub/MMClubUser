package com.appmart.mmcuser.trainingvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.trainingvideo.model.AtomyTrainingVideoListData;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.appmart.mmcuser.trainingvideo.fragment.MMC_training.MMC_SEEN_VIDEO_ID;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MMCTrainingVideoListAdapter extends RecyclerView.Adapter<MMCTrainingVideoListAdapter.ViewHolder> {

    private Context context;
    private List<AtomyTrainingVideoListData> my_data;

    public MMCTrainingVideoListAdapter(Context context, List<AtomyTrainingVideoListData> dataReceived) {
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

        holder.txtVideoTitle.setText(my_data.get(position).getVideo_title());
        holder.txtVideoDesc.setText(my_data.get(position).getVideo_desc());


        final String msv_url = "https://img.youtube.com/vi/" + my_data.get(position).getVideo_url_id() + "/0.jpg";

        Glide.with(context)
                .load(msv_url)
                .fitCenter()
                .into(holder.imgVideoThumbnail);
        holder.imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMC_SEEN_VIDEO_ID.clear();

                try {
                    JSONArray array = new JSONArray(my_data.get(position).getVideo_seen_status());
                    for (int i = 0; i < my_data.size(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        MMC_SEEN_VIDEO_ID.add(object.getInt("tv_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int PreviousVdoId = 0;
                try {
                    int CurrentVdoId = Integer.parseInt(my_data.get(position).getVideo_id());
                    PreviousVdoId = Integer.parseInt(my_data.get(position - 1).getVideo_id());
                } catch (Exception e) {

                }

                if (MMC_SEEN_VIDEO_ID.contains(PreviousVdoId)) {
                    Intent i = new Intent(context, YoutubePlayVideoTraining.class);
                    i.putExtra("youtube_video_id", my_data.get(position).getVideo_url_id());
                    i.putExtra("call_from", "MMC_TRAINING");
                    i.putExtra("video_questions", my_data.get(position).getVideo_questions());
                    i.putExtra("training_video_id", my_data.get(position).getVideo_id());
                    context.startActivity(i);

                } else if (position == 0) {

                    Intent i = new Intent(context, YoutubePlayVideoTraining.class);
                    i.putExtra("youtube_video_id", my_data.get(position).getVideo_url_id());
                    i.putExtra("call_from", "MMC_TRAINING");
                    i.putExtra("video_questions", my_data.get(position).getVideo_questions());
                    i.putExtra("training_video_id", my_data.get(position).getVideo_id());
                    context.startActivity(i);

                } else {
                    Toast.makeText(context, "Not eligible to seen This VDO", Toast.LENGTH_SHORT).show();
                    ConstantMethods.showAlertMessege(context,"Warning","You Are Not eligible to Watch This Video Right Now, You Have To Watch Previous Video and Give Answers of Test");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgVideoThumbnail;
        public TextView txtVideoTitle, txtVideoDesc;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgVideoThumbnail = itemView.findViewById(R.id.imgVideoThumbnail);
            txtVideoTitle = itemView.findViewById(R.id.txtVideoTitle);
            txtVideoDesc = itemView.findViewById(R.id.txtVideoDesc);
        }
    }

}
