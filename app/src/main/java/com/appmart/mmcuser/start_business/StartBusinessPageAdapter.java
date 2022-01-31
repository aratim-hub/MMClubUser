package com.appmart.mmcuser.start_business;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class StartBusinessPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public StartBusinessPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                start technical = new start();
                return technical;
            case 1:
                HowToUseStartBusiness business = new HowToUseStartBusiness();
                return business;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
