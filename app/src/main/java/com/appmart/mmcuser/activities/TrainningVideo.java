package com.appmart.mmcuser.activities;

import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.trainingvideo.adapter.TrainingVideoPageAdapter;

public class TrainningVideo extends AppCompatActivity {
    private TabLayout tabLayout;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainning_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Training Video ");
        Window window = TrainningVideo.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(TrainningVideo.this,R.color.colorPrimaryDark));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout = findViewById(R.id.trainning_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Business Training")); //MMC
        tabLayout.addTab(tabLayout.newTab().setText("Business Booster")); //Self Development
        tabLayout.addTab(tabLayout.newTab().setText("Advance Booster")); //Nex Money
        tabLayout.addTab(tabLayout.newTab().setText("Great Business")); //Ok Life Care
        tabLayout.addTab(tabLayout.newTab().setText("X X X X")); //Renatus Nova
        tabLayout.addTab(tabLayout.newTab().setText("X X X X"));
        tabLayout.addTab(tabLayout.newTab().setText("X X X X"));
        tabLayout.addTab(tabLayout.newTab().setText("X X X X"));
        tabLayout.addTab(tabLayout.newTab().setText("X X X X"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = findViewById(R.id.pager);
        final TrainingVideoPageAdapter adapter = new TrainingVideoPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
