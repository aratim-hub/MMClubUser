package com.appmart.mmcuser.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.ContextWrapper;
import com.appmart.mmcuser.GetMyReferalLinks;
import com.appmart.mmcuser.GetUserStatusDetails;
import com.appmart.mmcuser.GetVideoImages;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Splash;
import com.appmart.mmcuser.UpdateDeviceToken;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.adapters.AchievmentListAdapter;
import com.appmart.mmcuser.adapters.SliderAdapterExample;
import com.appmart.mmcuser.approval.ApprovalList;
import com.appmart.mmcuser.businessturnover.BusinessTurnover;
import com.appmart.mmcuser.fragments.AffidaviteLetter;
import com.appmart.mmcuser.fragments.AffidaviteLetterCopy;
import com.appmart.mmcuser.fragments.CompanyShops;
import com.appmart.mmcuser.fragments.FragLinkRequests;
import com.appmart.mmcuser.fragments.FragProfile;
import com.appmart.mmcuser.fragments.Frag_abou_us;
import com.appmart.mmcuser.fragments.MCQList;
import com.appmart.mmcuser.fragments.MyPaymentProofList;
import com.appmart.mmcuser.fragments.MyTeamList;
import com.appmart.mmcuser.fragments.News;
import com.appmart.mmcuser.fragments.SuccessStoriesVideo;
import com.appmart.mmcuser.fragments.SupportTicketList;
import com.appmart.mmcuser.fragments.TelegramGroupLinks;
import com.appmart.mmcuser.great_sponsor_training.GreatSponsorTraining;
import com.appmart.mmcuser.models.AchievmentListData;
import com.appmart.mmcuser.models.SliderImageListData;
import com.appmart.mmcuser.payment_gateway.PaymentRequest;
import com.appmart.mmcuser.services.ConstantDataService;
import com.appmart.mmcuser.services.GetCompanySequence;
import com.appmart.mmcuser.services.GetHostDetails;
import com.appmart.mmcuser.services.GetHostReferalLinks;
import com.appmart.mmcuser.services.GetLinkRequestDetails;
import com.appmart.mmcuser.services.GetMMCReferalLinks;
import com.appmart.mmcuser.services.GetPaymentDetails;
import com.appmart.mmcuser.services.GetStartBusinessVideoLink;
import com.appmart.mmcuser.services.GetUserIdAndNames;
import com.appmart.mmcuser.services.checkSponsorEligibility;
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
import com.appmart.mmcuser.sponsor_training.SponsorTraining;
import com.appmart.mmcuser.start_business_guide.StartBusinessGuide;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.appmart.mmcuser.utils.ShareUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import dmax.dialog.SpotsDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.CHECK_FOR_ADMIN_APPROAL;
import static com.appmart.mmcuser.utils.ServerAddress.CHECK_IF_PAYMENT_APPROVED_BY_SPONSOR;
import static com.appmart.mmcuser.utils.ServerAddress.CHECK_IF_TRAINING_COMPLETED;
import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_ACHIEVEMENT;
import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_NEWS;
import static com.appmart.mmcuser.utils.ServerAddress.GET_ALL_SLIDER_IMAGES;
import static com.appmart.mmcuser.utils.ServerAddress.GET_CHILD_TURNOVER_COUNT;
import static com.appmart.mmcuser.utils.ServerAddress.LOGIN_USER;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 17326;

    SliderView sliderView;
    SliderAdapterExample adapter;
    AchievmentListAdapter achievmentListAdapter;
    TextView actionbarUserName, actionBarTitle;
    BroadcastReceiver receiver;
    private List<SliderImageListData> data_list = null;
    private List<AchievmentListData> achievmentdata_list = null;
    private Fragment menu_Frag;
    private RecyclerView achievmentRecyclerView;
    private RelativeLayout txtOurAchievement;


    private TextView txtOurAchievement1;
    private String LATEST_NEWS_UPDATE;
    private TextView txtMarque;
    private String MSV_URL, MSP_URL;
    private SliderView imageSliderAchievment;
    private boolean success;
    private int pending_count;
    //***********************************************
//    private String DATE_FORMAT = "dd-MM-yyyy";
//    private Handler handler = new Handler();
//    private Runnable runnable;
//    Intent mServiceIntent;
    private String rejected_turnover = "", pending_turnover = "", id, f_name, l_name, email, mobile, whatsapp_no, p_code, city, sponsor_id , status = "", is_business_start, created_at, multisuccess_intro_video, multisuccess_path_video,isSponsor = "",isGreatSponsor = "",isSponserTraning = "",isGreatSponserTraning = "";
    private String wallet_balance;
    public static boolean CALL_ON_RESUME_HOME = false;

    NavigationView navigationView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Intent tnt = new Intent(getApplicationContext(),CompanyShopeeAddress.class);
//        tnt.putExtra("index","0");
//        startActivity(tnt);

        startActivity(new Intent(getApplicationContext(),EventActivity.class));

//        SharedPref.putString(Home.this,"great_sponser_test_index","0");
//        Intent tnt = new Intent(Home.this, GreatSponserTrainingTestActivity.class);
//        startActivity(tnt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtMarque = findViewById(R.id.txtMarquee);
        txtMarque.setSelected(true);
        txtMarque.setText(General_SharedPref.getInstance(Home.this).getLATEST_NEWS());


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionbarUserName = viewCustomActionBar.findViewById(R.id.actionbarUserName);

        actionbarUserName.setText("Welcome " + Profile_SharedPref.getInstance(Home.this).getFName());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(Home.this, R.color.colorBlack));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_bussinessturnover).setVisible(false);

        initiateImageSlider();
//        checkForJoiningSteps();

        checkConnectivity();

        new GetUserStatusDetails(Home.this);
        new GetVideoImages(Home.this);
        new GetMyReferalLinks(Home.this);

        //Starting Services to get related data from server

        Intent ii = new Intent(this, UpdateDeviceToken.class);
        startService(ii);

        Intent i = new Intent(Home.this, GetHostDetails.class);
        startService(i);
        startService(new Intent(Home.this, GetHostReferalLinks.class));
        startService(new Intent(Home.this, GetMMCReferalLinks.class));
        startService(new Intent(Home.this, GetStartBusinessVideoLink.class));
        startService(new Intent(Home.this, ConstantDataService.class));
        startService(new Intent(Home.this, GetPaymentDetails.class));
        startService(new Intent(Home.this, GetLinkRequestDetails.class));
        startService(new Intent(Home.this, checkSponsorEligibility.class));
        startService(new Intent(Home.this, GetUserIdAndNames.class));
        startService(new Intent(Home.this, GetCompanySequence.class));

        multiSuccessVideoThumbnail();

        checkForPendingReason();
