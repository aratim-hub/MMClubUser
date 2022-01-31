package com.appmart.mmcuser.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.utils.ConstantMethods;

public class PlayTrainingVideo extends AppCompatActivity {
    private ProgressBar progressBar;
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    String call_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_training_video);
        getSupportActionBar().hide();
        String video_url = getIntent().getStringExtra("video_url");
        call_from = getIntent().getStringExtra("call_from");

        vv = findViewById(R.id.vv);
        progressBar = findViewById(R.id.progrss);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        uri = Uri.parse(video_url);
        progressBar.setVisibility(View.VISIBLE);
        vv.setMediaController(mediacontroller);
        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();

        LinearLayout layout_lyconetMessege = findViewById(R.id.layout_lyconetMessege);
        ImageView txtLyconetLinktoProceed = findViewById(R.id.txtLyconetLinktoProceed);
//        layout_lyconetMessege.setVisibility(View.INVISIBLE);

        txtLyconetLinktoProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantMethods.showAlertMessege(PlayTrainingVideo.this,"Warning","You can proceed to cashback after completion of the video");
            }
        });
        try {
            if (call_from.equalsIgnoreCase("training")) {
                layout_lyconetMessege.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }

            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    if (call_from.equalsIgnoreCase("training")) {
                        //This condition will execute if this activity
                        //UpdateStatus is class which update users status on server
                        //Status=4 indicate that introduction video seen by user
//                        UpdateStatus updateStatus = new UpdateStatus(PlayTrainingVideo.this, "4");
//                        General_SharedPref.getInstance(PlayTrainingVideo.this).saveJoiningStatus("4");

                    } else {
                        finish();
                    }
                } catch (Exception E) {
                    finish();
                }

            }
        });
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
