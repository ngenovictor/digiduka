package com.digiduka.digiduka.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digiduka.digiduka.ui.SellProductsFragment;
import com.digiduka.digiduka.ui.UpdateStockFragment;

/**
 * Created by victor on 10/11/17.
 */

public class MainActivityFragmentsAdapter extends FragmentPagerAdapter {
    public MainActivityFragmentsAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SellProductsFragment.newInstance();
            case 1:
                return UpdateStockFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
