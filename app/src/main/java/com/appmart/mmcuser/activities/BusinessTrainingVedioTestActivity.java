package com.appmart.mmcuser.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.FragLinkRequests;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.SharedPref;
import com.appmart.mmcuser.trainingvideo.adapter.TrainingVideoListAdapter;
import com.appmart.mmcuser.trainingvideo.adapter.TraningVideoTestListAdapter;
import com.appmart.mmcuser.trainingvideo.model.AtomyTrainingVideoListData;
import com.appmart.mmcuser.trainingvideo.model.VedioQuestionList;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_TRAINING_VIDEO_BY_CATEGORY;
import static com.appmart.mmcuser.utils.ServerAddress.GET_TRAINING_VIDEO_BY_CATEGORY1;
import static com.appmart.mmcuser.utils.ServerAddress.SUBMIT_TRAINING_VIDEO;
import static com.appmart.mmcuser.utils.ServerAddress.SUBMIT_TRAINING_VIDEO_STATUS;

public class BusinessTrainingVedioTestActivity extends  YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    private List<AtomyTrainingVideoListData> data_list = null;
    List<VedioQuestionList> questionList = null;

    MyPlayerStateChangeListener playerStateChangeListener;
    private YouTubePlayerView youTubeView;
    YouTubePlayer youTubePlayer;

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    RecyclerView recyclerViewQuesion;
    AppCompatTextView btn_continue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_training_vedio_test);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        data_list = new ArrayList<>();
        playerStateChangeListener = new MyPlayerStateChangeListener();

        btn_continue = findViewById(R.id.btn_continue);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        recyclerViewQuesion = findViewById(R.id.recyclerViewQuesion);
        recyclerViewQuesion.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int index = Integer.valueOf(SharedPref.getString(BusinessTrainingVedioTestActivity.this,"test_index"));

                if (index == (data_list.size()-2)){ btn_continue.setText("Submit");}

                if (questionList.size() != 0){

                    boolean status = true;

                    for (int i=0; i<questionList.size(); i++){

                        if (questionList.get(i).getStatus()==null){
                            questionList.get(i).setStatus("2");
                            alertDialogBox("Select option for Que."+String.valueOf( i+1 )+" .");
                            status = false;
                            break;
                        } else if (questionList.get(i).getStatus().equals("")){
                            questionList.get(i).setStatus("2");
                            alertDialogBox("Select option for Que."+String.valueOf( i+1 )+" .");
                            status = false;
                            break;
                        } else if (questionList.get(i).getStatus().equals("0")){
                            questionList.get(i).setStatus("2");
                            alertDialogBox("Selected option for Que."+String.valueOf( i+1 )+" is wrong.");
                            status = false;
                            break;
                        }
                    }

                    if (status){
                       if (data_list.size() == index+1){
                           getAllAnswers(index);
//                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }else{
                           AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTrainingVedioTestActivity.this);
                           // Set a title for alert dialog
//                           builder.setTitle("");
                           // Show a message on alert dialog

                           builder.setMessage(Html.fromHtml("Test Successfully Completed for Above Questions."));
                           // Set the positive button
                           builder.setPositiveButton("Ok", null);

                           final AlertDialog dialog = builder.create();

                           dialog.show();

                           Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                           MoreDetails.setTextColor(Color.parseColor("#b90d86"));
                           MoreDetails.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialog.dismiss();
                                   int x = index;
                                   progressDialog.show();
                                   GoToNextVedio(x);
//                                   SharedPref.putString(BusinessTrainingVedioTestActivity.this,"test_index",String.valueOf(x + 1));
//                                   loadQuesions(data_list.get(x + 1));
//                                   loadVedio(data_list.get(x + 1));
                               }
                           });

                           dialog.setCanceledOnTouchOutside(false);
                           dialog.setCancelable(false);

                        }
                    }else{
                        TraningVideoTestListAdapter adapter = new TraningVideoTestListAdapter(getApplicationContext(), questionList ,data_list.get(index).getVideo_seen_status() , new TraningVideoTestListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(List<VedioQuestionList> item) {
                                questionList = item;
                            }
                        });
                        recyclerViewQuesion.setAdapter(adapter);
                    }
                }else{
                    if (data_list.size() == index+1){
                        getAllAnswers(index);
                    }else{
                        progressDialog.show();
                        GoToNextVedio(index);
                    }
                }
            }
        });

        load_data_from_server();
        progressDialog.show();
    }

    private void GoToNextVedio(final int index) {
        final int x = index;
        final String User_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", User_id)
                        .add("video_id", data_list.get(x).getVideo_id())
//                        .add("company", "MMC")
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(SUBMIT_TRAINING_VIDEO)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

//                    JSONArray array = new JSONArray(response.body().string());
//
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.getJSONObject(i);
//                        AtomyTrainingVideoListData data = new AtomyTrainingVideoListData(
//                                object.getString("video_id"),
//                                object.getString("video_title"),
//                                object.getString("video_desc"),
//                                object.getString("video_url"),
//                                object.getString("video_url_id"),
//                                object.getString("video_category"),
//                                object.getString("video_questions"),
//                                object.getString("video_seen_status")
//                        );
//                        data_list.add(data);
//
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                SharedPref.putString(BusinessTrainingVedioTestActivity.this,"test_index",String.valueOf(index + 1));
                loadQuesions(data_list.get(index + 1));
                loadVedio(data_list.get(index + 1));
            }
        };

        task.execute();

    }

    private void alertDialogBox(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTrainingVedioTestActivity.this);
        // Set a title for alert dialog
        builder.setTitle("Choose Correct Option");
        // Show a message on alert dialog

        builder.setMessage(Html.fromHtml(msg));
        // Set the positive button
        builder.setPositiveButton("Ok", null);

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        MoreDetails.setTextColor(Color.parseColor("#b90d86"));
        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void getAllAnswers(int index) {
        progressDialog.show();
        final int x = index;
        final String User_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", User_id)
                        .add("video_id", data_list.get(x).getVideo_id())
                        .add("company", "MMC")
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(SUBMIT_TRAINING_VIDEO_STATUS)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    Log.i("TAG", "doInBackground: "+response.body().string());

//                    if (response!=null){
//                        JSONObject obj = new JSONObject(response.body().string());
//                        responeStatus = obj.getString("success");
//                        respoonseMsg = obj.getString("message");
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessTrainingVedioTestActivity.this);
                // Set a title for alert dialog
