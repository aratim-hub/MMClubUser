package com.appmart.mmcuser.multiSuccess.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.appmart.mmcuser.multiSuccess.adapters.RenatusNovaListAdapter;
import com.appmart.mmcuser.multiSuccess.model.RenatusNovaListData;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
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

public class RenatusNova extends Fragment {
    RecyclerView recycler_view_dayjoy;
    private RenatusNovaListAdapter adapter;
    private List<RenatusNovaListData> data_list = null;
    Button btnJoinRenatusNova;
    LinearLayout buttonDesignLayout;
    Button btnUploadPaymentReceipt;
    ImageView imgCopyBankDetails;
    String sponsor_link;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_renatus_nova, container, false);
        recycler_view_dayjoy = (RecyclerView) view.findViewById(R.id.recycler_view_dayjoy);
        buttonDesignLayout = view.findViewById(R.id.buttonDesignLayout);
        btnJoinRenatusNova = view.findViewById(R.id.btnJoinRenatusNova);
        btnUploadPaymentReceipt = view.findViewById(R.id.btnUploadPaymentReceipt);
        imgCopyBankDetails = view.findViewById(R.id.imgCopyBankDetails);


        data_list = new ArrayList<>();

        recycler_view_dayjoy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RenatusNovaListAdapter(getContext(), data_list);
        recycler_view_dayjoy.setAdapter(adapter);

        load_data_from_server();


        //****************************If mylink is available then hide button to upload new Proofs************************
        String mylink = My_Links_SharedPref.getInstance(getContext()).getRENATUSNOVA_MYLINK();
        buttonDesignLayout.setVisibility(View.INVISIBLE);

//        if (mylink.equals("") || mylink.equals(null)) {
//            buttonDesignLayout.setVisibility(View.VISIBLE);
//        } else {
//            buttonDesignLayout.setVisibility(View.INVISIBLE);
//
//        }
        //****************************************************************************
        String status = General_SharedPref.getInstance(getContext()).getJoingStatus();
        String isSponsor = SponsorEligibility_SharedPref.getInstance(getContext()).getIS_SPONSOR();
        String mylink_nexmoney = My_Links_SharedPref.getInstance(getContext()).getNEXMONEY_MY_LINK();

        if (Integer.parseInt(status) == 12 && mylink.equals("")) {
            buttonDesignLayout.setVisibility(View.VISIBLE);

        } else {
            buttonDesignLayout.setVisibility(View.INVISIBLE);

        }


        imgCopyBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bankDetails = "(1) Renatus Wellness Private Limited\n" +
                        "\uD83D\uDC49 Bank Name : HDFC Bank\n" +
                        "A/c No. 50200047313946\n" +
                        "Branch : Hosur Main Road, Bommanahalli, Bangalore\n" +
                        "Ifsc Code : HDFC0001048\n" +
                        "*******\n" +
                        "(2) COMPANY ACCOUNT\n" +
                        "RENATUS WELLNESS PRIVATE LIMITED\n" +
                        "\uD83D\uDC49 Indusind Bank \n" +
                        "A/c  201002672589\n" +
                        "IFSC Code :  INDB0000740\n" +
                        "Branch : A S Rao Nagar Hyderabad\n" +
                        "*******\n" +
                        "(3)  RENATUS WELLNESS PRIVATE LIMITED\n" +
                        "BANDHAN BANK - \n" +
                        "Account Number: 10190000882112\n" +
                        "IFSC: BDBL0001625";
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", bankDetails);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();

//                buttonDesignLayout.setVisibility(View.INVISIBLE);
            }
        });

        btnJoinRenatusNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "http://distributor.renatuswellness.net/register";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {

                }
            }
        });

        btnUploadPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateReferenceLink.class);
                i.putExtra("CompanyName", "RenatusNova");
                startActivity(i);
                ((MultiSuccess) getActivity()).CloseActivity();

            }
        });

        return view;
    }

    private void load_data_from_server() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_ALL_NEX_MONEY_MULTISUCCESS + "?company=RenatusNova")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        RenatusNovaListData data = new RenatusNovaListData(object.getString("msuccess_id"),
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
