package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.models.Category;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProductsFragment extends Fragment implements View.OnClickListener{
    public static ArrayList<Category> categories;
    private FloatingActionButton viewCategory;
    public RecyclerView stockcategory;
    public CategoryListAdapter mAdapter;

    public SellProductsFragment() {
        // Required empty public constructor
    }
    public static SellProductsFragment newInstance(ArrayList<Category> mcategories){
        categories=mcategories;
        return new SellProductsFragment();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_products, container, false);
        viewCategory = view.findViewById(R.id.viewcategory);
        viewCategory.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view) {
        if(view == viewCategory){
            FragmentManager fm = getFragmentManager();
            CategoryView moodDialogFragment = new CategoryView ();
            Bundle bundle=new Bundle();
            bundle.putParcelable("category", Parcels.wrap(categories));
            moodDialogFragment.setArguments(bundle);
            moodDialogFragment.show(fm,categories.toString());

        }
    }
}