//                builder.setTitle("Choose Correct Option");
                // Show a message on alert dialog
                builder.setTitle("CONGRATULATION...!");
                builder.setMessage("आप ने MMC Membership Testing paas कर ली है,\n" +
                        "MMC की ओर से आप का हार्दिक अभिनंदन है!\n" +
                        "\uD83D\uDC90\uD83D\uDC90\uD83C\uDF89\uD83C\uDFAF\uD83C\uDF89\uD83D\uDC90\uD83D\uDC90\n" +
                        "\n" +
                        "आगे, MMC Admin की तरफ से आप को Business Approval दिया जायेगा,\n" +
                        "इस Approval के लिए,\n" +
                        "आप को MMC Admin No पर नीचे दिए डॉक्यूमेंट लिस्ट भेजना है! भेजनेवाले डिटेल स्क्रीनशॉर्ट -\n" +
                        "1.Fb Profile pic/ screenshot\n" +
                        "2.MMC के लिए अलग whats app No रखना है और इस नम्बर का DP MMC बैनर रखना है, \n" +
                        "और इसी whats app से सभी डिटेल भेजना है\n" +
                        "3.आप ने जिस कंपनी में ID लगाए हुवी है, जैसे HHI/Ok Life का Payment Tax Invoice या , GIG का जोइनिंग सर्टिफिकेट या Royal Q का Nik Name स्क्रीनशॉर्ट\n" +
                        "4.आप का नाम और MMC रजिस्ट्रर मोबाइल नंबर\n" +
                        "5.Sponsor Name & Mobile No \n" +
                        "\n" +
                        "यह सभी डिटेल MMC Admin No पर भेज देना है,\n" +
                        "फिर आप की कंपनी Joining कन्फर्म/चेक कर के आप को अगले 2 दिन में अप्रूवल दिया जायेगा!\n" +
                        "\n" +
                        " डिटेल/डॉक्यूमेंट भेजने के लिए\n" +
                        "MMC Admin No - \n" +
                        "9702306464\n" +
                        "\n" +
                        "जय हिंद!\n" +
                        "MMC\n" +
                        "आप की सफलता के लिए समर्पित!");
                // Set the positive button
                builder.setPositiveButton("Okay.. Thank You", null);

                final AlertDialog dialog = builder.create();

                dialog.show();

                Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                MoreDetails.setTextColor(Color.parseColor("#b90d86"));
                MoreDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Home.class));
                    }
                });

                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
//                alertDialogBox("Training and test completed successfully");

             }
        };

        task.execute();
