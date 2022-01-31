package com.appmart.mmcuser.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_EVENT_ATTENDANCE;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SponsorTrainingEventListAdapter extends RecyclerView.Adapter<SponsorTrainingEventListAdapter.ViewHolder> {

    SponsorTrainingEventList eventList;
    private Context context;
    private List<SponsorTrainingEventListData> my_data;
    private Handler handler = new Handler();

    public SponsorTrainingEventListAdapter(Context context, List<SponsorTrainingEventListData> dataReceived, SponsorTrainingEventList eventList) {
        this.context = context;
        this.my_data = dataReceived;
        this.eventList = eventList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_event_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.txtEventDescription.setText(Html.fromHtml(my_data.get(position).getEvent_description()));
        holder.txtEventTitle.setText(my_data.get(position).getEvent_title());
        holder.txtControlling.setText(my_data.get(position).getController());
        holder.txtMonitor.setText(my_data.get(position).getMonitor());
        holder.txtHost.setText(my_data.get(position).getHost());
        holder.txtDate.setText(my_data.get(position).getEvent_date());
        holder.txtTime.setText(my_data.get(position).getEvent_time());
        holder.txtZoomLink.setText(my_data.get(position).getEvent_zoom_link());

        holder.txtZoomLink.setTextColor(context.getResources().getColor(R.color.colorAccent));
        holder.txtZoomLink.setPaintFlags(holder.txtZoomLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        String userid = Profile_SharedPref.getInstance(context).getUserId();

        if (userid.equals(my_data.get(position).getController_id())) {
            holder.btnCancelEvent.setVisibility(View.VISIBLE);
            holder.btnCancelEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventList.cancelEventDialog(my_data.get(position).getOnline_event_id());

                }
            });
        } else {
            holder.btnCancelEvent.setVisibility(View.GONE);

        }

        holder.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String event_id = my_data.get(position).getOnline_event_id();
                final String user_id = Profile_SharedPref.getInstance(context).getUserId();
//                Log.i("TAG", "onClick: "+event_id + " : "+user_id);
                AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... integers) {

                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(60, TimeUnit.SECONDS)
                                .writeTimeout(60, TimeUnit.SECONDS)
                                .readTimeout(60, TimeUnit.SECONDS)
                                .build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("event_id", event_id)
                                .add("user_id", user_id)
                                .build();

                        Request request = new Request.Builder()
                                .url(GET_EVENT_ATTENDANCE)
                                .post(requestBody)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            Log.i("TAG", "doInBackground: "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        ConstantMethods.hideLoaderDialog(context);
                    }
                };

                task.execute();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_data.get(position).getEvent_zoom_link()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventTitle, txtControlling,txtMonitor,txtHost, txtDate, txtTime, txtZoomLink, txtDescription;
        public Button btnCancelEvent;
        LinearLayout btnJoin;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtEventTitle = itemView.findViewById(R.id.txtEventTitle);
            txtControlling = itemView.findViewById(R.id.txtControlling);
            txtMonitor = itemView.findViewById(R.id.txtMonitor);
            txtHost = itemView.findViewById(R.id.txtHost);
            txtControlling = itemView.findViewById(R.id.txtControlling);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtZoomLink = itemView.findViewById(R.id.txtZoomLink);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            btnCancelEvent = itemView.findViewById(R.id.btnCancelEvent);
            btnJoin = itemView.findViewById(R.id.join_button);
        }
    }
}
