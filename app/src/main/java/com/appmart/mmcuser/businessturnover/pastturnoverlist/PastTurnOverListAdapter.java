package com.appmart.mmcuser.businessturnover.pastturnoverlist;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.businessturnover.TurnoverList;
import com.appmart.mmcuser.businessturnover.TurnoverListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.media.CamcorderProfile.get;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class PastTurnOverListAdapter extends RecyclerView.Adapter<PastTurnOverListAdapter.ViewHolder> {

    TurnoverList turnoverList;
    private Context context;
    private List<TurnoverListData> my_data;
    private Handler handler = new Handler();
    LinkedHashMap<String, List<TurnoverListData>> hashMap = new LinkedHashMap<String, List<TurnoverListData>>();
    private String[] mKeys;
    public PastTurnOverListAdapter(Context context, List<TurnoverListData> dataReceived,LinkedHashMap<String, List<TurnoverListData>> hashMap) {
        this.context = context;
        this.my_data = dataReceived;
        this.hashMap = hashMap;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_turn_over, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mKeys = hashMap.keySet().toArray(new String[hashMap.size()]);
        holder.tvsponser.setText(mKeys[position]);
        Log.d("position ",""+mKeys.length);

        String Value = getItem(position).toString();
        holder.tvspCompany2.setText(hashMap.get(mKeys[position]).get(0).company_name);
        holder.tvspCompany2RS.setText("\u20B9 " +hashMap.get(mKeys[position]).get(0).turnover_amount);
        holder.tvspCompany2BV.setText("BV: " +hashMap.get(mKeys[position]).get(0).turnover_bv);

        holder.tvspCompany3R.setText(hashMap.get(mKeys[position]).get(1).company_name);
        holder.tvspCompany3RRS.setText("\u20B9 " + hashMap.get(mKeys[position]).get(1).turnover_amount);
        holder.tvspCompany3RBV.setText("BV: " +hashMap.get(mKeys[position]).get(1).turnover_bv);

        holder.tvspCompany3O.setText(hashMap.get(mKeys[position]).get(2).company_name+" Recharge");
        holder.tvspCompany3ORS.setText("\u20B9 " + hashMap.get(mKeys[position]).get(2).turnover_amount);
        holder.tvspCompany3OBV.setText("BV: " +hashMap.get(mKeys[position]).get(2).turnover_bv);

        holder.tvspCompany4.setText(hashMap.get(mKeys[position]).get(3).company_name+" Online");
        holder.tvspCompany4RS.setText("\u20B9 " + hashMap.get(mKeys[position]).get(3).turnover_amount);
        holder.tvspCompany4BV.setText("BV: " +hashMap.get(mKeys[position]).get(3).turnover_bv);

        holder.tvspCompany1.setText(hashMap.get(mKeys[position]).get(4).company_name);
        holder.tvspCompany1RS.setText("\u20B9 " + hashMap.get(mKeys[position]).get(4).turnover_amount);
        holder.tvspCompany1BV.setText("BV: " +hashMap.get(mKeys[position]).get(4).turnover_bv);

    }

    @Override
    public int getItemCount() {
        return hashMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvspCompany1,tvsponser, tvspCompany1RS,tvspCompany1BV;
        public TextView tvspCompany3R,tvspCompany3RRS,tvspCompany3RBV;
        public TextView tvspCompany3O,tvspCompany3ORS,tvspCompany3OBV;
        public TextView tvspCompany4,tvspCompany4RS,tvspCompany4BV;
        public TextView tvspCompany2,tvspCompany2RS,tvspCompany2BV;

        public ViewHolder(final View itemView) {
            super(itemView);
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
        }
    }

    public Object getItem(int position) {
        return hashMap.get(mKeys[position]);
    }

    boolean isVisible = true,isVisibleForSame = true;
    private void getDate(List<TurnoverListData> data_list,final ViewHolder holder,int position){
            String month = data_list.get(position).turnover_month;

            if (position < data_list.size()-1) {
                String nextMonth = data_list.get(position + 1).turnover_month;

                if (!month.equals(nextMonth)) {
                    if (isVisibleForSame) {
                        isVisibleForSame = false;
                        holder.tvsponser.setVisibility(View.VISIBLE);
                        holder.tvsponser.setText(data_list.get(position).turnover_for);
                    }else {
                        holder.tvsponser.setVisibility(View.GONE);
                    }

                } else {
                    if (isVisible){
                        isVisible = false;
                        holder.tvsponser.setVisibility(View.VISIBLE);
                        holder.tvsponser.setText(data_list.get(position).turnover_for);
                    }else {
                        holder.tvsponser.setVisibility(View.GONE);
                    }

                }
        }
    }


}