//       checkForLinkRequest ();
//        String version_code = SharedPrefForVersionCode.getInstance(Home.this).getVersionCode();
        String version_code = "29";
        // Check for update available
        try {
            if (com.appmart.mmcuser.BuildConfig.VERSION_CODE != Integer.parseInt(version_code)) {
                ConstantMethods.showUpdateDialog(Home.this);
            }
        } catch (Exception d) {

        }

        initiateAchievment();

        LinearLayout layout_news_latest = findViewById(R.id.layout_news_latest);
        layout_news_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_Frag = new News();
                loadFragment();

            }
        });
        Button btnMultiSuccess = findViewById(R.id.btnMultiSuccess);
        btnMultiSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSponsor.equals("1") && isSponserTraning.equals("0")){
                    checkSponserTrainingTest();
                }else if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("0")){
                    checkGreateSponsorTrainingTest();
                }else if (isSponsor.equals("1") && isSponserTraning.equals("1")){
                    isSponsorTrainingDone();
                }else if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("1")){
                    isGreatSponsorTrainingDone();
                }else if (status.equals("11")) {
                    MemberShipTestPassDialog();
                } else if (status.equals("12")) {
                    Intent i = new Intent(Home.this, MultiSuccess.class);
                    startActivity(i);
                } else if (pending_turnover != null && pending_turnover.length() != 0 && rejected_turnover != null && rejected_turnover.length() != 0) {
                    if (pending_count > 0) {
                        PendingApprovalList();
                    } else if ((Integer.parseInt(pending_turnover) == 1)) {
                        if (isShowOk()) {
                            Intent i = new Intent(Home.this, MultiSuccess.class);
                            startActivity(i);
                        } else {
                            onPendingTurnOver();
                        }

                    } else if (Integer.parseInt(rejected_turnover) == 1) {
                        onRejectedTurnOver();
                    } else {
                        Intent i = new Intent(Home.this, MultiSuccess.class);
                        startActivity(i);
                    }
                }
            }
        });
        getLatestNews();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("my_channel_01", "Simplified Coding Notification", importance);
            mChannel.setDescription("dd");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
        getCurrentStatusOfUser();

        checkForLinkRequest();
        CheckForchangeSponsorRequest();

//        Toast.makeText(Home.this, "ref:"+ InstallReferals_SharedPref.getInstance(Home.this).getLINK_REQUEST(), Toast.LENGTH_SHORT).show();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // internet lost alert dialog method call from here...
                Toast.makeText(context, "br rec", Toast.LENGTH_SHORT).show();
                getCurrentStatusOfUser();

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("RELOAD_GET_STATUS_METHOD"));
        checkUpdate();
        load_child_turnover_count();
        String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
        Log.d("Home-status", "" + status);
        if (Integer.parseInt(status) == 13) {
            onDisableApp();
        }
    }


    private void getCurrentStatusOfUser() {
        android.app.AlertDialog progress = null;
        try {

            progress = new SpotsDialog.Builder()
                    .setContext(Home.this)
                    .setTheme(R.style.Custom)
                    .setMessage("Please wait while getting your current Status . . .")
                    .setCancelable(false)
                    .build();
            progress.show();
        } catch (Exception e) {

        }

        final String statusFromLocalDB = General_SharedPref.getInstance(Home.this).getJoingStatus();
        if (Integer.parseInt(statusFromLocalDB) >= 11) {
            progress.dismiss();
        }

        final String mobile_number = Profile_SharedPref.getInstance(getApplicationContext()).getMobile();

        final android.app.AlertDialog finalProgress = progress;
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("mobile", mobile_number)
                        .build();

                Request request = new Request.Builder()
                        .url(LOGIN_USER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    Log.i("TAG", "doInBackground: "+object);
                    success = object.getBoolean("success");
                    id = object.getString("id");
                    f_name = object.getString("f_name");
                    l_name = object.getString("l_name");
                    mobile = object.getString("mobile");
                    email = object.getString("email");
                    whatsapp_no = object.getString("whatsapp_no");
                    city = object.getString("city");
                    p_code = object.getString("p_code");
                    sponsor_id = object.getString("sponsor_id");
                    status = object.getString("status");
                    created_at = object.getString("created_at");
                    is_business_start = object.getString("is_business_start");
                    wallet_balance = object.getString("wallet_balance");
                    multisuccess_intro_video = object.getString("multisuccess_intro_video");
                    multisuccess_path_video = object.getString("multisuccess_path_video");
                    isSponsor = object.getString("isSponsor");
                    isGreatSponsor = object.getString("isGreatSponsor");
                    isSponserTraning = object.getString("is_sponser_traning");
                    isGreatSponserTraning = object.getString("is_great_sponser_traning");
                    if (object.getString("pending_turnover") != null) {
                        pending_turnover = object.getString("pending_turnover");
                    }
                    if (object.getString("rejected_turnover") != null) {
                        rejected_turnover = object.getString("rejected_turnover");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Menu nav_Menu = navigationView.getMenu();
                            if (status.equals("12")) {
                                nav_Menu.findItem(R.id.nav_bussinessturnover).setVisible(true);
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d("rejected_turnover", "" + rejected_turnover);
                try {
                    finalProgress.dismiss();
                    Log.d("home_pending_turnover", "" + pending_turnover);
                } catch (Exception e) {
                    Toast.makeText(Home.this, "Progress exception", Toast.LENGTH_SHORT).show();
                }

                if (success) {

                    SharedPref.putString(getApplicationContext(),"isSponsor",isSponsor);
                    SharedPref.putString(getApplicationContext(),"isGreatSponsor",isGreatSponsor);
                    Profile_SharedPref.getInstance(getApplicationContext()).saveProfile(id, f_name, l_name, mobile, email, whatsapp_no, city, p_code, sponsor_id, created_at, multisuccess_intro_video, is_business_start, wallet_balance, multisuccess_path_video);
                    General_SharedPref.getInstance(getApplicationContext()).saveJoiningStatus(status);
                    checkForJoiningSteps(status);
                    checkForPendingTurnOver();
                    checkForRejectedTurnOver();
                    checkSponserTrainingTest();
                    checkGreateSponsorTrainingTest();
                } else {
                }
            }
        };

        task.execute();

    }

    private void checkSponserTrainingTest() {
        if (isSponsor.equals("1") && isSponserTraning.equals("0")){
             AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("Pending Sponsor Training and Testing");
            // Show a message on alert dialog
            builder.setMessage("आप का अभिनंदन है, आप अभी Sponsor बन गए है!\n" +
                    "\uD83D\uDC90\uD83D\uDC90\uD83D\uDC51\uD83C\uDFAF\uD83D\uDCC8\uD83D\uDC90\uD83D\uDC90\n" +
                    "\n" +
                    "अभी आप को शिक्षक की भूमिका निभानी है, इसीलिए, आप को अभी Sponsor Training लेना है!");
            // Set the positive button
            builder.setPositiveButton("GO FOR SPONSOR TRAINING", null);
            builder.setCancelable(false);
            // Set the negative button
//            if (isShowOk()) {
//                builder.setNegativeButton("Ok", null);
//            }
            // Create the alert dialog
            final AlertDialog dialog = builder.create();
            // Finally, display the alert dialog
            dialog.show();
            // Get the alert dialog buttons reference
            Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//            Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            // Change the alert dialog buttons text and background color
            Yes.setTextColor(Color.parseColor("#b90d86"));
//            No.setTextColor(Color.parseColor("#b90d86"));
            Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.hide();
                    SharedPref.putString(Home.this,"sponser_test_index","0");
                    Intent i = new Intent(Home.this, SpoonserTrainigTestActivity.class);
                    startActivity(i);
                }
            });
