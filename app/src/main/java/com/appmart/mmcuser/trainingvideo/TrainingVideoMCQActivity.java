package com.appmart.mmcuser.trainingvideo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainingVideoMCQActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TraningVideoMCQListAdapter adapter;
    private List<TrainingVideoMCQListData> data_list = null;
    private Button btnSubmitInterview;
    private int trueCount = 0;
    public static ArrayList<Boolean> CORRECTOPTION=new ArrayList<>();
    String training_video_id, video_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_video_m_c_q);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnSubmitInterview = findViewById(R.id.btnSubmitInterview);
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(TrainingVideoMCQActivity.this));

        adapter = new TraningVideoMCQListAdapter(this, data_list);
        recyclerView.setAdapter(adapter);
        video_questions = getIntent().getStringExtra("video_questions");
        training_video_id = getIntent().getStringExtra("training_video_id");

        load_data_from_server(video_questions);

        btnSubmitInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean correct;
                for (int i = 0; i < CORRECTOPTION.size(); i++) {
                    correct = CORRECTOPTION.get(i);
                    if (correct) {
                        trueCount++;
                    }

                }
                if (data_list.size() == trueCount) {
                    //This condition will executes when all answers are correct
                    correctAnswerDialog();
                } else {
                    int wrongCount = CORRECTOPTION.size() - trueCount;
                    wrongAnswerDialog(wrongCount);
                }
                trueCount = 0;

            }
        });
    }
    private void wrongAnswerDialog(int wrongCount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingVideoMCQActivity.this);
        builder.setTitle("Your "+wrongCount+" Answer(s) is Wrong");
        builder.setMessage("You have to see video again and then give Correct Answers");
        builder.setPositiveButton("Okay ", null);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });
        dialog.setCancelable(false);
    }

    private void correctAnswerDialog() {
        UpdateVideoSeenStatus updateVideoSeenStatus = new UpdateVideoSeenStatus(TrainingVideoMCQActivity.this, training_video_id,"MMC");

        String fname = Profile_SharedPref.getInstance(TrainingVideoMCQActivity.this).getFName();
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingVideoMCQActivity.this);
        // Set a title for alert dialog
        builder.setTitle("Congratulations "+fname);
        // Show a message on alert dialog
        builder.setMessage("Your All Answers Are Correct, Now You can See Next Video");
        // Set the positive button
        builder.setPositiveButton("Okay, Thank You", null);
        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    finish();

                } catch (Exception e) {

                }
                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
    }

    private void load_data_from_server(final String video_questions) {
        data_list.clear();
        CORRECTOPTION.clear();
        ConstantMethods.loaderDialog(TrainingVideoMCQActivity.this);

        final String userid = Profile_SharedPref.getInstance(TrainingVideoMCQActivity.this).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                try {
                    JSONArray array = new JSONArray(video_questions);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        TrainingVideoMCQListData data = new TrainingVideoMCQListData(object.getString("question_id"),
                                object.getString("question"),
                                object.getString("option_a"),
                                object.getString("option_b"),
                                object.getString("option_c"),
                                object.getString("option_d"),
                                object.getString("correct_answer"));
                        data_list.add(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(TrainingVideoMCQActivity.this);
                adapter.notifyDataSetChanged();
                for (int ii = 0; ii < data_list.size(); ii++) {
                    CORRECTOPTION.add(false);
                }
            }
        };
        task.execute();
    }

}