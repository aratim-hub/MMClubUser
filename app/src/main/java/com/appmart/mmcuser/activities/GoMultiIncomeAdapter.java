package com.appmart.mmcuser.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.models.GoMultiincomeMemberListData;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingContentListData;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class GoMultiIncomeAdapter extends RecyclerView.Adapter<GoMultiIncomeAdapter.ViewHolder> {

    private Context context;
    private List<GoMultiincomeMemberListData> my_data;

    public GoMultiIncomeAdapter(Context context, List<GoMultiincomeMemberListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gomultiincome_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtName.setText(my_data.get(position).getDownline_id()+" - "+my_data.get(position).getDownline_name());


    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);

        }
    }

}
