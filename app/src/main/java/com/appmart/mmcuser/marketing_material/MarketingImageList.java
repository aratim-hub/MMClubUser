package com.appmart.mmcuser.marketing_material;

import android.graphics.drawable.Drawable;
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
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_MARKETING_IMAGES;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingImageList extends Fragment {

    private RecyclerView recyclerView;
    private MarketingImageListAdapter adapter;
    private List<MarketingImageListData> data_list = null;
    private Fragment menu_Frag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maketing_images_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MarketingImageListAdapter(getContext(), data_list,MarketingImageList.this);
        recyclerView.setAdapter(adapter);

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        ConstantMethods.loaderDialog(getContext());
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(GET_ALL_MARKETING_IMAGES)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                            MarketingImageListData data = new MarketingImageListData(object.getString( "image_id"),
                                    object.getString("image_title"),
                                    object.getString("image"),
                                    object.getString("image_url"),
                                    object.getString("created_at"));
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
                    ConstantMethods.showAlertMessege(getContext(), "Retry", "Sorry, unable to Marketing material, Your internet connection may be slow..!");

                }

            }
        };

        task.execute();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}