package com.appmart.mmcuser.trainingvideo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appmart.mmcuser.trainingvideo.fragment.Atomy_training;
import com.appmart.mmcuser.trainingvideo.fragment.DaBank_training;
import com.appmart.mmcuser.trainingvideo.fragment.IntraGold_training;
import com.appmart.mmcuser.trainingvideo.fragment.MMC_training;
import com.appmart.mmcuser.trainingvideo.fragment.OkLIfeCare_training;
import com.appmart.mmcuser.trainingvideo.fragment.RenatusNova_training;
import com.appmart.mmcuser.trainingvideo.fragment.NexMoney_training;
import com.appmart.mmcuser.trainingvideo.fragment.SelfDevelopment_training;
import com.appmart.mmcuser.trainingvideo.fragment.SwissGold_training;


/**
 * Created by Aniruddha on 01/11/2017.
 */

public class TrainingVideoPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TrainingVideoPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MMC_training technical = new MMC_training();
                return technical;
            case 1:
                SelfDevelopment_training selfDevelopment_training = new SelfDevelopment_training();
                return selfDevelopment_training;
            case 2:
                NexMoney_training business = new NexMoney_training();
                return business;
            case 3:
                OkLIfeCare_training sports = new OkLIfeCare_training();
                return sports;
            case 4:
                RenatusNova_training general = new RenatusNova_training();
                return general;
            case 5:
                DaBank_training medical = new DaBank_training();
                return medical;
            case 6:
                Atomy_training scienceNature = new Atomy_training();
                return scienceNature;
            case 7:
                SwissGold_training swissGold_training = new SwissGold_training();
                return swissGold_training;
            case 8:
                IntraGold_training intraGold_training = new IntraGold_training();
                return intraGold_training;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
