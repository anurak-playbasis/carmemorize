package com.carmemorize.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.carmemorize.app.fragment.BreakFragment;
import com.carmemorize.app.fragment.OilFragment;
import com.carmemorize.app.fragment.RadiaterFragment;
import com.carmemorize.app.fragment.TireFragment;


/**
 * Created by User on 11/4/2016.
 */

public class ServiceAdapter extends FragmentPagerAdapter {

    public ServiceAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){

            case 0:
                fragment = new RadiaterFragment();
                break;
            case 1 :
                fragment = new TireFragment();
                break;
            case 2 :
                fragment = new BreakFragment();
                break;
            case 3 :
                fragment = new OilFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
