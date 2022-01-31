package com.appmart.mmcuser.multiSuccess.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appmart.mmcuser.multiSuccess.fragment.GIG;
import com.appmart.mmcuser.multiSuccess.fragment.HHI;
import com.appmart.mmcuser.multiSuccess.fragment.NexMoney;
import com.appmart.mmcuser.multiSuccess.fragment.OkLifeCare;
import com.appmart.mmcuser.multiSuccess.fragment.RenatusNova;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class MultiSuccessPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MultiSuccessPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HHI medical = new HHI();
                return medical;

            case 1:
                OkLifeCare business = new OkLifeCare();
                return business;

            case 2:
                GIG atomy = new GIG();
                return atomy;

            case 3:
                NexMoney technical = new NexMoney();
                return technical;

            case 4:
                RenatusNova daBank = new RenatusNova();
                return daBank;


            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