//        for (int i=0; i<data_list.size(); i++){
//            String quesionList = data_list.get(i).getVideo_questions();
//
//            questionList = new ArrayList<>();
//            String answer = "";
//            if (quesionList != null){
//                try {
//                    JSONArray jsonArray = new JSONArray(quesionList);
//                    if (jsonArray.length()!=0){
//                        for (int k=0; k<jsonArray.length(); k++){
//                            JSONObject jsonObject = new JSONObject(jsonArray.get(k).toString());
//                            answer = answer + ",("+jsonObject.getString("correct_option")+"-"+jsonObject.getString(jsonObject.getString("correct_option"))+")\n";
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Toast.makeText(this, "Test Completed Successfully", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),Home.class));
//        }
    }

    private void loadVedio(AtomyTrainingVideoListData item) {
        String YOUTUBE_VIDEO_ID = item.getVideo_url_id();
        if (YOUTUBE_VIDEO_ID !=null ){
            if (YOUTUBE_VIDEO_ID.length()!=0){
                youTubeView.setVisibility(View.VISIBLE);
                youTubePlayer.loadVideo(YOUTUBE_VIDEO_ID);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            }else{
                youTubeView.setVisibility(View.GONE);
            }
        }else{
            youTubeView.setVisibility(View.GONE);
        }

    }

    private void load_data_from_server() {
        final String User_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", User_id)
                        .add("company", "MMC")
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_TRAINING_VIDEO_BY_CATEGORY1)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        AtomyTrainingVideoListData data = new AtomyTrainingVideoListData(
                                object.getString("video_id"),
                                object.getString("video_title"),
                                object.getString("video_desc"),
                                object.getString("video_url"),
                                object.getString("video_url_id"),
                                object.getString("video_category"),
                                object.getString("video_questions"),
                                object.getString("video_seen_status")
                        );
                        data_list.add(data);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                if (data_list != null){
                    if (data_list.size() != 0){
                        loadQuesions(data_list.get(Integer.valueOf(SharedPref.getString(BusinessTrainingVedioTestActivity.this,"test_index"))));
                    }else{
                        Toast.makeText(BusinessTrainingVedioTestActivity.this, "data not found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BusinessTrainingVedioTestActivity.this, "data not found", Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }

    private void loadQuesions(AtomyTrainingVideoListData item) {
        youTubeView.initialize(YoutubeConfig.YOUTUBE_API_KEY, BusinessTrainingVedioTestActivity.this);

        String quesionList = item.getVideo_questions();
        String video_status = item.getVideo_seen_status();

        questionList = new ArrayList<>();

        TraningVideoTestListAdapter adapter = new TraningVideoTestListAdapter(getApplicationContext(), questionList ,video_status , new TraningVideoTestListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<VedioQuestionList> item) {
                questionList = item;
            }
        });
        recyclerViewQuesion.setAdapter(adapter);

        if (quesionList != null){
            try {
                JSONArray jsonArray = new JSONArray(quesionList);
                if (jsonArray.length()!=0){
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        VedioQuestionList videoQuestionList = new VedioQuestionList();
                        videoQuestionList.setQuestion_id(jsonObject.getString("question_id"));
                        videoQuestionList.setTv_id(jsonObject.getString("tv_id"));
                        videoQuestionList.setQuestion(jsonObject.getString("question"));
                        videoQuestionList.setOption_a(jsonObject.getString("option_a"));
                        videoQuestionList.setOption_b(jsonObject.getString("option_b"));
                        videoQuestionList.setOption_c(jsonObject.getString("option_c"));
                        videoQuestionList.setOption_d(jsonObject.getString("option_d"));
                        videoQuestionList.setCorrect_option(jsonObject.getString("correct_option"));
                        videoQuestionList.setCorrect_answer(jsonObject.getString(jsonObject.getString("correct_option")));
                        videoQuestionList.setStatus("");
                        questionList.add(videoQuestionList);
                    }
                    adapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

//            if (call_from.equalsIgnoreCase("mmcQA")) {
//                UpdateStatus updateStatus = new UpdateStatus(YoutubePlayVideoTraining.this, "4");
//                General_SharedPref.getInstance(YoutubePlayVideoTraining.this).saveJoiningStatus("4");
//                finish();
//                YoutubePlayVideoTraining.this.sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));
//
//            } else if (call_from.equalsIgnoreCase("MMC_TRAINING")) {
//                finish();
//                Intent i = new Intent(YoutubePlayVideoTraining.this, TrainingVideoMCQActivity.class);
//                i.putExtra("video_questions", video_questions);
//                i.putExtra("training_video_id", training_video_id);
//                startActivity(i);
//            }

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);

        if (!wasRestored) {
            String YOUTUBE_VIDEO_ID = data_list.get(0).getVideo_url_id();
            player.loadVideo(YOUTUBE_VIDEO_ID);
//             Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer = player;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeConfig.YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}