package com.appmart.mmcuser.start_business_guide.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.YoutubePlayVideoMultisuccess;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.start_business_guide.adapter.BusinessGuideContentListAdapter;
import com.appmart.mmcuser.start_business_guide.adapter.BusinessGuideVideoListAdapter;
import com.appmart.mmcuser.start_business_guide.model.BusinessGuideContentListData;
import com.appmart.mmcuser.start_business_guide.model.BusinessGuideVideoListData;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_BUSINESS_GUIDE_CONTENT;
import static com.appmart.mmcuser.utils.ServerAddress.GET_BUSINESS_GUIDE_VIDEO;

public class BusinessGuideContent extends Fragment {

    RecyclerView recycler_view;
    private BusinessGuideContentListAdapter adapter;
    private List<BusinessGuideContentListData> data_list = null;

    public int partId = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_business_guide_content, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BusinessGuideContentListAdapter(getContext(), data_list);
        recycler_view.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_BUSINESS_GUIDE_CONTENT)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        if (Integer.parseInt(object.getString("part_id"))  == partId ) {
                            BusinessGuideContentListData data = new BusinessGuideContentListData(object.getString("bgc_id"),
                                    object.getString("title"),
                                    object.getString("content"), object.getString("arrange_id"), object.getString("part_id")
                            );
                            data_list.add(data);
                        }
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
