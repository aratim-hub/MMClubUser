package com.appmart.mmcuser.qa_session.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appmart.mmcuser.qa_session.MMC_QA;
import com.appmart.mmcuser.qa_session.NexMoney_QA;
import com.appmart.mmcuser.qa_session.OkLifeCare_QA;
import com.appmart.mmcuser.qa_session.RenatusNova_QA;
import com.appmart.mmcuser.qa_session.SwissGold_QA;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class QAPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public QAPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MMC_QA mmc_qa = new MMC_QA();
                return mmc_qa;

            case 1:
                RenatusNova_QA renatusNova_qa = new RenatusNova_QA();
                return renatusNova_qa;


            case 2:
                NexMoney_QA technical = new NexMoney_QA();
                return technical;


            case 3:
                OkLifeCare_QA okLifeCare_qa = new OkLifeCare_QA();
                return okLifeCare_qa;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
