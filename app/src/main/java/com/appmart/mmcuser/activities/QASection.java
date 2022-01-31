package com.appmart.mmcuser.activities;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.qa_session.adapters.QAPageAdapter;

public class QASection extends AppCompatActivity {
    TextView actionBarTitle;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qasection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Questions & Answers");

//****************Status bar color change to color primary dark  ************************************
        Window window = QASection.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(QASection.this, R.color.colorPrimaryDark));


        tabLayout = (TabLayout) findViewById(R.id.qa_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("MMC Q & A"));
        tabLayout.addTab(tabLayout.newTab().setText("Renatus Nova"));
        tabLayout.addTab(tabLayout.newTab().setText("Nex Money"));
        tabLayout.addTab(tabLayout.newTab().setText("Ok Life Care"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final QAPageAdapter adapter = new QAPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
