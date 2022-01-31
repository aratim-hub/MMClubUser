package com.appmart.mmcuser.events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Splash;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.fragments.AffidaviteLetterCopy;
import com.appmart.mmcuser.sharedPreference.ChangeSponsor_SharedPref;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.Language_SharedPref;
import com.appmart.mmcuser.sharedPreference.MMC_Referal_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.My_Links_SharedPref;
import com.appmart.mmcuser.sharedPreference.PaymentGatewayDetails_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.sharedPreference.SharedPref;
import com.appmart.mmcuser.sharedPreference.SharedPrefForVersionCode;
import com.appmart.mmcuser.sharedPreference.SponsorEligibility_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.CANCEL_ONLINE_EVENT_BY_EVENT_ID;
import static com.appmart.mmcuser.utils.ServerAddress.EVENT_TITLE_LIST;
import static com.appmart.mmcuser.utils.ServerAddress.GET_ONLINE_EVENT_LIST;


/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SpTrainingEventList extends Fragment {

    private RecyclerView recyclerView;
    private TextView txtWarningText;
    private SpTrainingEventListAdapter adapter;
    private List<SpTrainingEventListData> data_list = null;
    private Fragment menu_Frag;
    Button btnAddNewEvent;
    private boolean success;

    String  current_datee,selectedFromTime,selectedToTime;
    int mYear, mMonth, mDay;
    public int mHour, mMinute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_online_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        txtWarningText = view.findViewById(R.id.txtWarningText);
        btnAddNewEvent = view.findViewById(R.id.btnAddNewEvent);
        data_list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SpTrainingEventListAdapter(getContext(), data_list, SpTrainingEventList.this);
        recyclerView.setAdapter(adapter);

        String status = General_SharedPref.getInstance(getContext()).getJoingStatus();
        if (Integer.parseInt(status) < 10) {
        } else {
            load_data_from_server();
//            getSubjectList();
            txtWarningText.setVisibility(View.GONE);

        }

        btnAddNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventRequest();
//                if (SponsorEligibility_SharedPref.getInstance(getContext()).getIS_GREAT_SPONSOR().equals("true")) {
//                    Intent i = new Intent(getContext(), AddNewOnlineEvents.class);
//                    i.putExtra("eventFor", "SP Training Revision");
//                    startActivity(i);
//                } else {
//                    ConstantMethods.showAlertMessege(getContext(),"Messege","You are not Eligible to Add new Event");
//                }
            }
        });
        return view;
    }

    private void addEventRequest() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_add_online_event_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText eventDate = dialog.findViewById(R.id.edtEventDate);
        final EditText eventFromTime = dialog.findViewById(R.id.edtEventFromTime);
        final EditText eventToTime = dialog.findViewById(R.id.edtEventToTime);
        final EditText eventSubject = dialog.findViewById(R.id.edtSubject);
        final EditText zoomLink = dialog.findViewById(R.id.edtZoomLink);

        final AppCompatRadioButton isHost = dialog.findViewById(R.id.is_host);
        final AppCompatRadioButton isMonitor = dialog.findViewById(R.id.is_monitor);
        final AppCompatRadioButton isControl = dialog.findViewById(R.id.is_control);

        Button btnAddEvent = dialog.findViewById(R.id.btnAddEvent);

        if (SharedPref.getString(getContext(),"isSponsor")!= null || SharedPref.getString(getContext(),"isGreatSponsor") != null){
            if (SharedPref.getString(getContext(),"isSponsor").equals("1") || SharedPref.getString(getContext(),"isGreatSponsor").equals("1")){
                isControl.setVisibility(View.VISIBLE);
            }else{
                isControl.setVisibility(View.GONE);
            }
        }

        final boolean[] selectedLanguage;
        final ArrayList<Integer> langList = new ArrayList<>();
        final String[] langArray = {"Daily Task",
                "17 Steps - नए गेस्ट ने जोईनिंग के बाद, किस तरह से अपना MMC Business शुरू करेंगे वह स्टेप्स! ",
                "HHI, One Code और GIG और  Royal Q  में अपने SP की  ID Green कैसे कराए -- प्रोडक्ट आर्डर कैसे करे ? - उन्हें भेजने वाले 2 मेसेज!",
                "अगर गेस्ट Highway से जुड़ कर आते है, तो MMC App में कंपनी जोइनिंग डॉक्यूमेंट कैसे अपलोड करना है ?",
                "SP Royal Q Join हो कर MMC Membership लेते है, तो Sponsor द्वारा SP को 20 डॉलर या Rs.1500/ का रिटर्न Gift देने का नियम!",
                "MMC द्वारा 2nd Performance  Gift गिफ्ट पाने की स्टेप्स!"};

        selectedLanguage = new boolean[langArray.length];

        eventSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Subject");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            if ((i) < langList.size()){
                                langList.remove(i);
                            }

                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (langList.size() > 4){
                            Toast.makeText(getContext(), "Select Only Four Subject", Toast.LENGTH_SHORT).show();
                        }else if (langList.size() != 4){
                            Toast.makeText(getContext(), "Select At Least Four Subject", Toast.LENGTH_SHORT).show();
                        }else{
                            // Initialize string builder
                            StringBuilder stringBuilder = new StringBuilder();
                            // use for loop
                            for (int j = 0; j < langList.size(); j++) {
                                // concat array value
                                if (j==0){
                                    stringBuilder.append(String.valueOf(j+1)+". "+langArray[langList.get(j)]);
                                }else{
                                    stringBuilder.append("\n"+String.valueOf(j+1)+". "+langArray[langList.get(j)]);
                                }

                            }
                            // set text on textView
                            eventSubject.setText(stringBuilder.toString());
                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            eventSubject.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String tempMonthofYear = String.valueOf(monthOfYear + 1);
                                String tempDayOfMonth = String.valueOf(dayOfMonth);
                                if (Integer.parseInt(tempMonthofYear) < 10) {
                                    tempMonthofYear = "0" + String.valueOf(tempMonthofYear);
                                }
                                if (Integer.parseInt(tempDayOfMonth) < 10) {
                                    tempDayOfMonth = "0" + String.valueOf(tempDayOfMonth);
                                }
                                current_datee = tempDayOfMonth + "-" + tempMonthofYear + "-" + year;
                                eventDate.setText(current_datee);
                                eventFromTime.setText("");
                                eventToTime.setText("");
                            }
                        }, mYear, mMonth, mDay);

                final Calendar cl = Calendar.getInstance();
                cl.add(Calendar.DAY_OF_YEAR, 1);

                datePickerDialog.getDatePicker().setMinDate(cl.getTimeInMillis());

                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });

        eventFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_datee == null) {
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select date first");
                    return;
                }

                TimePickerDialog mTimePicker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            public String strhourOfDay, str_min;
                            String format;

                            public String to_strhourOfDay;
                            String to_format;

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                Date CurrrentDateTime = new Date();

                                //  Toast.makeText(Home.this, ""+dateFormat.format(CurrrentDateTime), Toast.LENGTH_SHORT).show();
                                //  Toast.makeText(Home.this, "hours: "+hourOfDay+"\nMinute: "+minute, Toast.LENGTH_SHORT).show();

                                Date dateSelectedByUser = null;
                                String sDate1 = current_datee + " " + hourOfDay + ":" + minute;
                                try {
                                    dateSelectedByUser = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(sDate1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (CurrrentDateTime.after(dateSelectedByUser)) {
                                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select Correct Time");
                                    return;
                                }

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                if (hourOfDay <= 9) {
                                    strhourOfDay = "0" + String.valueOf(hourOfDay);
                                } else {
                                    strhourOfDay = String.valueOf(hourOfDay);
                                }
                                if (minute <= 9) {
                                    str_min = "0" + String.valueOf(minute);
                                } else {
                                    str_min = String.valueOf(minute);
                                }

                                int to_hourOfDay = hourOfDay + 4;

                                if (to_hourOfDay == 0) {
                                    to_hourOfDay += 12;
                                    to_format = "AM";
                                } else if (to_hourOfDay == 12) {
                                    to_format = "PM";
                                } else if (to_hourOfDay > 12) {
                                    to_hourOfDay -= 12;
                                    to_format = "PM";
                                } else {
                                    to_format = "AM";
                                }
                                if (to_hourOfDay <= 9) {
                                    to_strhourOfDay = "0" + String.valueOf(to_hourOfDay);
                                } else {
                                    to_strhourOfDay = String.valueOf(to_hourOfDay);
                                }

                                String fromTime = strhourOfDay + ":" + str_min + ":" + "00";
                                String toTime = to_strhourOfDay + ":" + str_min + ":" + "00";
                                eventFromTime.setText(fromTime + " " + format);
                                eventToTime.setText(toTime + " " + to_format);

                                selectedFromTime =  fromTime + " " + format;
                                selectedToTime = toTime + " " + to_format;
                            }
                        }, mHour, mMinute, false);
                mTimePicker.show();
                mTimePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                mTimePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classRoom = "SP Training Revision";
                String userId = Profile_SharedPref.getInstance(getContext()).getUserId();
                String hosting = "0", monitoring = "0", controlling = "0";

                if (isHost.isChecked()){ hosting = "1"; }
                if (isMonitor.isChecked()){ monitoring = "1"; }
                if (isControl.isChecked()){ controlling = "1"; }

                if (current_datee == null){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select date");
                }else if (selectedFromTime == null){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select time");
                }else if (selectedToTime == null){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select time");
                }else if (langList.size() == 0){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select subject");
                }else if(langList.size() > 4){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select only four subject");
                }else if(langList.size() != 4){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please select atleast four subject");
                }else if (zoomLink.getText().toString().isEmpty()){
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Please enter zoom Link");
                }else{
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "all ok");
                }
            }
        });

        dialog.show();
    }

    private void load_data_from_server() {

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
                        .add("eventFor", "SP Training Revision")
                        .build();

                Request request = new Request.Builder()
                        .url(GET_ONLINE_EVENT_LIST)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        SpTrainingEventListData data = new SpTrainingEventListData(
                                object.getString("online_event_id"),
                                object.getString("controller"),
                                object.getString("controller_id"),
                                object.getString("monitor"),
                                object.getString("monitor_id"),
                                object.getString("host"),
                                object.getString("host_id"),
                                object.getString("event_title"),
                                object.getString("event_date"),
                                object.getString("event_time"),
                                object.getString("event_zoom_link"),
                                object.getString("event_status"),
                                object.getString("event_for"));
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

//    private void getSubjectList() {
//
//        Log.i("TAG", "getSubjectList: doInBackgroundxyz");
//        final String userid = Profile_SharedPref.getInstance(getContext()).getUserId();
//        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
//            @Override
//            protected Void doInBackground(Integer... integers) {
//
//                OkHttpClient client = new OkHttpClient.Builder()
//                        .connectTimeout(60, TimeUnit.SECONDS)
//                        .writeTimeout(60, TimeUnit.SECONDS)
//                        .readTimeout(60, TimeUnit.SECONDS)
//                        .build();
////                RequestBody requestBody = new FormBody.Builder()
////                        .add("eventFor", "SP Training Revision")
////                        .build();
//
//                Request request = new Request.Builder()
//                        .url(EVENT_TITLE_LIST)
////                        .post(requestBody)
//                        .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    Log.i("TAG", "doInBackgroundxyz: "+response.body().string());
//
////                    JSONArray array = new JSONArray(response.body().string());
////
////                    for (int i = 0; i < array.length(); i++) {
////
////                        JSONObject object = array.getJSONObject(i);
////
////                        Log.i("TAG", "doInBackgroundxyz: "+object.toString());
////
//////                        SpTrainingEventListData data = new SpTrainingEventListData(
//////                                object.getString("online_event_id"),
//////                                object.getString("controller"),
//////                                object.getString("controller_id"),
//////                                object.getString("monitor"),
//////                                object.getString("monitor_id"),
//////                                object.getString("host"),
//////                                object.getString("host_id"),
//////                                object.getString("event_title"),
//////                                object.getString("event_date"),
//////                                object.getString("event_time"),
//////                                object.getString("event_zoom_link"),
//////                                object.getString("event_status"),
//////                                object.getString("event_for"));
//////                        data_list.add(data);
////                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
////                } catch (JSONException e) {
////                    System.out.println("End of content");
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                adapter.notifyDataSetChanged();
//
//            }
//        };
//
//        task.execute();
//    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void cancelEventDialog(final String online_event_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set a title for alert dialog
        builder.setTitle("Confirm Cancel?");
        // Show a message on alert dialog
        builder.setMessage("Are you sure you want to Cancel Event?");
        // Set the positive button
        builder.setPositiveButton("Yes", null);
        // Set the negative button
        builder.setNegativeButton("No", null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEventRequest(online_event_id);
dialog.dismiss();

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void cancelEventRequest(final String online_event_id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("online_event_id", online_event_id)
                        .build();

                Request request = new Request.Builder()
                        .url(CANCEL_ONLINE_EVENT_BY_EVENT_ID)
                        .post(requestBody)
                        .build();
                try {

                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    ConstantMethods.showAlertMessege(getContext(),"Done","Event Cancelled Successfully");
                    Toast.makeText(getContext(), "Event Cancelled Successfully", Toast.LENGTH_SHORT).show();
                    data_list.clear();
                    load_data_from_server();
                } else {
                    ConstantMethods.showAlertMessege(getContext(),"Failed to cancel","Event Cancellation failed, Please Try Again Later");

                }

            }
        };

        task.execute();
    }
}