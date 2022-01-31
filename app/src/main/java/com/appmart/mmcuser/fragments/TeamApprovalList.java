package com.appmart.mmcuser.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.TeamApprovalListAdapter;
import com.appmart.mmcuser.models.TeamApprovalListData;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.GetUserStatusDetails.USER_STATUS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_REFERAL_LINKS_BY_DOWNLINK_USER_ID;
import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_AS_TRAINING_COMPLETED;

public class TeamApprovalList extends Fragment {
    String downlink_user_id, name, email, mobile_number, wa_number, status;
    TextView txtName, txtEmail, txtMobile, txtWhatsApp, txtCurrentStatus;
    ProgressBar pb3;

    private RecyclerView recyclerView;
    private TeamApprovalListAdapter adapter;
    private List<TeamApprovalListData> data_list = null;
    private boolean success = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_approval_list, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.team_member_details));
        downlink_user_id = getArguments().getString("downlink_user_id");
        name = getArguments().getString("name");
        email = getArguments().getString("email");
        mobile_number = getArguments().getString("mobile_number");
        wa_number = getArguments().getString("wa_number");
        status = getArguments().getString("status");

        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtWhatsApp = view.findViewById(R.id.txtWhatsAppNo);
        txtCurrentStatus = view.findViewById(R.id.txtCurrentStatus);
        pb3 = view.findViewById(R.id.pb3);

        txtName.setText(name);
        txtEmail.setText(email);
        txtMobile.setText(mobile_number);
        txtWhatsApp.setText(wa_number);

        final String stat = status;
        int percentage = (100 * Integer.parseInt(status)) / 12;
        txtCurrentStatus.setText(percentage + "% Profile completed");

        pb3.setProgress(Integer.parseInt(status));
        pb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileProgressDetails();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TeamApprovalListAdapter(getContext(), data_list, TeamApprovalList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void showProfileProgressDetails() {

        Dialog dialogProfileProgress = new Dialog(getContext());
        dialogProfileProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProfileProgress.setContentView(R.layout.dialog_profile_progress_details);
        dialogProfileProgress.show();
        LinearLayout layout_profileProgress = dialogProfileProgress.findViewById(R.id.layout_profileProgress);

        final TextView[] myTextViews = new TextView[USER_STATUS.size()]; // create an empty array;

        for (int i = 0; i < USER_STATUS.size(); i++) {
            // create a new textview
            final TextView rowTextView = new TextView(getContext());

            // set some properties of rowTextView or something
            USER_STATUS.get(i + 1);
            int percentage = (100 * (i + 1)) / 12;


            rowTextView.setText(i + 1 + ". " + USER_STATUS.get(i + 1) + " (" + percentage + "%)");

            rowTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

            // add the textview to the linearlayout
            layout_profileProgress.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());
        data_list.clear();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("downlink_user_id", downlink_user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_REFERAL_LINKS_BY_DOWNLINK_USER_ID)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        TeamApprovalListData data = new TeamApprovalListData(object.getString("link_id"),
                                object.getString("user_id"),
                                object.getString("company"),
                                object.getString("ref_link"),
                                object.getString("ispaid"),
                                object.getString("payment_proof"),
                                object.getString("isApproved"),
                                object.getString("isApprovedByAdmin"),
                                object.getString("is_training_completed"));
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
            }
        };

        task.execute();
    }

    public void showPaymentProof(Bundle bundle) {

        Fragment menu_Frag = new ViewPaymentProof();
        menu_Frag.setArguments(bundle);
        if (menu_Frag != null) {
            FragmentManager FM = getFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.main_Container, menu_Frag)
                    .addToBackStack(String.valueOf(FM))
                    .commit();
        }


    }

    public void updateAsTrainingCompled(final String link_id, final String company) {

        ConstantMethods.loaderDialog(getContext());
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("link_id", link_id)
                        .add("downlink_user_id", downlink_user_id)
                        .add("downlink_user_name", name)
                        .add("company", company)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_AS_TRAINING_COMPLETED)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");

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
                if (success) {
                    Toast.makeText(getContext(), "Payment Receipt Approved Successfully", Toast.LENGTH_SHORT).show();
                    data_list.clear();
                    load_data_from_server();
                }
            }
        };

        task.execute();

    }
}
