package com.appmart.mmcuser.events;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class EventsPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public EventsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EventList technical = new EventList();
                return technical;
            case 1:
                SpTrainingEventList business = new SpTrainingEventList();
                return business;
            case 2:
                SponsorTrainingEventList businessa = new SponsorTrainingEventList();
                return businessa;
            case 3:
                GreatSponsorTrainingEventList asas = new GreatSponsorTrainingEventList();
                return asas;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
