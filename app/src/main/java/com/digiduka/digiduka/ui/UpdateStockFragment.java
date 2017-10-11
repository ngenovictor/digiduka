package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoriesGridViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateStockFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton addStockfab;



    public UpdateStockFragment() {
        // Required empty public constructor
    }
    public static UpdateStockFragment newInstance(){
        return new UpdateStockFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_stock, container, false);
        addStockfab = view.findViewById(R.id.addStockfab);

        addStockfab.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(view == addStockfab){
            FragmentManager fm = getFragmentManager();
            AddStockItemFragment addStockItemFragment = new AddStockItemFragment();
            addStockItemFragment.show(fm, "dialog");
        }
    }
}
