package com.appmart.mmcuser.changeSponsor;

import com.appmart.mmcuser.start_business.HowToUseStartBusiness;
import com.appmart.mmcuser.start_business.start;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class ChangeSponsorPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ChangeSponsorPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReceivedRequest technical = new ReceivedRequest();
                return technical;
            case 1:
                PlacedRequest business = new PlacedRequest();
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
