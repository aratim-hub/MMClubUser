package com.appmart.mmcuser.activities;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
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

import com.appmart.mmcuser.ContextWrapper;
import com.appmart.mmcuser.marketing_material.MarketingMaterialPageAdapter;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.utils.ConstantMethods;

import java.util.Locale;

public class MarketingMaterial  extends AppCompatActivity {
    private TabLayout tabLayout;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Marketing Material");
        Window window = MarketingMaterial.this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(MarketingMaterial.this,R.color.colorPrimaryDark));
        }

        tabLayout = (TabLayout) findViewById( R.id.marketing_tab_layout );

        tabLayout.addTab( tabLayout.newTab().setText( getString(R.string.sharing_image)) );
        tabLayout.addTab( tabLayout.newTab().setText( getString(R.string.closing_content)) );
        tabLayout.addTab( tabLayout.newTab().setText( getString(R.string.how_to_use))  );

//        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL );
//        tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );

        final ViewPager viewPager = (ViewPager) findViewById( R.id.pager );
        final MarketingMaterialPageAdapter adapter = new MarketingMaterialPageAdapter( getSupportFragmentManager(), tabLayout.getTabCount() );
        viewPager.setAdapter( adapter );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
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
}