//            No.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.hide();
//                }
//            });
            dialog.setCancelable(false);
        }
    }

    private void checkGreateSponsorTrainingTest() {
        if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("0")){
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("Pending Great Sponsor Training and Testing");
            // Show a message on alert dialog
            builder.setMessage("आप का अभिनंदन है, आप अभी Great Sponsor बनने की पहली स्टेप पूर्ण कर चुके!\n" +
                    "\uD83D\uDC90\uD83D\uDC90\uD83D\uDC51\uD83C\uDFAF\uD83D\uDCC8\uD83D\uDC90\uD83D\uDC90\n" +
                    "(Gr8 Sponsor=5 x 25=125)\n" +
                    "\n" +
                    "अभी आप को शिक्षक + मुखिया साथी की भूमिका निभानी है, इसीलिए, आप को अभी Great Sponsor Training लेना है!");
            // Set the positive button
            builder.setPositiveButton("GO FOR GREAT SPONSOR TRAINING", null);
            builder.setCancelable(false);
            // Set the negative button
//            if (isShowOk()) {
//                builder.setNegativeButton("Ok", null);
//            }
            // Create the alert dialog
            final AlertDialog dialog = builder.create();
            // Finally, display the alert dialog
            dialog.show();
            // Get the alert dialog buttons reference
            Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//            Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            // Change the alert dialog buttons text and background color
            Yes.setTextColor(Color.parseColor("#b90d86"));
//            No.setTextColor(Color.parseColor("#b90d86"));
            Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.hide();
                    SharedPref.putString(Home.this,"great_sponser_test_index","0");
                    Intent i = new Intent(Home.this, GreatSponserTrainingTestActivity.class);
                    startActivity(i);
                }
            });
