package com.appmart.mmcuser.multiSuccess.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.MultiSuccess;
import com.appmart.mmcuser.activities.UpdateReferenceLink;
import com.appmart.mmcuser.multiSuccess.adapters.NexMoneyListAdapter;
import com.appmart.mmcuser.multiSuccess.model.NexMoneyListData;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.My_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.SponsorEligibility_SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEX_MONEY_MULTISUCCESS;

public class NexMoney extends Fragment {
    RecyclerView recycler_view_nex_money;
    private NexMoneyListAdapter adapter;
    private List<NexMoneyListData> data_list = null;
    private Button btnUploadPaymentReceipt;
    private Button btnJoinNexMoney;
    LinearLayout buttonDesignLayout;
    String sponsor_link;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_nexmoney, container, false);
        recycler_view_nex_money = (RecyclerView) view.findViewById(R.id.recycler_view_nex_money);
        btnUploadPaymentReceipt = (Button) view.findViewById(R.id.btnUploadPaymentReceipt);
        btnJoinNexMoney = (Button) view.findViewById(R.id.btnJoinNexMoney);
        buttonDesignLayout = view.findViewById(R.id.buttonDesignLayout);

        data_list = new ArrayList<>();

        recycler_view_nex_money.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NexMoneyListAdapter(getContext(), data_list);
        recycler_view_nex_money.setAdapter(adapter);

        btnUploadPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UpdateReferenceLink.class);
                i.putExtra("CompanyName", "NexMoney");
                startActivity(i);
                ((MultiSuccess) getActivity()).CloseActivity();

            }
        });

        btnJoinNexMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK().equals("") || Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK().equals(null)) {
//                    sendWarningToHost();
                    btnJoinNexMoney.setText("Sponsor link not found");

                    return;
                }

                try {
                    String url = Host_Links_SharedPref.getInstance(getContext()).getNEXMONEY_HOSTLINK();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Invalid link provided by your Sponsor", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //****************************If mylink is available then hide button to upload new Proofs************************
        String mylink = My_Links_SharedPref.getInstance(getContext()).getNEXMONEY_MY_LINK();
        buttonDesignLayout.setVisibility(View.INVISIBLE);

//        if (mylink.equals("") || mylink.equals(null)) {
//            buttonDesignLayout.setVisibility(View.VISIBLE);
//        }else{
//            buttonDesignLayout.setVisibility(View.INVISIBLE);
//
//        }
        String status = General_SharedPref.getInstance(getContext()).getJoingStatus();
        String isSponsor = SponsorEligibility_SharedPref.getInstance(getContext()).getIS_SPONSOR();

        if (Integer.parseInt(status) == 12 && mylink.equals("")) {
            buttonDesignLayout.setVisibility(View.VISIBLE);
        } else {
            buttonDesignLayout.setVisibility(View.INVISIBLE);

        }

        //****************************************************************************
        load_data_from_server();



        return view;
    }

    private void load_data_from_server() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(GET_ALL_NEX_MONEY_MULTISUCCESS + "?company=NexMoney")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        NexMoneyListData data = new NexMoneyListData(object.getString("msuccess_id"),
                                object.getString("msuccess_title"),
                                object.getString("msuccess_desc"),
                                object.getString("msuccess_image"),
                                object.getString("msuccess_image_url"),
                                object.getString("msuccess_category"),
                                object.getString("msuccess_video_url"),
                                object.getString("youtube_video_id"));
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
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();

    }
}
