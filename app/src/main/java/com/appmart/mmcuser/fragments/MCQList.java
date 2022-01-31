package com.appmart.mmcuser.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.MCQListAdapter;
import com.appmart.mmcuser.models.MCQListData;
import com.appmart.mmcuser.models.StoreSelectedAnswerDataModel;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_MCQ;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MCQList extends Fragment {

    private RecyclerView recyclerView;
    private MCQListAdapter adapter;
    private List<MCQListData> data_list = null;
    private Button btnSubmitInterview;
    private int trueCount = 0;
    public static ArrayList<Boolean> CORRECTANSWER=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mcq_list, container, false);
        ((Home) getActivity()).setActionBarTitle("MMC Membership Interview");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btnSubmitInterview = view.findViewById(R.id.btnSubmitInterview);
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MCQListAdapter(getContext(), data_list, MCQList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        btnSubmitInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean correct;
                for (int i = 0; i < CORRECTANSWER.size(); i++) {
                    correct = CORRECTANSWER.get(i);
                    if (correct) {
                        trueCount++;
                    }

                }
                if (data_list.size() == trueCount) {
                    //This condition will executes when all answers are correct
                    Toast.makeText(getContext(), "All answers are correct, ready to proceed next step", Toast.LENGTH_SHORT).show();
                    UpdateStatus updateStatus = new UpdateStatus(getContext(), "5");
                    General_SharedPref.getInstance(getContext()).saveJoiningStatus("5");
                    correctAnswerDialog();
                } else {
                    int wrongCount = CORRECTANSWER.size() - trueCount;
                    wrongAnswerDialog(wrongCount);
                }
                trueCount = 0;

            }
        });

        return view;
    }
    private void wrongAnswerDialog(int wrongCount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Your "+wrongCount+" Answer(s) is Wrong");
        builder.setMessage("Please contact to Your Sponsor to confirm answers and then Try again");
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
        String fname = Profile_SharedPref.getInstance(getContext()).getFName();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set a title for alert dialog
        builder.setTitle("Congratulations "+fname);
        // Show a message on alert dialog
        builder.setMessage("You are selected for MMC Membership and Service");
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

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.commit();
                    manager.popBackStack();
                    getContext().sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));

                } catch (Exception e) {

                }
                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
    }

    private void load_data_from_server() {
        data_list.clear();
        CORRECTANSWER.clear();
        ConstantMethods.loaderDialog(getContext());

        final String userid = Profile_SharedPref.getInstance(getContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", userid)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_ALL_MCQ)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MCQListData data = new MCQListData(object.getString("question_id"),
                                object.getString("question"),
                                object.getString("option_a"),
                                object.getString("option_b"),
                                object.getString("correct_answer"));
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
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();
                if (data_list.size() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please try again later..!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                } else {

                }
            }
        };

        task.execute();
    }

    @Override
    public void onDetach() {
        ((Home) getActivity()).setActionBarTitle("Home");
        super.onDetach();
    }
}