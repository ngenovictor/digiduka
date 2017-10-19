package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Stock;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewStockItemsFragment extends DialogFragment {
    private static Stock stockItem;


    public NewStockItemsFragment() {
        // Required empty public constructor
    }
    public static NewStockItemsFragment newInstance(Stock stock){
        stockItem = stock;
        return new NewStockItemsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fragment_new_stock_items, container, false);
        return thisView;
    }

}
