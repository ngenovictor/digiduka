package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateStockFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton addStockfab;
    private static ArrayList<Category> categories;



    public UpdateStockFragment() {
        // Required empty public constructor
    }
    public static UpdateStockFragment newInstance(ArrayList<Category> mcategories){
        categories=mcategories;
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
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            FragmentManager fm = getFragmentManager();
            AddStockItemFragment addStockItemFragment = new AddStockItemFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("categories", Parcels.wrap(categories));
            fragmentTransaction.add(R.id.container, addStockItemFragment);
            addStockItemFragment.setArguments(bundle);
            fragmentTransaction.commit();
//            addStockItemFragment.show(fm, "dialog");
        }
    }
}
