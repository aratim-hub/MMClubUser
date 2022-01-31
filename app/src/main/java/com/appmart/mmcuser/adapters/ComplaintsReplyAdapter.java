package com.appmart.mmcuser.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appmart.mmcuser.models.ComplaintsReplyModel;
import com.appmart.mmcuser.R;

import java.util.List;

public class ComplaintsReplyAdapter extends RecyclerView.Adapter<ComplaintsReplyAdapter.ViewHolder> {

    private Context mcontext;
    private List<ComplaintsReplyModel> planModels;

    public ComplaintsReplyAdapter(Context mcontext, List<ComplaintsReplyModel> planModels) {

        this.mcontext = mcontext;
        this.planModels = planModels;
    }

    @NonNull
    @Override
    public ComplaintsReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.fragment_reply_to_complaints_card, null);
        ComplaintsReplyAdapter.ViewHolder holder = new ComplaintsReplyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintsReplyAdapter.ViewHolder viewHolder, int i) {
        ComplaintsReplyModel poJo = planModels.get(i);

        String admin_id = poJo.getAdmin_id();
        String customer_id = poJo.getCustomer_id();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if (!admin_id.equals("0")) {
            params.addRule(RelativeLayout.ALIGN_PARENT_START);

        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_END);

            // viewHolder.crdMessageCard.setBackgroundResource(R.drawable.message_box);
        }

        viewHolder.crdMessageCard.setLayoutParams(params);
        viewHolder.txtReply.setText(poJo.getReply_message());
    }

    @Override
    public int getItemCount() {
        return planModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAdminName, txtReply;
        CardView crdMessageCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReply = itemView.findViewById(R.id.txtAdminReply);
            crdMessageCard = itemView.findViewById(R.id.crdMessageCard);
        }
    }
}