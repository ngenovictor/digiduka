package com.digiduka.digiduka.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoriesGridViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockItemFragment extends DialogFragment  implements View.OnClickListener{
    private GridView gridView;
    private Button addCategoryButton;

    public AddStockItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        gridView = view.findViewById(R.id.gridView);
        CategoriesGridViewAdapter adapter = new CategoriesGridViewAdapter(getContext());
        gridView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == addCategoryButton) {

        }
    }
}
