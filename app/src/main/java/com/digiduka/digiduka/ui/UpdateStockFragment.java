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
public class UpdateStockFragment extends Fragment {


    public UpdateStockFragment() {
        // Required empty public constructor
    }
    public static UpdateStockFragment newInstance(){
        return new UpdateStockFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_stock, container, false);
    }

}
