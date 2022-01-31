package com.appmart.mmcuser.companyshopee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.CompanyShopeeAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class CompanyShopeeAddressListAdapter extends RecyclerView.Adapter<CompanyShopeeAddressListAdapter.ViewHolder> {

    private Context context;
    private List<String> my_data;

    public CompanyShopeeAddressListAdapter(Context context, List<String> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_shopee_address_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String s = my_data.get(position);
        try {
            JSONObject jsonArray = new JSONObject(s);
            if (jsonArray.has("name")){
                String str = jsonArray.getString("name");
                if (str != null){
                    if (str.length()!=0){
                        holder.txtTitle.setText(str);
                    }
                }
            }

            if (jsonArray.has("owner_name")){
                String str = jsonArray.getString("owner_name");
                if (str != null){
                    if (str.length()!=0 || !str.equals("null")){
                        holder.txtOwnerName.setText(str);
                    }else{ holder.nameDiv.setVisibility(View.GONE); }
                }else{ holder.nameDiv.setVisibility(View.GONE); }
            }else{ holder.nameDiv.setVisibility(View.GONE); }

            if (jsonArray.has("mobile_whatapp_number")){
                final String str = jsonArray.getString("mobile_whatapp_number");
                if (str != null){
                    if (str.length()!=0 || !str.equals("null")){

                        holder.txtMobile.setText(str);
                        holder.txtMobile.setTextColor(context.getResources().getColor(R.color.colorAccent));
                        holder.txtMobile.setPaintFlags(holder.txtMobile.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                        holder.txtMobile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+str));//change the number
                                context.startActivity(callIntent);
                            }
                        });

                    }else{ holder.mobileDiv.setVisibility(View.GONE); }
                }else{ holder.mobileDiv.setVisibility(View.GONE); }
            }else{ holder.mobileDiv.setVisibility(View.GONE); }

            if (jsonArray.has("app_link")){
                final String str = jsonArray.getString("app_link");
                if (str != null){
                    if (str.length()!=0 && !str.equals("null")){
                        holder.txtAppLink.setText(str);
                        holder.txtAppLink.setTextColor(context.getResources().getColor(R.color.colorAccent));
                        holder.txtAppLink.setPaintFlags(holder.txtAppLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                        holder.txtAppLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                                context.startActivity(browserIntent);
                            }
                        });
                    }else{ holder.linkDiv.setVisibility(View.GONE); }
                }else{ holder.linkDiv.setVisibility(View.GONE); }
            }else{ holder.linkDiv.setVisibility(View.GONE); }
            if (jsonArray.has("address")){
                String str = jsonArray.getString("address");
                if (str != null){
                    if (str.length()!=0 && !str.equals("null")){
                        holder.txtAddress.setText(str);
                    }else{ holder.addressDiv.setVisibility(View.GONE); }
                }else{ holder.addressDiv.setVisibility(View.GONE); }
            }else{ holder.addressDiv.setVisibility(View.GONE); }
            if (jsonArray.has("other_details")){
                String str = jsonArray.getString("other_details");
                if (str != null){
                    if (str.length()!=0 && !str.equals("null")){
                        holder.txtOther.setText(str);
                    }else{ holder.otherDiv.setVisibility(View.GONE); }
                }else{ holder.otherDiv.setVisibility(View.GONE); }
            }else{ holder.otherDiv.setVisibility(View.GONE); }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle,txtOwnerName,txtMobile,txtAppLink, txtAddress, txtOther;
        LinearLayout nameDiv,mobileDiv,linkDiv,addressDiv,otherDiv;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtOwnerName = itemView.findViewById(R.id.txtOwnerName);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtAppLink = itemView.findViewById(R.id.txtAppLink);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtOther = itemView.findViewById(R.id.txtOther);
            nameDiv = itemView.findViewById(R.id.name_div);
            mobileDiv = itemView.findViewById(R.id.mobile_div);
            linkDiv = itemView.findViewById(R.id.link_div);
            addressDiv = itemView.findViewById(R.id.address_div);
            otherDiv = itemView.findViewById(R.id.other_div);
        }
    }

}
