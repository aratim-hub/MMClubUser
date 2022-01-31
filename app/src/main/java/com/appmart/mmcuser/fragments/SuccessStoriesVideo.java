package com.appmart.mmcuser.fragments;

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
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.adapters.SuccessStoriesAdapter;
import com.appmart.mmcuser.models.SuccessStoriesData;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_SUCCESS_STORIES_VIDEO;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SuccessStoriesVideo extends Fragment {

    private RecyclerView recyclerView;
    private SuccessStoriesAdapter adapter;
    private List<SuccessStoriesData> data_list = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_video_list, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.success_stories));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SuccessStoriesAdapter(getContext(), data_list);
        recyclerView.setAdapter(adapter);

        load_data_from_server();
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_ALL_SUCCESS_STORIES_VIDEO)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        SuccessStoriesData data = new SuccessStoriesData(object.getString("success_story_id"),
                                object.getString("success_story_title"),
                                object.getString("success_story_desc"),
                                object.getString("success_story_url"),
                                object.getString("success_story_url_id"),
                                object.getString("created_at")
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
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();

            }
        };

        task.execute();

    }

}