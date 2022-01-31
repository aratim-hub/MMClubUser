package com.appmart.mmcuser.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Splash;
import com.appmart.mmcuser.events.EventsPageAdapter;
import com.appmart.mmcuser.marketing_material.MarketingMaterialPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();

//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Gurukul");
        Window window = EventActivity.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(EventActivity.this,R.color.colorPrimaryDark));
        }

        tabLayout = (TabLayout) findViewById( R.id.marketing_tab_layout );

        tabLayout.addTab( tabLayout.newTab().setText("MMC Events"));
        tabLayout.addTab( tabLayout.newTab().setText( "SP Training Revision") );
        tabLayout.addTab( tabLayout.newTab().setText( "Sponsor Training Revision") );
        tabLayout.addTab( tabLayout.newTab().setText( "Great Sponsor Training Revision") );
        final ViewPager viewPager = (ViewPager) findViewById( R.id.pager );
        final EventsPageAdapter adapter = new EventsPageAdapter( getSupportFragmentManager(), tabLayout.getTabCount() );
        viewPager.setAdapter( adapter );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        } );
    }

    public void setActionBarTitle(String title) {
        actionBarTitle.setText(title);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EventActivity.this, Splash.class);
        startActivity(i);
    }
}