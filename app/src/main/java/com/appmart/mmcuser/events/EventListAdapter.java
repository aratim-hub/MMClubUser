package com.appmart.mmcuser.events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.GET_EVENT_ATTENDANCE;
import static com.appmart.mmcuser.utils.ServerAddress.GET_EVENT_LIST;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    EventList eventList;
    private Context context;
    private List<EventListData> my_data;
    private Handler handler = new Handler();

    public EventListAdapter(Context context, List<EventListData> dataReceived, EventList eventList) {
        this.context = context;
        this.my_data = dataReceived;
        this.eventList = eventList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


//        holder.txtEventDescription.setText(Html.fromHtml(my_data.get(position).getEvent_description()));
        holder.txtEventDescription.setText(my_data.get(position).getEvent_description());
        holder.txtDate.setText(my_data.get(position).getEvent_date());
        holder.txtTime.setText(my_data.get(position).getEvent_time());
        holder.txtJoiningLink.setText(my_data.get(position).getEvent_link());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)

                .load("https://manageadmin.mmclub.in/" + my_data.get(position).getEvent_img_url())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.eventLoadProgress.setVisibility(View.INVISIBLE);
                        holder.imgEventBanner.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventDescription,txtDate,txtTime, txtJoiningLink;
        public ImageView imgEventBanner;
        public ProgressBar eventLoadProgress;


        public ViewHolder(final View itemView) {
            super(itemView);
            txtEventDescription = itemView.findViewById(R.id.txtEventDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtEventDescription = itemView.findViewById(R.id.txtEventDescription);
            txtJoiningLink = itemView.findViewById(R.id.txtJoiningLink);
            imgEventBanner = itemView.findViewById(R.id.imgEventBanner);
            eventLoadProgress = itemView.findViewById(R.id.eventLoadProgress);
        }
    }
}
