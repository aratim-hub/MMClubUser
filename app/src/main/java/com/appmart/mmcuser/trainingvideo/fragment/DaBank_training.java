package com.appmart.mmcuser.trainingvideo.fragment;

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

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.trainingvideo.adapter.TrainingVideoListAdapter;
import com.appmart.mmcuser.trainingvideo.model.AtomyTrainingVideoListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_TRAINING_VIDEO_BY_CATEGORY;

public class DaBank_training extends Fragment {
    RecyclerView recycler_view_atomy;
    private TrainingVideoListAdapter adapter;
    private List<AtomyTrainingVideoListData> data_list = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_training_video, container, false);
        recycler_view_atomy = (RecyclerView) view.findViewById(R.id.recycler_view_atomy);
        data_list = new ArrayList<>();

        recycler_view_atomy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TrainingVideoListAdapter(getContext(), data_list);
        recycler_view_atomy.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_TRAINING_VIDEO_BY_CATEGORY + "?company=DaBank")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        AtomyTrainingVideoListData data = new AtomyTrainingVideoListData(object.getString("video_id"),
                                object.getString("video_title"),
                                object.getString("video_desc"),
                                object.getString("video_url"),
                                object.getString("video_url_id"),
                                object.getString("video_category"),
                                object.getString("video_questions"), object.getString("video_seen_status"));
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