//            No.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.hide();
//                }
//            });
            dialog.setCancelable(false);
        }
    }

    private void checkForPendingTurnOver() {
        if (pending_turnover != null) {
            if (Integer.parseInt(pending_turnover) == 1) {
                SharedPref.putString(Home.this, "pending_turnover", pending_turnover);
                onPendingTurnOver();
            }
        }
    }

    private void checkForRejectedTurnOver() {
        if (rejected_turnover != null && rejected_turnover.length() != 0) {
            if (Integer.parseInt(rejected_turnover) == 1) {
                SharedPref.putString(Home.this, "rejected_turnover", rejected_turnover);
                onRejectedTurnOver();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void CheckForchangeSponsorRequest() {
        String m = ChangeSponsor_SharedPref.getInstance(Home.this).getCHANGE_SPONSOR_REQUEST();
        if (m.equals("yes")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("New Request Received");
            // Show a message on alert dialog
            String sourceString = "You have received request for change sponsor, Click on MORE DETAILS to Accept or Reject request.";

            builder.setMessage(Html.fromHtml(sourceString));
            // Set the positive button
            builder.setPositiveButton("More Details", null);
            builder.setNeutralButton("Later", null);

            // Set the negative button
            // Create the alert dialog
            final AlertDialog dialog = builder.create();
            // Finally, display the alert dialog
            dialog.show();
            // Get the alert dialog buttons reference
            Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button Later = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

            // Change the alert dialog buttons text and background color
            MoreDetails.setTextColor(Color.parseColor("#b90d86"));
            Later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            MoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Home.this, ChangeSponsor.class);
                    startActivity(i);
                }
            });


            dialog.setCancelable(true);

        }
    }

    private void checkForLinkRequest() {
        String m = ChangeSponsor_SharedPref.getInstance(Home.this).getLINK_REQUEST();
        if (m.equals("yes")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("Request From Downline");
            // Show a message on alert dialog
            String sourceString = "Your referal link or sponsor id required to your downline";

            builder.setMessage(Html.fromHtml(sourceString));
            // Set the positive button
            builder.setPositiveButton("More Details", null);
            builder.setNeutralButton("Later", null);

            // Set the negative button
            // Create the alert dialog
            final AlertDialog dialog = builder.create();
            // Finally, display the alert dialog
            dialog.show();
            // Get the alert dialog buttons reference
            Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button Later = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

            // Change the alert dialog buttons text and background color
            MoreDetails.setTextColor(Color.parseColor("#b90d86"));
            Later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            MoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_Frag = new FragLinkRequests();
                    loadFragment();
                    dialog.dismiss();
                }
            });


            dialog.setCancelable(true);

        }
    }

    private void checkForPendingReason() {
        String isApprovedByAdmin = General_SharedPref.getInstance(Home.this).getIS_APPROVED_BY_ADMIN();
        String trainging_completed = General_SharedPref.getInstance(Home.this).getTRAINGING_COMPLETED();
        String reason = General_SharedPref.getInstance(Home.this).getREJECT_REASON();

        try {
            if (isApprovedByAdmin.equals("0") && trainging_completed.equals("1")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                // Set a title for alert dialog
                builder.setTitle("Approval Pending");
                // Show a message on alert dialog
                String sourceString = "Your referal Link and payment receipt approval by MMC Admin is pending due to <b>" + reason + "</b>";

                builder.setMessage(Html.fromHtml(sourceString));
                // Set the positive button
                builder.setPositiveButton("ok", null);
                builder.setNeutralButton("View or Upload Again", null);

                // Set the negative button
                // Create the alert dialog
                final AlertDialog dialog = builder.create();
                // Finally, display the alert dialog
                dialog.show();
                // Get the alert dialog buttons reference
                Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button uploadAgain = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

                // Change the alert dialog buttons text and background color
                Yes.setTextColor(Color.parseColor("#b90d86"));
                uploadAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menu_Frag = new MyPaymentProofList();
                        loadFragment();
                        dialog.dismiss();
                    }
                });
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.setCancelable(true);

            }
        } catch (Exception e) {

        }

    }


    public void checkForJoiningSteps(String status) {
//        String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
//        Toast.makeText(this, "Current Status: " + status, Toast.LENGTH_SHORT).show();
        switch (status) {
            case "1":
                UpdateStatus updateStatus = new UpdateStatus(Home.this, "2");
                General_SharedPref.getInstance(Home.this).saveJoiningStatus("2");

                dialogForIntroVideo(status);

                break;
            case "2":
                dialogForIntroVideo(status);

                break;
            case "3":
                dialogForQAVideo(status);

                break;
            case "4":
                dialogForMembershipInterview(status);

                break;
            case "5":
                dialogForAcceptanceLetter();

                break;
            case "6":
                dialogForPaymentRequest(status);

                break;
            case "7":
                Intent i = new Intent(Home.this, MultiSuccess.class);
                i.putExtra("GoTo", "1");
                startActivity(i);
                break;
            case "8":
                Intent ii = new Intent(Home.this, MultiSuccess.class);
                ii.putExtra("GoTo", "2");
                startActivity(ii);

//                checkForAdminApproval();
                break;
            case "9":
                checkForIsPaymentApprovedBySponsor();
                break;
            case "10":
                TrainingVDODialog();
                checkForIsTrainingCompleted();
                break;
            case "11":
                checkForAdminApproval();
                break;
            case "12":
                break;
        }

    }

    private void checkForIsPaymentApprovedBySponsor() {
        final android.app.AlertDialog progress = new SpotsDialog.Builder()
                .setContext(Home.this)
                .setTheme(R.style.Custom)
                .setMessage("Please wait while Check whether Your payment receipt Approved By Your Sponsor to Access MMC Business Start Training Video")
                .setCancelable(false)
                .build();
        progress.show();

        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(CHECK_IF_PAYMENT_APPROVED_BY_SPONSOR)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

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
                progress.dismiss();
//                ConstantMethods.hideLoaderDialog(Home.this);
//                Toast.makeText(Home.this, "Training Status " + success, Toast.LENGTH_SHORT).show();
                if (success) {
                    UpdateStatus updateStatus = new UpdateStatus(Home.this, "10");
                    General_SharedPref.getInstance(Home.this).saveJoiningStatus("10");
//                    checkForJoiningSteps();
                    dialogForTraining();

                } else {
                    dialogForNotApproved();

                }
            }
        };

        task.execute();

    }

    private void dialogForNotApproved() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("Approval Pending ");
        // Show a message on alert dialog
        builder.setMessage("Your Payment proof for Renatus Nova & NexMoney joining approval is pending, Please wait or Contact to your Sponsor");
        // Set the positive button
        builder.setPositiveButton("ok", null);

        // Set the negative button

        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });

        dialog.setCancelable(false);
    }

    private void checkForAdminApproval() {
        final android.app.AlertDialog progress = new SpotsDialog.Builder()
                .setContext(Home.this)
                .setTheme(R.style.Custom)
                .setMessage("Please wait while checking Admin Approval status. . .")
                .setCancelable(false)
                .build();
        progress.show();

        success = false;
        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(CHECK_FOR_ADMIN_APPROAL)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

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
                progress.dismiss();
                if (success) {
//                    UpdateStatus updateStatus = new UpdateStatus(Home.this, "12");
//                    General_SharedPref.getInstance(Home.this).saveJoiningStatus("12");
//                    congratsDialog();
                } else {
                    ConstantMethods.showAlertMessege(Home.this, "MMC Admin Approval Pending", "Please wait while MMC approve your Training Testing or Document");
                }
            }
        };

        task.execute();

    }

    private void congratsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("CONGRATULATION...!");
        // Show a message on alert dialog
        builder.setMessage("आप का अभिनंदन है!!\n" +
                "आप को MMC की तरफ से Business Approval दे दिया है!    \n" +
                "\uD83C\uDF89\uD83C\uDF89\uD83D\uDC90\uD83D\uDC90\uD83C\uDF89\uD83C\uDF89\n" +
                "\n" +
                "अभी आप को अपने Sponsor द्वारा आगे के स्टेप्स की जानकारी ले कर, उन्हें साथ ले कर और उनके मार्गदर्शन में,अपना MMC Business शुरू करना है !\n" +
                "आप को अगले, 90 days में  How To Earn 1CR TO 100CR Course की 3 Level की Practical Training पूर्ण करनी है!\n" +
                "\n" +
                "MMC Practical Training द्वारा आप की टीम भी बनेंगी और Training भी मिलती रहेगी,\n" +
                "इस Practical Training में आप को रोज शाम की 7.30pm की Training Revision में हिस्सा लेना है!\n" +
                "और MMC द्वारा जो सिखाया जा रहा है, उसे Sponsor के मार्गदर्शन में प्रक्टिकल इस्तेमाल करना है!\n" +
                "\n" +
                "आप MMC Business के द्वारा बहुत बड़ी सफलता हासिल करें ऐसी हार्दिक शुभकामनाएं !\n" +
                "\uD83D\uDC90\uD83D\uDC90\uD83D\uDC90\uD83D\uDC90\uD83D\uDC90\uD83D\uDC90\uD83D\uDC90\n" +
                "\n" +
                "जय हिंद !\n" +
                "MMC");
        // Set the positive button
        builder.setPositiveButton("Okay.. Thank You ", null);
        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });
        dialog.setCancelable(false);
    }

    private void dialogForAcceptanceLetter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
        builder.setMessage("Now its time to accept Affidavit Letter ");
        // Set the positive button
        builder.setPositiveButton("Proceed ", null);
        builder.setNegativeButton("Later", null);
        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button no = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    dialog.dismiss();
                    menu_Frag = new AffidaviteLetter();
                    loadFragment();
                } catch (Exception e) {

                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
    }

    private void dialogForMembershipInterview(String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog

        builder.setMessage("Now its time to MMC Membership Interview Question and Answers.");
        // Set the positive button
        builder.setPositiveButton("Yes, I Am Ready ", null);
        builder.setNegativeButton("Later", null);
        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button no = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    menu_Frag = new MCQList();
                    loadFragment();
                } catch (Exception e) {

                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
    }


    private void checkForIsTrainingCompleted() {

        final String user_id = Profile_SharedPref.getInstance(getApplicationContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(CHECK_IF_TRAINING_COMPLETED)
                        .post(requestBody)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

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
//                ConstantMethods.hideLoaderDialog(Home.this);
//                Toast.makeText(Home.this, "Training Status " + success, Toast.LENGTH_SHORT).show();
                if (success) {
//                    UpdateStatus updateStatus = new UpdateStatus(Home.this, "11");
                    General_SharedPref.getInstance(Home.this).saveJoiningStatus("11");

//                    checkForJoiningSteps();
                } else {
                    dialogForTraining();

                }
            }
        };

        task.execute();

    }

    private void dialogForTraining() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("CONGRATULATION...!");
        // Show a message on alert dialog
        builder.setMessage("Your payment proof & Sponsor ID Approved by Sponsor, \n" +
                "Now go to MMC Start Business Training VDO\n" +
                "And \n" +
                "Complete MMC Start Business Training & Approval by MMC for Business & Get full App access");
        // Set the positive button
        builder.setPositiveButton("Go TO Training VDO", null);
        builder.setNeutralButton(getString(R.string.how_to_use), null);

        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button howToUse = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    SharedPref.putString(Home.this,"test_index","0");
                    Intent i = new Intent(Home.this, BusinessTrainingVedioTestActivity.class);
                    startActivity(i);
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", General_SharedPref.getInstance(Home.this).getSTART_BUSINESS_VIDEO_ID());
                startActivity(i);
            }
        });

        dialog.setCancelable(false);
    }

    private void TrainingVDODialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
//        builder.setTitle("CONGRATULATION...!");
        // Show a message on alert dialog
        builder.setMessage("Please Complete MMC Start Business Training and Testing.");
        // Set the positive button
        builder.setPositiveButton("Go TO Training VDO", null);
