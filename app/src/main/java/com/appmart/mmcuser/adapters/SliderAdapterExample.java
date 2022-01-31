package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.models.SliderImageListData;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private int mCount;
    private List<SliderImageListData> my_data;

    public SliderAdapterExample(Context context, List<SliderImageListData> data_list) {
        this.context = context;
        this.my_data = data_list;

    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String url = "https://manageadmin.mmclub.in/" + my_data.get(position).getSliderImageURL()+"?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
        Glide.with(viewHolder.itemView)
                .load(url)
                .fitCenter()
                .into(viewHolder.imageViewBackground);


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
