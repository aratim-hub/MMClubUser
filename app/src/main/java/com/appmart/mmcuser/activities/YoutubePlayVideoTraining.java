package com.appmart.mmcuser.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.trainingvideo.TrainingVideoMCQActivity;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.concurrent.TimeUnit;

public class YoutubePlayVideoTraining extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String YOUTUBE_VIDEO_ID;
    // YouTube player view
    private YouTubePlayerView youTubeView;
    String call_from,video_questions,training_video_id;
    MyPlayerStateChangeListener playerStateChangeListener;
    private ImageView imgImageBelowVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube_play_video);

        playerStateChangeListener = new MyPlayerStateChangeListener();

        YOUTUBE_VIDEO_ID = getIntent().getStringExtra("youtube_video_id");
        call_from = getIntent().getStringExtra("call_from");
        video_questions = getIntent().getStringExtra("video_questions");
        training_video_id = getIntent().getStringExtra("training_video_id");

        Log.i("TAG", "onCreate: "+YOUTUBE_VIDEO_ID);
        Log.i("TAG", "onCreate: "+call_from);
        Log.i("TAG", "onCreate: "+video_questions);
        Log.i("TAG", "onCreate: "+training_video_id);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        imgImageBelowVideo = (ImageView) findViewById(R.id.imgImageBelowVideo);

        String url = "https://manageadmin.mmclub.in/" + General_SharedPref.getInstance(YoutubePlayVideoTraining.this) .getVIDEO_IMAGE_TRAINING()+"?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(imgImageBelowVideo);


        youTubeView.initialize(YoutubeConfig.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
//            String errorMessage = String.format(
//                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        final YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);

        if (!wasRestored) {
            player.loadVideo(YOUTUBE_VIDEO_ID);
//             Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    if(player.getCurrentTimeMillis() <= 20000) {
//                        handler.postDelayed(this, 1000);
//                        Toast.makeText(YoutubePlayVideoTraining.this, "else ", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        handler.removeCallbacks(this);
//                        player.pause();
//                        Toast.makeText(YoutubePlayVideoTraining.this, "else ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, 1000);
//            Toast.makeText(this, minutes+" : "+seconds, Toast.LENGTH_SHORT).show();
//            Log.d("min: ",minutes+" : "+seconds);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeConfig.YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }





    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.

        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.

            if (call_from.equalsIgnoreCase("mmcQA")) {
                UpdateStatus updateStatus = new UpdateStatus(YoutubePlayVideoTraining.this, "4");
                General_SharedPref.getInstance(YoutubePlayVideoTraining.this).saveJoiningStatus("4");
                finish();
                YoutubePlayVideoTraining.this.sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));

            } else if (call_from.equalsIgnoreCase("MMC_TRAINING")) {
                finish();
                Intent i = new Intent(YoutubePlayVideoTraining.this, TrainingVideoMCQActivity.class);
                i.putExtra("video_questions", video_questions);
                i.putExtra("training_video_id", training_video_id);
                startActivity(i);
            }

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }

}
