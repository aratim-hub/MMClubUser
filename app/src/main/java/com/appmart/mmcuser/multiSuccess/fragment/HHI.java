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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.UpdateReferenceLink;
import com.appmart.mmcuser.multiSuccess.adapters.OkLifeCareListAdapter;
import com.appmart.mmcuser.multiSuccess.model.OkLifeCareListData;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.My_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEX_MONEY_MULTISUCCESS;
import static com.appmart.mmcuser.utils.ServerAddress.REQUEST_HOST_LINK;

public class HHI extends Fragment {
    RecyclerView recycler_view_atomy;
    Button btnJoinOkLifeCare;
    Button btnUploadPaymentReceipt;
    String messege, dateTime;
    TextView txtSponsorId;
    ImageView imgCopy;
    String sponsor_link;
    String mySponsorId;
    private OkLifeCareListAdapter adapter;
    private List<OkLifeCareListData> data_list = null;
    public static String NEW_SPONSOR_ID;
    String newSponsor_id;
    private LinearLayout buttonDesignLayout;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_hhi, container, false);
        recycler_view_atomy = (RecyclerView) view.findViewById(R.id.recycler_view_atomy);
        btnJoinOkLifeCare = view.findViewById(R.id.btnJoinOkLifeCare);
        btnUploadPaymentReceipt = view.findViewById(R.id.btnUploadPaymentReceipt);
        txtSponsorId = view.findViewById(R.id.txtSponsorId);
        imgCopy = view.findViewById(R.id.imgCopy);
        buttonDesignLayout = view.findViewById(R.id.buttonDesignLayout);

        data_list = new ArrayList<>();

        recycler_view_atomy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OkLifeCareListAdapter(getContext(), data_list);
        recycler_view_atomy.setAdapter(adapter);

        btnJoinOkLifeCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ConstantMethods.showAlertMessege(getContext(), "Coming Soon", "MMC Will inform you once it enabled");

                try {
                    sponsor_link = Host_Links_SharedPref.getInstance(getContext()).getOKLIFE_CARE_HOSTLINK();

                    if (sponsor_link.equals("") || sponsor_link.equals(null)) {
                        sendWarningToHost();
                        return;
                    }

                    String url = "https://happyhealthindia.co.in/userpanel/UserPreRegistration.aspx";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {

                }
            }
        });
        btnUploadPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Before proceed to next Make sure that NexMoney is Joined
//                String noofjoining = No_OfJoining_SharedPref.getInstance(getContext()).getNO_OF_NEXMONEY_JOININF();
//                if (Integer.parseInt(noofjoining) < 2) {
//                    ConstantMethods.showAlertMessege(getContext(), "Warning", "You should join atleast 2 members for Nexmoney to Proceed for OkLifeCare");
//                    return;
//                }
                Intent i = new Intent(getContext(), UpdateReferenceLink.class);
                i.putExtra("CompanyName", "HHI");
                startActivity(i);
//                ConstantMethods.showAlertMessege(getContext(), "Coming Soon", "MMC Will inform you once it enabled");

            }
        });
        sponsor_link = Host_Links_SharedPref.getInstance(getContext()).getHHI_HOSTLINK();
        if (sponsor_link.equals("") || sponsor_link.equals(null)) {
            txtSponsorId.setText("Sponsor is not Joined to this Company");
            imgCopy.setVisibility(View.INVISIBLE);
            btnUploadPaymentReceipt.setVisibility(View.INVISIBLE);

        } else {
            txtSponsorId.setText("Copy Your Sponsor Id: " + sponsor_link);
        }

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", sponsor_link);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        load_data_from_server();
        String status = General_SharedPref.getInstance(getContext()).getJoingStatus();
        if (Integer.parseInt(status) == 7) {
            buttonDesignLayout.setVisibility(View.VISIBLE);
        } else {
            buttonDesignLayout.setVisibility(View.INVISIBLE);

        }

// disable buttons if already joined and approved
        mySponsorId = My_Links_SharedPref.getInstance(getContext()).getHHI_MYLINK();
        if (mySponsorId.equals("") || mySponsorId.equals(null)) {

        } else {
            btnJoinOkLifeCare.setVisibility(View.INVISIBLE);
            btnUploadPaymentReceipt.setText("Already Joined");
            btnUploadPaymentReceipt.setEnabled(false);
            txtSponsorId.setVisibility(View.INVISIBLE);
            imgCopy.setVisibility(View.INVISIBLE);
            buttonDesignLayout.setVisibility(View.INVISIBLE);
        }


        return view;
    }


    private void load_data_from_server() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(GET_ALL_NEX_MONEY_MULTISUCCESS + "?company=HHI")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        OkLifeCareListData data = new OkLifeCareListData(object.getString("msuccess_id"),
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

    private void sendWarningToHost() {

        ConstantMethods.loaderDialog(getContext());
        final String host_id = Host_Details_SharedPref.getInstance(getContext()).getHOST_ID();
        final String host_mobile = Host_Details_SharedPref.getInstance(getContext()).getHOST_MOBILE_NUMBER();
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dateobj = new Date();
        final String CurrentDateTime = df.format(dateobj);
        System.out.println(df.format(dateobj));


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("request_to_id", host_id)
                        .add("request_to_mobile", host_mobile)
                        .add("request_by", user_id)
                        .add("company", "HHI")
                        .add("dateTime", CurrentDateTime)
                        .build();

                Request request = new Request.Builder()
                        .url(REQUEST_HOST_LINK)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
//                    success = object.getBoolean("success");
                    messege = object.getString("messege");
                    dateTime = object.getString("dateTime");

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
                ConstantMethods.showAlertMessege(getContext(), "Information:", messege);

            }
        };

        task.execute();

    }

}
