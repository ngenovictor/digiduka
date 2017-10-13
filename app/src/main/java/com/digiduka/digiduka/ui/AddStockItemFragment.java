package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoriesGridViewAdapter;
import com.digiduka.digiduka.databaseHandlers.TableControllerCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockItemFragment extends DialogFragment implements View.OnClickListener{
    private GridView gridView;
    private Button addCategoryButton;
    private TextView recordCountText;
    /**
     * initializes the SQL Database TableController
     * **/
    TableControllerCategory tableControllerCategory = new TableControllerCategory(getContext());


    public AddStockItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        gridView = view.findViewById(R.id.gridView);
        CategoriesGridViewAdapter adapter = new CategoriesGridViewAdapter(getContext());
        gridView.setAdapter(adapter);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(this);
        //countRecords();
        return view;
    }//

    @Override
    public void onClick(View view) {
        if(view == addCategoryButton){
            FragmentManager fm = getFragmentManager();
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            addStockItemFragment.show(fm, "dialog");
        }
    }

    /**
     * count method, retrieves the number of records in sql database
     * **/
//    public void countRecords() {
//        int recordCount = tableControllerCategory.count();
////        recordCountText = (TextView) getView().findViewById(R.id.categoryShowRecord);
////        recordCountText.setText(recordCount);
//    }
    /**
     * count method, retrieves the number of records in sql database
     * **/
}
