package com.appmart.mmcuser.fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.appmart.mmcuser.adapters.MyTeamListAdapter;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.models.MyTeamListData;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

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
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_DOWNLINK;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyTeamList extends Fragment {

    private RecyclerView recyclerView;
    private MyTeamListAdapter adapter;
    private List<MyTeamListData> data_list = null;
    private Fragment menu_Frag;

    private TextView txtTeamSizeCount;
    private EditText edtSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downlink_list, container, false);
        ((Home) getActivity()).setActionBarTitle("My Team");
        setHasOptionsMenu(true);

        txtTeamSizeCount =  view.findViewById(R.id.txtTeamSizeCount);
        edtSearch =  view.findViewById(R.id.edtSearch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyTeamListAdapter(getContext(), data_list,MyTeamList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                adapter.getFilter().filter(s);


            }
        });        return view;
    }

    private void load_data_from_server() {
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
                        .url(GET_MY_DOWNLINK)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                            @SuppressLint("StaticFieldLeak") MyTeamListData data = new MyTeamListData(object.getString( "id"),
                                    object.getString("firstname"),
                                    object.getString("lastname"),
                                    object.getString("email"),
                                    object.getString("mobile_no"),
                                    object.getString("whatsapp_no"),
                                    object.getString("city_name"),
                                    object.getString("pincode"),
                                    object.getString("status"),
                                    object.getString("created_at"),
                                    object.getString("status_per"));
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
                txtTeamSizeCount.setText("Total Team Size: "+data_list.size()+" Members");
            }
        };

        task.execute();
    }

    @Override
    public void onDetach() {
        ((Home) getActivity()).setActionBarTitle("Home");
        super.onDetach();
    }

    public void loadDetails(Bundle bundle) {
        menu_Frag = new TeamApprovalList();
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