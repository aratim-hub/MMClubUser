package com.appmart.mmcuser.start_business_guide;

import com.appmart.mmcuser.start_business_guide.fragment.BusinessGuideContent;
import com.appmart.mmcuser.start_business_guide.fragment.BusinessGuideVideo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class BusinessGuidePageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public BusinessGuidePageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BusinessGuideContent technical = new BusinessGuideContent();
                technical.partId = 1;
                return technical;
            case 1:
                BusinessGuideContent followup = new BusinessGuideContent();
                followup.partId = 2;
                return followup;
            case 2:
                BusinessGuideVideo business = new BusinessGuideVideo();
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
