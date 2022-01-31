package com.appmart.mmcuser.activities;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlayVideoMultisuccess extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String YOUTUBE_VIDEO_ID;
    // YouTube player view
    private YouTubePlayerView youTubeView;
    String call_from;
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

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        imgImageBelowVideo = (ImageView) findViewById(R.id.imgImageBelowVideo);

        String url = "https://manageadmin.mmclub.in/" + General_SharedPref.getInstance(YoutubePlayVideoMultisuccess.this) .getVIDEO_IMAGE_MULTISUCCESS()+"?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
//        Picasso.with(YoutubePlayVideoMultisuccess.this).invalidate(url);
//        Picasso.with(YoutubePlayVideoMultisuccess.this)  //Here, this is context.
//                .load(url)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .fit()
//                .into(imgImageBelowVideo);


        Glide.with(YoutubePlayVideoMultisuccess.this)
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
                                        YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);

        if (!wasRestored) {
            try {
                player.loadVideo(YOUTUBE_VIDEO_ID);
//             Hiding player controls
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

            } catch (Exception e) {
                finish();
            }
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
 finish();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }
}
