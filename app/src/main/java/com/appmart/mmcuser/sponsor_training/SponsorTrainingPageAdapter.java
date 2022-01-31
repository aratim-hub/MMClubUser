package com.appmart.mmcuser.sponsor_training;

import com.appmart.mmcuser.sponsor_training.fragment.SponsorTrainingContent;
import com.appmart.mmcuser.sponsor_training.fragment.SponsorTrainingVideo;
import com.appmart.mmcuser.start_business_guide.fragment.BusinessGuideContent;
import com.appmart.mmcuser.start_business_guide.fragment.BusinessGuideVideo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class SponsorTrainingPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SponsorTrainingPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SponsorTrainingContent technical = new SponsorTrainingContent();
                return technical;
            case 1:
                SponsorTrainingVideo business = new SponsorTrainingVideo();
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
