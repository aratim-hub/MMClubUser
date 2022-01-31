package com.appmart.mmcuser.marketing_material;

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
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingContentListAdapter extends RecyclerView.Adapter<MarketingContentListAdapter.ViewHolder> {

    private Context context;
    private List<MarketingContentListData> my_data;
    private MarketingContentList marketingContentList;

    public MarketingContentListAdapter(Context context, List<MarketingContentListData> dataReceived, MarketingContentList marketingVideoList) {
        this.context = context;
        this.my_data = dataReceived;
        this.marketingContentList = marketingVideoList;
    }



    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketing_content_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txtClosingContent.setText(my_data.get(position).getContent());


        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", my_data.get(position).getContent());
                clipboard.setPrimaryClip(clip);
                clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                    @Override
                    public void onPrimaryClipChanged() {
                        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_LONG).show();                        }
                });
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtClosingContent;
        public CardView card_view;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtClosingContent = itemView.findViewById(R.id.txtClosingContent);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }

}
