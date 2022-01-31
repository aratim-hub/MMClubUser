package com.appmart.mmcuser.great_sponsor_training;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sponsor_training.adapter.SponsorTrainingVideoListAdapter;
import com.appmart.mmcuser.sponsor_training.model.SponsorTrainingVideoListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_GREATSPONSOR_TRAINING_VIDEO;
import static com.appmart.mmcuser.utils.ServerAddress.GET_SPONSOR_TRAINING_VIDEO;

public class GreatSponsorTrainingVideo extends Fragment {

    RecyclerView recycler_view;
    private SponsorTrainingVideoListAdapter adapter;
    private List<SponsorTrainingVideoListData> data_list = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_business_guide_content, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SponsorTrainingVideoListAdapter(getContext(), data_list);
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
                        .url(GET_GREATSPONSOR_TRAINING_VIDEO)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        SponsorTrainingVideoListData data = new SponsorTrainingVideoListData(object.getString("gstv_id"),
                                object.getString("video_title"),
                                object.getString("video_url"),
                                object.getString("video_url_id")
                        );
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
