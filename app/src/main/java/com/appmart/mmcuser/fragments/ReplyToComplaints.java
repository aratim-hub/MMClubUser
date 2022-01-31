package com.appmart.mmcuser.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.adapters.ComplaintsReplyAdapter;
import com.appmart.mmcuser.models.ComplaintsReplyModel;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.appmart.mmcuser.utils.ServerAddress.ADD_NEW_REPLY_TO_TICKET;
import static com.appmart.mmcuser.utils.ServerAddress.GET_REPLY_OF_TICKETS;

public class ReplyToComplaints  extends Fragment {
    TextView txtTicketId, txtSubject, txtMessage, txtReply;
    String support_ticket_id,support_subject, support_messege;
    Fragment fragment;
    List<ComplaintsReplyModel> replyModelList;
    RecyclerView recyclerView;
    ComplaintsReplyAdapter adapter;
    EditText edtReply;
    Button btnReply;
    boolean success;
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reply_to_complaints, container, false);

        txtTicketId = view.findViewById(R.id.txtTicketId);
        txtSubject = view.findViewById(R.id.txtSubject);
        txtMessage = view.findViewById(R.id.txtMessage);

        edtReply = view.findViewById(R.id.edtReply);

        recyclerView = view.findViewById(R.id.recyclerView);

        btnReply = view.findViewById(R.id.btnReply);


        support_ticket_id = getArguments().getString("support_ticket_id");
        support_subject = getArguments().getString("support_subject");
        support_messege = getArguments().getString("support_messege");

        progress = new ProgressDialog(getContext());
        progress.setTitle("Please Wait");
        progress.setMessage("Getting All Reply...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);

        txtTicketId.setText(support_ticket_id);
        txtSubject.setText(support_subject);
        txtMessage.setText(support_messege);

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewReply();

            }
        });

        recyclerView.setHasFixedSize(true);
        replyModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAllReply();
        return view;

    }


    private void loadAllReply() {
        progress.show();

        replyModelList.clear();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();


                RequestBody requestBody = new FormBody.Builder()
                        .add("support_ticket_id", support_ticket_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_REPLY_OF_TICKETS)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ComplaintsReplyModel data = new ComplaintsReplyModel(
                                object.getString("reply_id"),
                                object.getString("support_id"),
                                object.getString("admin_id"),
                                object.getString("customer_id"),
                                object.getString("reply_msg"),
                                object.getString("time"));

                        replyModelList.add(data);

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
                adapter = new ComplaintsReplyAdapter(getContext(), replyModelList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(replyModelList.size());
                progress.hide();
            }
        };

        task.execute();
    }


    private void addNewReply() {

       AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();


                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", Profile_SharedPref.getInstance(getContext()).getUserId())
                        .add("support_ticket_id", support_ticket_id)
                        .add("reply_message", edtReply.getText().toString().trim())
                        .build();

                Request request = new Request.Builder()
                        .url(ADD_NEW_REPLY_TO_TICKET)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());

                    success = object.getBoolean("success");

                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    edtReply.getText().clear();
                    loadAllReply();
                } else {
                    Toast.makeText(getContext(), "Not Placed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }


}