//        builder.setNeutralButton(getString(R.string.how_to_use), null);

        // Set the negative button
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        Button howToUse = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    SharedPref.putString(Home.this,"test_index","0");
                    Intent i = new Intent(Home.this, BusinessTrainingVedioTestActivity.class);
                    startActivity(i);
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });
//        howToUse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
//                i.putExtra("youtube_video_id", General_SharedPref.getInstance(Home.this).getSTART_BUSINESS_VIDEO_ID());
//                startActivity(i);
//            }
//        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }


    private void dialogForRenatusNovaJoining() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
        builder.setMessage(getString(R.string.step_join_renatus_nova));
        // Set the positive button
        builder.setPositiveButton(getString(R.string.join_renatusnova_now), null);
        // Set the negative button
        builder.setNegativeButton(getString(R.string.upload_link), null);
        builder.setNeutralButton(getString(R.string.how_to_use), null);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button howToUse = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    Intent i = new Intent(Home.this, MultiSuccess.class);
                    i.putExtra("GoTo", "GoToRenatusNova");
                    startActivity(i);

                } catch (Exception e) {

                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, UpdateReferenceLink.class);
                i.putExtra("CompanyName", "RenatusNova");
                startActivity(i);
                dialog.dismiss();
            }
        });
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", General_SharedPref.getInstance(Home.this).getSTART_BUSINESS_VIDEO_ID());
                startActivity(i);
            }
        });

    }


    public void dialogForIntroVideo(final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
        builder.setMessage(getString(R.string.step_msg_introvdo));
        // Set the positive button
        builder.setPositiveButton(getString(R.string.watch_now), null);
        // Set the negative button
        builder.setNegativeButton(getString(R.string.later), null);
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
                try {
                    if (Integer.parseInt(status) <= 3) {
                        //This condition will be true if user logged in first Time, it results in play introducton video
                        dialog.dismiss();
                        Intent i = new Intent(Home.this, YoutubePlayVideoIntroduction.class);
                        i.putExtra("youtube_video_id", Profile_SharedPref.getInstance(Home.this).getINTRO_VDO_URL());
                        i.putExtra("call_from", "Introduction");
                        startActivity(i);
                    }
                } catch (Exception e) {

                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dialogForQAVideo(final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
        builder.setMessage(getString(R.string.step_msg_qa_video));
        // Set the positive button
        builder.setPositiveButton(getString(R.string.watch_now), null);
        // Set the negative button
        builder.setNegativeButton(getString(R.string.later), null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        dialog.setCancelable(false);
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    if (Integer.parseInt(status) == 3) {
                        Intent i = new Intent(Home.this, QASection.class);
                        startActivity(i);
                    }
                } catch (Exception e) {

                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dialogForPaymentRequest(final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("CONGRATULATIONS...!");
        // Show a message on alert dialog
        builder.setMessage(getString(R.string.step_msg_paynow));
        // Set the positive button
        builder.setPositiveButton(getString(R.string.pay_now), null);
        // Set the negative button
        builder.setNegativeButton(getString(R.string.later), null);
        builder.setNeutralButton(getString(R.string.how_to_use), null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.setCancelable(false);


        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button howToUse = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    if (Integer.parseInt(status) == 6) {
                        Intent i = new Intent(Home.this, PaymentRequest.class);
                        startActivity(i);
                    }
                } catch (Exception e) {

                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", General_SharedPref.getInstance(Home.this).getSTART_BUSINESS_VIDEO_ID());
                startActivity(i);
            }
        });

    }

    private void checkConnectivity() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected == false) {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        };
        registerReceiver(receiver, filter);

    }


    private void getLatestNews() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_ALL_NEWS)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        LATEST_NEWS_UPDATE = object.getString("news_title");

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
                General_SharedPref.getInstance(Home.this).latestNewsUpdates(LATEST_NEWS_UPDATE);
                txtMarque.setText(LATEST_NEWS_UPDATE);
            }
        };

        task.execute();

    }


    private void multiSuccessVideoThumbnail() {
        multiSuccessPathThumbnail();
        MSV_URL = Profile_SharedPref.getInstance(Home.this).getINTRO_VDO_URL();
        ImageView imgMultisuccessVideo = findViewById(R.id.imgMultisuccessVideo);

        final String msv_url = "https://img.youtube.com/vi/" + MSV_URL + "/0.jpg";

        Glide.with(Home.this)
                .load(msv_url)
                .fitCenter()
                .into(imgMultisuccessVideo);

        imgMultisuccessVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Home.this, PlayTrainingVideo.class);
                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", MSV_URL);
                i.putExtra("call_from", "msv_intro");
                startActivity(i);


            }
        });
        txtOurAchievement = findViewById(R.id.txtOurAchievement);
        txtOurAchievement1 = findViewById(R.id.txtOurAchievement1);
    }

    private void multiSuccessPathThumbnail() {
        MSP_URL = Profile_SharedPref.getInstance(Home.this).getMULTI_SUCCESS_PATH_VIDEO();
        ImageView imgMultisuccessPathVideo = findViewById(R.id.imgMultisuccessPathVideo);

        final String msv_url = "https://img.youtube.com/vi/" + MSP_URL + "/0.jpg";

        Glide.with(Home.this)
                .load(msv_url)
                .fitCenter()
                .into(imgMultisuccessPathVideo);

        imgMultisuccessPathVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Home.this, PlayTrainingVideo.class);
                Intent i = new Intent(Home.this, YoutubePlayVideoMultisuccess.class);
                i.putExtra("youtube_video_id", MSP_URL);
                i.putExtra("call_from", "msp_intro");
                startActivity(i);


            }
        });

    }

    private void initiateAchievment() {
//        achievmentRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        achievmentdata_list = new ArrayList<>();
//
//        achievmentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        achievmentListAdapter = new AchievmentListAdapter(this, achievmentdata_list, Home.this);
//        achievmentRecyclerView.setAdapter(achievmentListAdapter);

        imageSliderAchievment = findViewById(R.id.imageSliderAchievment);
        achievmentdata_list = new ArrayList<>();
        imageSliderAchievment.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSliderAchievment.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        imageSliderAchievment.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSliderAchievment.startAutoCycle();

        loadAchievment();


    }

    private void loadAchievment() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(GET_ALL_ACHIEVEMENT)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

//                        MSV_URL = object.getString("video_url_id");

                        AchievmentListData data = new AchievmentListData(object.getString("achievement_id"),
                                object.getString("achievement_title"),
                                object.getString("achievement"),
                                object.getString("achievement_url"),
                                object.getString("created_at"));
                        achievmentdata_list.add(data);
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
                achievmentListAdapter = new AchievmentListAdapter(Home.this, achievmentdata_list);
                achievmentListAdapter.setCount(achievmentdata_list.size());
                imageSliderAchievment.setSliderAdapter(achievmentListAdapter);


                if (achievmentdata_list.size() <= 0) {
//                    Toast.makeText(Home.this, "Internet connecion is too slow or Disconnected...!", Toast.LENGTH_LONG).show();
                }
