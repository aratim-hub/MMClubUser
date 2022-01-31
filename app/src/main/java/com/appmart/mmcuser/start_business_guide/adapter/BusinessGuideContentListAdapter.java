package com.appmart.mmcuser.start_business_guide.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoTraining;
import com.appmart.mmcuser.start_business_guide.model.BusinessGuideContentListData;
import com.appmart.mmcuser.start_business_guide.model.BusinessGuideVideoListData;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class BusinessGuideContentListAdapter extends RecyclerView.Adapter<BusinessGuideContentListAdapter.ViewHolder> {

    private Context context;
    private List<BusinessGuideContentListData> my_data;

    public BusinessGuideContentListAdapter(Context context, List<BusinessGuideContentListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_guide_content_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtContentTitle.setText(my_data.get(position).getTitle());
        holder.txtContent.setText(my_data.get(position).getContent());
        holder.txtContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", my_data.get(position).getContent());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgVideoThumbnail;
        public TextView txtContentTitle, txtContent;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtContentTitle = itemView.findViewById(R.id.txtContentTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }

}
