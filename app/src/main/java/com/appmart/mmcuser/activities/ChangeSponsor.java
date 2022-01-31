package com.appmart.mmcuser.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.changeSponsor.ChangeSponsorPageAdapter;
import com.appmart.mmcuser.fragments.FragLinkRequests;
import com.appmart.mmcuser.models.MyTeamListData;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.start_business.StartBusinessPageAdapter;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.appmart.mmcuser.utils.ServerAddress.GET_MY_DOWNLINK;
import static com.appmart.mmcuser.utils.ServerAddress.PLACE_CHANGE_SPONSOR_REQUEST;

public class ChangeSponsor extends AppCompatActivity {
    TextView actionBarTitle;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sponsor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Change Sponsor");
        Window window = ChangeSponsor.this.getWindow();

        window.setStatusBarColor(ContextCompat.getColor(ChangeSponsor.this, R.color.colorPrimaryDark));

        tabLayout = (TabLayout) findViewById(R.id.changeSponsor_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Received Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Placed Request"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ChangeSponsorPageAdapter adapter = new ChangeSponsorPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });


    }

}