//                General_SharedPref.getInstance(Home.this).SaveMSVVideoLink(MSV_URL);
            }
        };

        task.execute();

    }

    private void initiateImageSlider() {
        sliderView = findViewById(R.id.imageSlider);
        data_list = new ArrayList<>();
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();
//        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
//            @Override
//            public void onIndicatorClicked(int position) {
//                sliderView.setCurrentPagePosition(position);
//            }
//        });
        loadSliderImages();
    }

    private void loadSliderImages() {
//        ConstantMethods.loaderDialog(Home.this);
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(GET_ALL_SLIDER_IMAGES)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        SliderImageListData data = new SliderImageListData(object.getString("image_id"),
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
//                ConstantMethods.hideLoaderDialog(Home.this);
                adapter = new SliderAdapterExample(Home.this, data_list);
                adapter.setCount(data_list.size());
                sliderView.setSliderAdapter(adapter);
                if (data_list.size() <= 0) {
                    Toast.makeText(Home.this, "Internet connecion is too slow or Disconnected...!", Toast.LENGTH_LONG).show();
                }
            }
        };

        task.execute();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void attachBaseContext(Context newBase) {
        String lan = ConstantMethods.getSelectedLanguage();
        Locale newLocale;
        // .. create or get your new Locale object here.
        newLocale = new Locale(lan);
        Locale.setDefault(newLocale);


        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (CALL_ON_RESUME_HOME) {
            getCurrentStatusOfUser();
        }
//        String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
//        Log.d("Home-status",""+status);
//        if (Integer.parseInt(status) == 13){
//            onDisableApp();
//        }
        super.onResume();
    }

    @SuppressLint("MissingPermission")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (isSponsor.equals("1") && isSponserTraning.equals("0")){
            checkSponserTrainingTest();
        }else if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("0")){
            checkGreateSponsorTrainingTest();
        }else if (isSponsor.equals("1") && isSponserTraning.equals("1")){
            isSponsorTrainingDone();
        }else
            if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("1")){
            isGreatSponsorTrainingDone();
        }else if (status.equals("11")) {
            MemberShipTestPassDialog();
        } else if (status.equals("12")) {
            setNavigationClickEvent(id);
        } else if (rejected_turnover != null && rejected_turnover.length() != 0) {
            if (pending_count > 0) {
                PendingApprovalList();
            } else if (pending_turnover != null && (Integer.parseInt(pending_turnover) == 1)) {
                if (isShowOk()) {
                    setNavigationClickEvent(id);
                } else {
                    onPendingTurnOver();
                }
            } else if (Integer.parseInt(rejected_turnover) == 1) {
                onRejectedTurnOver();
            } else {
                setNavigationClickEvent(id);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void MemberShipTestPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
//                builder.setTitle("Choose Correct Option");
        // Show a message on alert dialog
        builder.setTitle("CONGRATULATION...!");
        builder.setMessage("आप ने MMC Membership Testing paas कर ली है,\n" +
                "MMC की ओर से आप का हार्दिक अभिनंदन है!\n" +
                "\uD83D\uDC90\uD83D\uDC90\uD83C\uDF89\uD83C\uDFAF\uD83C\uDF89\uD83D\uDC90\uD83D\uDC90\n" +
                "\n" +
                "आगे, MMC Admin की तरफ से आप को Business Approval दिया जायेगा,\n" +
                "इस Approval के लिए,\n" +
                "आप को MMC Admin No पर नीचे दिए डॉक्यूमेंट लिस्ट भेजना है! भेजनेवाले डिटेल स्क्रीनशॉर्ट -\n" +
                "1.Fb Profile pic/ screenshot\n" +
                "2.MMC के लिए अलग whats app No रखना है और इस नम्बर का DP MMC बैनर रखना है, \n" +
                "और इसी whats app से सभी डिटेल भेजना है\n" +
                "3.आप ने जिस कंपनी में ID लगाए हुवी है, जैसे HHI/Ok Life का Payment Tax Invoice या , GIG का जोइनिंग सर्टिफिकेट या Royal Q का Nik Name स्क्रीनशॉर्ट\n" +
                "4.आप का नाम और MMC रजिस्ट्रर मोबाइल नंबर\n" +
                "5.Sponsor Name & Mobile No \n" +
                "\n" +
                "यह सभी डिटेल MMC Admin No पर भेज देना है,\n" +
                "फिर आप की कंपनी Joining कन्फर्म/चेक कर के आप को अगले 2 दिन में अप्रूवल दिया जायेगा!\n" +
                "\n" +
                " डिटेल/डॉक्यूमेंट भेजने के लिए\n" +
                "MMC Admin No - \n" +
                "9702306464\n" +
                "\n" +
                "जय हिंद!\n" +
                "MMC\n" +
                "आप की सफलता के लिए समर्पित!");
        // Set the positive button
        builder.setPositiveButton("Okay.. Thank You", null);

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        MoreDetails.setTextColor(Color.parseColor("#b90d86"));
        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void isSponsorTrainingDone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
//                builder.setTitle("Choose Correct Option");
        // Show a message on alert dialog
        builder.setTitle("CONGRATULATION...!");
        builder.setMessage("आप MMC System द्वारा Sponsor बन गए है,\n" +
                "आप का हार्दिक अभिनंदन है!\n" +
                "\uD83D\uDC90\uD83D\uDC90\uD83C\uDF89\uD83C\uDFAF\uD83C\uDF89\uD83D\uDC90\uD83D\uDC90\n" +
                "\n" +
                "आगे Sponsor Appruval के लिए, आप को अगली कंपनी में अपना पेड़ ID लगाकर MMC Admin No पर नीचे दिए डॉक्यूमेंट लिस्ट भेजना है! \n" +
                "\uD83D\uDC47\uD83C\uDFFB\uD83D\uDC90\n" +
                "1. अगली कंपनी में ID लगाए हुवी है, जैसे HHI/Ok Life/Nexmoney/  Payment Tax Invoice\n" +
                "2. अगर GIG Join किए है, तो GIG जोइनिंग सर्टिफिकेट\n" +
                "3. अगर Royal Q Join किए है, तो Royal Q का Nikname Screenshot \n" +
                "4.Sponsor Name & Mobile No \n" +
                "\n" +
                "यह सभी डिटेल MMC Admin No पर भेज देना है,\n" +
                "फिर आप की कंपनी Joining कन्फर्म/चेक कर के आप को अगले 2 दिन में आप को Sponsor Approval दिया जायेगा!\n" +
                "\n" +
                "डिटेल/डॉक्यूमेंट भेजने के लिए\n" +
                "MMC Admin No - \n" +
                "9702306464\n" +
                "\n" +
                "जय हिंद!\n" +
                "MMC\n" +
                "आप की सफलता के लिए समर्पित!");
        // Set the positive button
        builder.setPositiveButton("Okay.. Thank You", null);

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        MoreDetails.setTextColor(Color.parseColor("#b90d86"));
        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(Home.this, MultiSuccess.class);
                startActivity(i);
//                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void isGreatSponsorTrainingDone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
//                builder.setTitle("Choose Correct Option");
        // Show a message on alert dialog
        builder.setTitle("CONGRATULATION...!");
        builder.setMessage("आप MMC System द्वारा Great Sponsor की पहली स्टेप पर आ चुके है, आप का हार्दिक अभिनंदन है!\n" +
                "\uD83D\uDC90\uD83D\uDC90\uD83C\uDF89\uD83C\uDFAF\uD83C\uDF89\uD83D\uDC90\uD83D\uDC90\n" +
                "(Great Sponsor मतलब 5 x 25 = 125)\n" +
                "\n" +
                "आगे Great Sponsor Appruval के लिए, आप को अगली कंपनी में अपना पेड़ ID लगाकर MMC Admin No पर नीचे दिए डॉक्यूमेंट लिस्ट भेजना है! \n" +
                "\uD83D\uDC47\uD83C\uDFFB\uD83D\uDC90\n" +
                "1. अगली कंपनी में ID लगाए हुवी है, जैसे HHI/Ok Life/Nexmoney/Renatus Nova  Payment/Tax Invoice\n" +
                "2. अगर GIG Join किए है, तो GIG जोइनिंग सर्टिफिकेट\n" +
                "3. अगर Royal Q Join किए है, तो Royal Q का Nikname Screenshot \n" +
                "4.Sponsor Name & Mobile No \n" +
                "\n" +
                "यह सभी डिटेल MMC Admin No पर भेज देना है,\n" +
                "फिर आप की कंपनी Joining कन्फर्म/चेक कर के आप को अगले 2 दिन में आप को Great Sponsor Approval दिया जायेगा!\n" +
                "\n" +
                "डिटेल/डॉक्यूमेंट भेजने के लिए\n" +
                "MMC Admin No - \n" +
                "9702306464\n" +
                "\n" +
                "जय हिंद!\n" +
                "MMC\n" +
                "आप की सफलता के लिए समर्पित!");
        // Set the positive button
        builder.setPositiveButton("Okay.. Thank You", null);

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button MoreDetails = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        MoreDetails.setTextColor(Color.parseColor("#b90d86"));
        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(Home.this, MultiSuccess.class);
                startActivity(i);
//                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void setNavigationClickEvent(int id) {
        String joiningStatus = General_SharedPref.getInstance(Home.this).getJoingStatus();
        if (id == R.id.nav_home) {
            if (Integer.parseInt(joiningStatus) < 13) {
                startActivity(new Intent(Home.this, Home.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            }
        } else if (id == R.id.nav_company_shopee) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new CompanyShops();
                loadFragment();
            }

        } else if (id == R.id.nav_profile) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new FragProfile();
                loadFragment();
            }

        }  else if (id == R.id.nav_my_referal_link) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new MyPaymentProofList();
                loadFragment();
            }

        } else if (id == R.id.nav_shapathPatra) {
            String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
            if (Integer.parseInt(joiningStatus) < 13) {
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    menu_Frag = new AffidaviteLetterCopy();
                    loadFragment();
                }
            }

        }
