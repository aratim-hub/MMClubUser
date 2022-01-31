package com.appmart.mmcuser.marketing_material;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class MarketingMaterialPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MarketingMaterialPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MarketingImageList technical = new MarketingImageList();
                return technical;
            case 1:
                MarketingContentList business = new MarketingContentList();
                return business;

            case 2:
                MarketingVideoList business1 = new MarketingVideoList();
                return business1;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
