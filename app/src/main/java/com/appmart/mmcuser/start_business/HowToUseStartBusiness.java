package com.appmart.mmcuser.start_business;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class HowToUseStartBusiness extends Fragment {
    private ImageView imgVideoThumbnail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_how_to_use_start_business, container, false);
        imgVideoThumbnail = view.findViewById(R.id.imgVideoThumbnail);


        String video_humbnail_url =  "https://img.youtube.com/vi/"+ General_SharedPref.getInstance(getContext()).getSTART_BUSINESS_VIDEO_ID() +"/0.jpg";
        Picasso.get()  //Here, this is context.
                .load(video_humbnail_url)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.bg_overlay)
                .fit()
                .into(imgVideoThumbnail);

        imgVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", General_SharedPref.getInstance(getContext()).getSTART_BUSINESS_VIDEO_ID());
                startActivity(i);
            }
        });
        return view;
    }
}