//        else if (id == R.id.nav_goMultiIncome) {
//            if (Integer.parseInt(joiningStatus) < 13) {
//                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
//                if (Integer.parseInt(status) < 12) {
//                    getCurrentStatusOfUser();
//                } else {
//                    Intent i = new Intent(Home.this, GoMultiIncome.class);
//                    startActivity(i);
//                }
//            }
//
//        }
        else if (id == R.id.nav_start_business) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 10) {
                    getCurrentStatusOfUser();
                } else {
                    Intent i = new Intent(Home.this, StartBusinessGuide.class);
                    startActivity(i);
                }
            }

        } else if (id == R.id.nav_join_telegram) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new TelegramGroupLinks();
                loadFragment();
            }

        } else if (id == R.id.nav_myteam) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    menu_Frag = new MyTeamList();
                    loadFragment();
                }
            }
        } else if (id == R.id.nav_bussinessturnover) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    Intent i = new Intent(Home.this, BusinessTurnover.class);
                    startActivity(i);
                }
            }

        }
//        else if (id == R.id.nav_change_sponsor) {
//            if (Integer.parseInt(joiningStatus) < 13) {
//                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
//                if (Integer.parseInt(status) < 12) {
//                    getCurrentStatusOfUser();
//                } else {
//                    changeSponsor();
//                }
//            }
//        }
        else if (id == R.id.nav_AboutUs) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new Frag_abou_us();
                loadFragment();
            }

        } else if (id == R.id.nav_trainning_video) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String JoingStatus = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(JoingStatus) < 10) {
                    getCurrentStatusOfUser();
                } else {
//                    Log.i("TAG", "setNavigationClickEvent: "+status);
                    if (Integer.valueOf(status) <= 10){
                        SharedPref.putString(Home.this,"test_index","0");
                        Intent i = new Intent(Home.this, BusinessTrainingVedioTestActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(Home.this, TrainningVideo.class);
                        startActivity(i);
                    }

                }
            }
        } else if (id == R.id.nav_sponsor_trainning) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    if (SponsorEligibility_SharedPref.getInstance(Home.this).getIS_SPONSOR().equals("true")) {
                        if (isSponsor!=null && isSponserTraning != null){
                            if (isSponsor.equals("1") && isSponserTraning.equals("0")){
                                SharedPref.putString(Home.this,"sponser_test_index","0");
                                Intent i = new Intent(Home.this, SpoonserTrainigTestActivity.class);
                                startActivity(i);
                            }else{
                                Intent i = new Intent(Home.this, SponsorTraining.class);
                                startActivity(i);
                            }
                        }else{
                            Intent i = new Intent(Home.this, SponsorTraining.class);
                            startActivity(i);
                        }
                    } else {
                        ConstantMethods.showAlertMessege(Home.this, "Warning", "You are not eligible to Access this Section");
                    }
                }
            }
        } else if (id == R.id.nav_great_sponsor_trainning) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    if (SponsorEligibility_SharedPref.getInstance(Home.this).getIS_GREAT_SPONSOR().equals("true")) {
                        if (isGreatSponsor != null && isGreatSponserTraning != null){
                            if (isGreatSponsor.equals("1") && isGreatSponserTraning.equals("0")){
                                SharedPref.putString(Home.this,"great_sponser_test_index","0");
                                Intent i = new Intent(Home.this, GreatSponserTrainingTestActivity.class);
                                startActivity(i);
                            }else{
                                Intent i = new Intent(Home.this, GreatSponsorTraining.class);
                                startActivity(i);
                            }
                        }else{
                            Intent i = new Intent(Home.this, GreatSponsorTraining.class);
                            startActivity(i);
                        }
                    } else {
                        ConstantMethods.showAlertMessege(Home.this, "Warning", "You are not eligible to Access this Section");
                    }
                }
            }
        } else if (id == R.id.nav_marketing_material) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    Intent ii = new Intent(Home.this, MarketingMaterial.class);
                    startActivity(ii);
                }
            }

        } else if (id == R.id.nav_news) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    menu_Frag = new News();
                    loadFragment();
                }
            }
        } else if (id == R.id.nav_event) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 10) {
                    getCurrentStatusOfUser();
                } else {
                    Intent intent = new Intent(Home.this, EventActivity.class);
                    startActivity(intent);
                }
            }
        } else if (id == R.id.nav_support) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    menu_Frag = new SupportTicketList();
                    loadFragment();
                }
            }

        } else if (id == R.id.nav_companycaresupport) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    Intent iii = new Intent(Home.this, CustomerCareActivity.class);
                    startActivity(iii);
                }
            }
        } else if (id == R.id.nav_Share) {
            if (Integer.parseInt(joiningStatus) < 13) {
                String status = General_SharedPref.getInstance(Home.this).getJoingStatus();
                if (Integer.parseInt(status) < 12) {
                    getCurrentStatusOfUser();
                } else {
                    ShareAppLink();
                }
            }
        } else if (id == R.id.nav_logout) {
            logOut();
        }
