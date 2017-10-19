package com.digiduka.digiduka.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.ui.SellProductsFragment;
import com.digiduka.digiduka.ui.UpdateStockFragment;

import java.util.ArrayList;

/**
 * Created by victor on 10/11/17.
 */

public class MainActivityFragmentsAdapter extends FragmentPagerAdapter {
    public ArrayList<Category> categories;
    public MainActivityFragmentsAdapter(FragmentManager fm, ArrayList<Category> categories){
        super(fm);
        this.categories=categories;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SellProductsFragment.newInstance();
            case 1:
                return UpdateStockFragment.newInstance(categories);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
