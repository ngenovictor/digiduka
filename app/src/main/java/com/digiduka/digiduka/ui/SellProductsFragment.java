package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digiduka.digiduka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProductsFragment extends Fragment{

    public SellProductsFragment() {
        // Required empty public constructor
    }
    public static SellProductsFragment newInstance(){
        return new SellProductsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_products, container, false);
        return view;
    }
}