//        else if (id == R.id.nav_qasection) {
//            if (Integer.parseInt(joiningStatus) < 13) {
//                Intent i = new Intent(Home.this, QASection.class);
//                startActivity(i);
//            }
//
//        }
        else if (id == R.id.nav_success_stories) {
            if (Integer.parseInt(joiningStatus) < 13) {
                menu_Frag = new SuccessStoriesVideo();
                loadFragment();
            }

        }
    }

    private void changeSponsor() {
        Intent i = new Intent(this, ChangeSponsor.class);
        startActivity(i);
    }


    private void ShareAppLink() {
        try {
            String SHARING_MESSEGE;
            SHARING_MESSEGE = ShareUtils.getShareLink(Home.this);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "MMC Application ");
            String sAux = SHARING_MESSEGE + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Select medium to share App link"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void setActionBarTitle(String title) {
        actionBarTitle.setText(title);
    }

    private void loadFragment() {
        if (menu_Frag != null) {
            FragmentManager FM = getSupportFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.main_Container, menu_Frag)
                    .addToBackStack(String.valueOf(FM))
                    .commit();
        }
    }

    private void logOut() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("Confirm logout?");
            // Show a message on alert dialog
            builder.setMessage("Are you sure you want to logout from the application?");
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

                    ChangeSponsor_SharedPref.getInstance(Home.this).clearSharedPref();
                    General_SharedPref.getInstance(Home.this).clearSharedPref();
                    Profile_SharedPref.getInstance(Home.this).clearSharedPref();
                    Host_Details_SharedPref.getInstance(Home.this).clearSharedPref();
                    Host_Links_SharedPref.getInstance(Home.this).clearSharedPref();
                    SharedPrefForVersionCode.getInstance(Home.this).clearSharedPref();
                    My_Links_SharedPref.getInstance(Home.this).clearSharedPref();
                    MMC_Referal_Links_SharedPref.getInstance(Home.this).clearSharedPref();
                    Language_SharedPref.getInstance(Home.this).clearSharedPref();
                    PaymentGatewayDetails_SharedPref.getInstance(Home.this).clearSharedPref();

                    Intent i = new Intent(Home.this, Splash.class);
                    startActivity(i);
                    finish();
                }
            });
            No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                loadDashBoardFragment();
                    dialog.hide();
                }
            });
//         dialog.setCancelable(false);
        }
    }

    private void onDisableApp() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            // Set a title for alert dialog
            builder.setTitle("App Blocked");
            // Show a message on alert dialog
            builder.setMessage("Your app is blocked because you previous added turnover is rejected by MMC, Please Add Added turnover again");
            // Set the positive button
            builder.setPositiveButton("Ok", null);
            // Set the negative button
            //builder.setNegativeButton("No", null);
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

                    dialog.hide();

                    Intent i = new Intent(Home.this, BusinessTurnover.class);
                    startActivity(i);
                }
            });
//            No.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                loadDashBoardFragment();
//                    dialog.hide();
//                }
//            });
//         dialog.setCancelable(false);
        }
    }

    private void onPendingTurnOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("Pending Turnover");
        // Show a message on alert dialog
        builder.setMessage("You have not added turnover for current month. Please add turnover before end of this month.");
        // Set the positive button
        builder.setPositiveButton("Add TurnOver", null);
        builder.setCancelable(false);
        // Set the negative button
        if (isShowOk()) {
            builder.setNegativeButton("Ok", null);
        }
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

                dialog.hide();

                Intent i = new Intent(Home.this, BusinessTurnover.class);
                startActivity(i);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.setCancelable(false);

    }

    private void onRejectedTurnOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("Rejected Turnover");
        // Show a message on alert dialog
        builder.setMessage("Your previous added turnover rejected by mmc team. please add turnover again");
        // Set the positive button
        builder.setPositiveButton("Add TurnOver", null);
        builder.setCancelable(false);
        // Set the negative button
        if (isShowOk()) {
            builder.setNegativeButton("Ok", null);
        }
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

                dialog.hide();

                Intent i = new Intent(Home.this, BusinessTurnover.class);
                startActivity(i);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.setCancelable(false);

    }

    private boolean isShowOk() {
        boolean isShow = false;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 25) {
            isShow = true;
        }
        Log.d("KEY_SHOW", "" + isShow + " " + day);
        return isShow;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void checkUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                Log.d("appUpdateInfo :", "packageName :" + appUpdateInfo.packageName() + ", " + "availableVersionCode :" + appUpdateInfo.availableVersionCode() + ", " + "updateAvailability :" + appUpdateInfo.updateAvailability() + ", " + "installStatus :" + appUpdateInfo.installStatus());

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)) {
                    requestUpdate(appUpdateInfo);
                    Log.d("UpdateAvailable", "update is there ");
                    Toast.makeText(Home.this, "UpdateAvailable  update is there", Toast.LENGTH_SHORT).show();
                } else if (appUpdateInfo.updateAvailability() == Integer.parseInt("3")) {
                    Log.d("Update", "3");
                    notifyUser();
//                    Toast.makeText(Home.this, "Notify user ", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(Home.this, "No Update Available", Toast.LENGTH_SHORT).show();
                    Log.d("NoUpdateAvailable", "update is not there ");
                }
            }
        });
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, Home.this, MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (resultCode != RESULT_OK) {
//                        Toast.makeText(this,"RESULT_OK" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_OK  :", "" + resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    if (resultCode != RESULT_CANCELED) {
//                        Toast.makeText(this,"RESULT_CANCELED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_CANCELED  :", "" + resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:

                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED) {

//                        Toast.makeText(this,"RESULT_IN_APP_UPDATE_FAILED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_IN_APP_FAILED:", "" + resultCode);
                    }
            }
        }
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("InstallDownloded", "InstallStatus sucsses");
                notifyUser();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            appUpdateManager.unregisterListener((InstallStateUpdatedListener) this);

        } catch (Exception e) {

        }
    }

    private void notifyUser() {
        Toast.makeText(this, "An update has just been downloaded.", Toast.LENGTH_SHORT).show();
        appUpdateManager.completeUpdate();

//        Snackbar snackbar =
//                Snackbar.make(findViewById(R.id.message),
//                        "An update has just been downloaded.",
//                        Snackbar.LENGTH_INDEFINITE);
//        snackbar.setAction("RESTART", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                appUpdateManager.completeUpdate();
//            }
//        });
//        snackbar.setActionTextColor(
//                getResources().getColor(R.color.colorBlack));
//        snackbar.show();
    }

    private void resume() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    notifyUser();

                }

            }
        });
    }

    private void load_child_turnover_count() {
        // ConstantMethods.loaderDialog(this);

        final String userid = Profile_SharedPref.getInstance(this).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            JSONObject object;

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
                        .url(GET_CHILD_TURNOVER_COUNT)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    pending_count = object.getInt("pending_count");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //ConstantMethods.hideLoaderDialog(Home.this);

                if (success) {
                    if (pending_count > 0) {
                        PendingApprovalList();
                    }
                }

            }
        };

        task.execute();
    }

    private void PendingApprovalList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        // Set a title for alert dialog
        builder.setTitle("Pending Approval List");
        // Show a message on alert dialog
        builder.setMessage("You have pending turnover approval list.");
        // Set the positive button
        builder.setPositiveButton("Go to Approval List", null);
        builder.setCancelable(false);
        // Set the negative button
//                        builder.setNegativeButton("Ok", null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
//                        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();

                Intent intent = new Intent(Home.this, ApprovalList.class);
                startActivity(intent);
            }
        });
//                        No.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.hide();
//                            }
//                        });
        dialog.setCancelable(false);
    }
}