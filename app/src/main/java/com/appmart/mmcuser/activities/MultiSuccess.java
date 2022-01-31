package com.appmart.mmcuser.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.appmart.mmcuser.GetMyReferalLinks;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.multiSuccess.adapters.MultiSuccessPageAdapter;
import com.appmart.mmcuser.sharedPreference.CompanySequence_SharedPref;
import com.google.android.material.tabs.TabLayout;

public class MultiSuccess extends AppCompatActivity {
    TextView actionBarTitle;
    private TabLayout tabLayout;
    private String GOTO;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_success);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Multi Success Program");
        Window window = MultiSuccess.this.getWindow();

        window.setStatusBarColor(ContextCompat.getColor(MultiSuccess.this, R.color.colorPrimaryDark));
        GOTO = getIntent().getStringExtra("GoTo");

        tabLayout = findViewById(R.id.multisuccess_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("HHI"));
        tabLayout.addTab(tabLayout.newTab().setText("Oklifecare"));
        tabLayout.addTab(tabLayout.newTab().setText("GIG"));
        tabLayout.addTab(tabLayout.newTab().setText("NexMoney"));
        tabLayout.addTab(tabLayout.newTab().setText("Renatus Nova"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = findViewById(R.id.pager);
        final MultiSuccessPageAdapter adapter = new MultiSuccessPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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


      String companySequence=  CompanySequence_SharedPref.getInstance(getApplicationContext()).getCOMPANY_SEQUENCE_ARRAY();
        Toast.makeText(this, ""+companySequence, Toast.LENGTH_SHORT).show();

        try {
            if (GOTO.equals("1")) {
                viewPager.setCurrentItem(0);
            } else if (GOTO.equals("2")) {
                viewPager.setCurrentItem(1);
            }
        } catch (Exception e) {

        }
        new GetMyReferalLinks(MultiSuccess.this);
    }


    public void CloseActivity() {
        finish();
    }
}
