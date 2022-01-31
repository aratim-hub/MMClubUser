package com.appmart.mmcuser.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.MyPaymentProofListAdapter;
import com.appmart.mmcuser.models.MyPaymentProofListData;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_REFERAL_LINKS;


public class MyPaymentProofList extends Fragment {

    private RecyclerView recyclerView;
    private MyPaymentProofListAdapter adapter;
    private List<MyPaymentProofListData> data_list = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_payment_proof_list, container, false);
        ((Home) getActivity()).setActionBarTitle("My Referal Links Status");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyPaymentProofListAdapter(getContext(), data_list, MyPaymentProofList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());
        final String User_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("host_id", User_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(GET_REFERAL_LINKS)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        MyPaymentProofListData data = new MyPaymentProofListData(object.getString("link_id"),
                                object.getString("company"),
                                object.getString("ref_link"),
                                object.getString("payment_proof"),
                                object.getString("payment_proof_date"),
                                object.getString("isApproved"),
                                object.getString("isApprovedByAdmin"),
                                object.getString("is_training_completed"),
                                object.getString("reject_reason"));
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
                    builder.setMessage("Sorry, unable to load data..!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                }
            }
        };

        task.execute();

    }

    @Override
    public void onDetach() {
        ((Home) getActivity()).setActionBarTitle(getString(R.string.app_name));
        super.onDetach();
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
}