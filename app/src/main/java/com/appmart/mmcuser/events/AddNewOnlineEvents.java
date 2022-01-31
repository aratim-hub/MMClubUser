package com.appmart.mmcuser.events;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.services.GetUserIdAndNames.USERID_NAMES;
import static com.appmart.mmcuser.utils.ServerAddress.ADD_NEW_ONLINE_EVENT;

public class AddNewOnlineEvents extends AppCompatActivity {
    private TabLayout tabLayout;
    TextView actionBarTitle;
    EditText edtEventDate, edtEventTime, edtSubject, edtControlling, edtMonitor, edtMonitorId, edtHostId, edtHost, edtZoomLink;
    Button btnAddEvent;
    String eventFor, messege, current_datee;
    boolean success;
    ImageView ImgSelectTime, ImgSelectDate;
    int mYear, mMonth, mDay;
    public int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addonline_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Add New Event");
        Window window = AddNewOnlineEvents.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(AddNewOnlineEvents.this, R.color.colorPrimaryDark));
        }

        edtEventDate = findViewById(R.id.edtEventDate);
        edtEventTime = findViewById(R.id.edtEventTime);
        ImgSelectDate = findViewById(R.id.ImgSelectDate);
        ImgSelectTime = findViewById(R.id.ImgSelectTime);
        edtSubject = findViewById(R.id.edtSubject);
        edtHost = findViewById(R.id.edtHost);
        edtHostId = findViewById(R.id.edtHostId);
        edtControlling = findViewById(R.id.edtControlling);
        edtMonitor = findViewById(R.id.edtMonitor);
        edtMonitorId = findViewById(R.id.edtMonitorId);
        edtZoomLink = findViewById(R.id.edtZoomLink);
        btnAddEvent = findViewById(R.id.btnAddEvent);

        final String user_name = Profile_SharedPref.getInstance(AddNewOnlineEvents.this).getFName() + " " + Profile_SharedPref.getInstance(AddNewOnlineEvents.this).getLName();
        edtControlling.setText(user_name);

        eventFor = getIntent().getStringExtra("eventFor");


        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        final Date date = new Date();
//
//        current_datee = sdf.format(date);
//        edtEventDate.setText(current_datee);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()) {
                    addEventToDatabase();
                }

            }
        });

        ImgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewOnlineEvents.this,
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
                                edtEventDate.setText(current_datee);
                                edtEventTime.setText("");
                            }
                        }, mYear, mMonth, mDay);

                final Calendar cl = Calendar.getInstance();
                cl.add(Calendar.DAY_OF_YEAR, 1);

//                datePickerDialog.getDatePicker().setMinDate(date.getTime());
                datePickerDialog.getDatePicker().setMinDate(cl.getTimeInMillis());

                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        ImgSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timerselection();

            }
        });
        edtHostId.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                // yourEditText...
                String name = USERID_NAMES.get(edtHostId.getText().toString());
                edtHost.setText(name);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        edtMonitorId.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                // yourEditText...
                String name = USERID_NAMES.get(edtMonitorId.getText().toString());
                edtMonitor.setText(name);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    private void Timerselection() {

        if (current_datee == null) {
            ConstantMethods.showAlertMessege(AddNewOnlineEvents.this, "Warning", "Please select date first");
            return;
        }

        TimePickerDialog mTimePicker = new TimePickerDialog(AddNewOnlineEvents.this,
                new TimePickerDialog.OnTimeSetListener() {
                    public String strhourOfDay, str_min;
                    String format;

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
                            ConstantMethods.showAlertMessege(AddNewOnlineEvents.this, "Warning", "Please select Correct Time");
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

                        String time = strhourOfDay + ":" + str_min + ":" + "00";
                        edtEventTime.setText(time + " " + format);


                    }
                }, mHour, mMinute, false);

//        mTimePicker.setTitle("Select Time");
//        SimpleDateFormat hour = new SimpleDateFormat("HH");
//        int hrs = Integer.parseInt(hour.format(new Date()));
//        SimpleDateFormat min = new SimpleDateFormat("mm");
//        int mins = 2 + Integer.parseInt(min.format(new Date()));
//
//        mTimePicker.updateTime(hrs, mins);
        mTimePicker.show();
        mTimePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        mTimePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);


    }

    private void addEventToDatabase() {

        ConstantMethods.loaderDialog(AddNewOnlineEvents.this);

        final String u_id = Profile_SharedPref.getInstance(AddNewOnlineEvents.this).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", u_id)
                        .add("controlling", "GSPR  " + edtControlling.getText().toString())
                        .add("monitor_id", edtMonitorId.getText().toString())
                        .add("monitor", edtMonitor.getText().toString())
                        .add("host", edtHost.getText().toString())
                        .add("host_id", edtHostId.getText().toString())
                        .add("eventDate", edtEventDate.getText().toString())
                        .add("eventTime", edtEventTime.getText().toString())
                        .add("title", edtSubject.getText().toString())
                        .add("zoomlink", edtZoomLink.getText().toString())
                        .add("eventFor", eventFor)
                        .build();

                Request request = new Request.Builder()
                        .url(ADD_NEW_ONLINE_EVENT)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    messege = object.getString("messege");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(AddNewOnlineEvents.this);
                if (success) {
                    edtHost.setText("");
                    edtMonitor.setText("");
                    edtZoomLink.setText("");
                    edtEventTime.setText("");
                    ConstantMethods.showAlertMessege(AddNewOnlineEvents.this, "Done", messege);
                } else {
                    ConstantMethods.showAlertMessege(AddNewOnlineEvents.this, "Alert", messege);
                }
            }
        };

        task.execute();

    }


    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtEventTime)) ret = false;
        if (!Validation.hasText(edtEventDate)) ret = false;
        if (!Validation.hasText(edtSubject)) ret = false;
        if (!Validation.hasText(edtHost)) ret = false;
        if (!Validation.hasText(edtControlling)) ret = false;
        if (!Validation.hasText(edtMonitor)) ret = false;
        if (!Validation.hasText(edtZoomLink)) ret = false;

        return ret;
    }

}