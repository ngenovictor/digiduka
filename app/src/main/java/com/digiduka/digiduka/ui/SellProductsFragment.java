package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digiduka.digiduka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProductsFragment extends Fragment {


    public SellProductsFragment() {
        // Required empty public constructor
    }
    public static SellProductsFragment newInstance(){
        SellProductsFragment sellProductsFragment = new SellProductsFragment();
        return sellProductsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell_products, container, false);
    }

}
