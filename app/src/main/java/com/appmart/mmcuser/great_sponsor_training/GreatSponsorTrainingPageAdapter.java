package com.appmart.mmcuser.great_sponsor_training;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appmart.mmcuser.sponsor_training.fragment.SponsorTrainingContent;
import com.appmart.mmcuser.sponsor_training.fragment.SponsorTrainingVideo;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class GreatSponsorTrainingPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public GreatSponsorTrainingPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GreatSponsorTrainingContent technical = new GreatSponsorTrainingContent();
                return technical;
            case 1:
                GreatSponsorTrainingVideo business = new GreatSponsorTrainingVideo();
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
