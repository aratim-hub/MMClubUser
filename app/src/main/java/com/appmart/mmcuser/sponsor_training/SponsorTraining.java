package com.appmart.mmcuser.sponsor_training;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.start_business_guide.BusinessGuidePageAdapter;
import com.appmart.mmcuser.start_business_guide.StartBusinessGuide;
import com.google.android.material.tabs.TabLayout;

public class SponsorTraining extends AppCompatActivity {
    TextView actionBarTitle;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_training);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("SPONSOR TRAINING");
        Window window = SponsorTraining.this.getWindow();

        window.setStatusBarColor(ContextCompat.getColor(SponsorTraining.this, R.color.colorPrimaryDark));

        tabLayout = (TabLayout) findViewById(R.id.start_business_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Sponsor Training Content"));
        tabLayout.addTab(tabLayout.newTab().setText("Sponsor Training Video"));


//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final SponsorTrainingPageAdapter adapter = new SponsorTrainingPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
