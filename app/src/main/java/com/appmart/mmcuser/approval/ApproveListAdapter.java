package com.appmart.mmcuser.approval;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.businessturnover.TurnOverModel;
import com.appmart.mmcuser.businessturnover.TurnoverList;
import com.appmart.mmcuser.businessturnover.TurnoverListData;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class ApproveListAdapter extends RecyclerView.Adapter<ApproveListAdapter.ViewHolder> {

    TurnoverList turnoverList;
    private Context context;
    private List<TurnOverModel> response_body;
    private List<TurnoverListData> my_data;
    private Handler handler = new Handler();
    private OnItemClickListener listener;
//    LinkedHashMap<String, List<TurnoverListData>> hashMap = new LinkedHashMap<String, List<TurnoverListData>>();
    private String[] mKeys;
    public ApproveListAdapter(Context context, List<TurnOverModel> response) {
        this.context = context;
        this.response_body = response;
//        this.hashMap = hashMap;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        mKeys = hashMap.keySet().toArray(new String[hashMap.size()]);
        holder.tvsponser.setText(response_body.get(position).id+" "+response_body.get(position).firstname+" "+response_body.get(position).lastname+", "+response_body.get(position).turnover_for);
//        Log.d("position ",""+mKeys.length);

        holder.tvspCompany2.setText(response_body.get(position).turnOver.get(0).company_name);
        holder.tvspCompany2RS.setText("\u20B9 " +response_body.get(position).turnOver.get(0).turnover_amount);
        holder.tvspCompany2BV.setText("BV: " +response_body.get(position).turnOver.get(0).turnover_bv);

        holder.tvspCompany3R.setText(response_body.get(position).turnOver.get(1).company_name);
        holder.tvspCompany3RRS.setText("\u20B9 " + response_body.get(position).turnOver.get(1).turnover_amount);
        holder.tvspCompany3RBV.setText("BV: " +response_body.get(position).turnOver.get(1).turnover_bv);

        holder.tvspCompany3O.setText(response_body.get(position).turnOver.get(2).company_name+" Recharge");
        holder.tvspCompany3ORS.setText("\u20B9 " + response_body.get(position).turnOver.get(2).turnover_amount);
        holder.tvspCompany3OBV.setText("BV: " +response_body.get(position).turnOver.get(2).turnover_bv);

        holder.tvspCompany4.setText(response_body.get(position).turnOver.get(3).company_name+" Online");
        holder.tvspCompany4RS.setText("\u20B9 " + response_body.get(position).turnOver.get(3).turnover_amount);
        holder.tvspCompany4BV.setText("BV: " +response_body.get(position).turnOver.get(3).turnover_bv);

        holder.tvspCompany1.setText(response_body.get(position).turnOver.get(4).company_name);
        holder.tvspCompany1RS.setText("\u20B9 " + response_body.get(position).turnOver.get(4).turnover_amount);
        holder.tvspCompany1BV.setText("BV: " +response_body.get(position).turnOver.get(4).turnover_bv);

        int is_turnoved_approved = Integer.valueOf(response_body.get(position).is_turnoved_approved);

        if (is_turnoved_approved == 0){
            holder.btnApprove.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
        }else if (is_turnoved_approved == 1){
            holder.layoutApproveAndRejectButton.setVisibility(View.GONE);
            holder.lblApprove.setVisibility(View.VISIBLE);
        }else {
            holder.layoutApproveAndRejectButton.setVisibility(View.GONE);
            holder.lblReject.setVisibility(View.VISIBLE);
        }

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onApproveItemClick(response_body.get(position));
                }
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onRejectItemClick(response_body.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return response_body.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvspCompany1,tvsponser, tvspCompany1RS,tvspCompany1BV;
        public TextView tvspCompany3R,tvspCompany3RRS,tvspCompany3RBV;
        public TextView tvspCompany3O,tvspCompany3ORS,tvspCompany3OBV;
        public TextView tvspCompany4,tvspCompany4RS,tvspCompany4BV;
        public TextView tvspCompany2,tvspCompany2RS,tvspCompany2BV;
        Button btnApprove,btnReject;
        TextView lblApprove,lblReject;
        LinearLayout layoutApproveAndRejectButton;

        public ViewHolder(final View itemView) {
            super(itemView);
            layoutApproveAndRejectButton = itemView.findViewById(R.id.layoutApproveAndRejectButton);
            tvsponser = itemView.findViewById(R.id.tvsponser);

            tvspCompany2 = itemView.findViewById(R.id.tvspCompany2);
            tvspCompany2RS = itemView.findViewById(R.id.tvspCompany2RS);
            tvspCompany2BV = itemView.findViewById(R.id.tvspCompany2BV);

            tvspCompany3R= itemView.findViewById(R.id.tvspCompany3R);
            tvspCompany3RRS= itemView.findViewById(R.id.tvspCompany3RRS);
            tvspCompany3RBV= itemView.findViewById(R.id.tvspCompany3RBV);

            tvspCompany3O= itemView.findViewById(R.id.tvspCompany3O);
            tvspCompany3ORS= itemView.findViewById(R.id.tvspCompany3ORS);
            tvspCompany3OBV= itemView.findViewById(R.id.tvspCompany3OBV);

            tvspCompany4= itemView.findViewById(R.id.tvspCompany4);
            tvspCompany4RS= itemView.findViewById(R.id.tvspCompany4RS);
            tvspCompany4BV= itemView.findViewById(R.id.tvspCompany4BV);

            tvspCompany1= itemView.findViewById(R.id.tvspCompany1);
            tvspCompany1RS= itemView.findViewById(R.id.tvspCompany1RS);
            tvspCompany1BV= itemView.findViewById(R.id.tvspCompany1BV);

            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);

            lblApprove = itemView.findViewById(R.id.lblApprove);
            lblReject = itemView.findViewById(R.id.lblReject);


        }
    }


    private void getDate(List<TurnoverListData> data_list,final ViewHolder holder,int position){
            String month = data_list.get(position).turnover_month;
            boolean isVisible = true;

            if (position < data_list.size()) {
                String nextMonth = data_list.get(position + 1).turnover_month;

                if (!month.equals(nextMonth)) {
                    holder.tvsponser.setVisibility(View.VISIBLE);
                    holder.tvsponser.setText(data_list.get(position).turnover_for);

                } else {
                    if (isVisible){
                        holder.tvsponser.setVisibility(View.VISIBLE);
                        holder.tvsponser.setText(data_list.get(position).turnover_for);
                        isVisible = false;
                    }else {
                        holder.tvsponser.setVisibility(View.GONE);
                    }

                }
        }
    }


    public interface OnItemClickListener {
        void onApproveItemClick(TurnOverModel model);
        void onRejectItemClick(TurnOverModel model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
