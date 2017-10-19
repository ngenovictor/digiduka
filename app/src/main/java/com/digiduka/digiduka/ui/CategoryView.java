package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryView extends DialogFragment {
    public static ArrayList<Category> categories;
    private RecyclerView categoriesRecyclerView;
    private CategoryListAdapter mAdapter;



    public CategoryView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category_view, container, false);
        categories= Parcels.unwrap(getArguments().getParcelable("category"));
        StockItemsAdapter adapter=new StockItemsAdapter(null);

        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView1);
        mAdapter = new CategoryListAdapter(getContext(), categories,"sell",adapter);
        categoriesRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoriesRecyclerView.setLayoutManager(layoutManager);
        categoriesRecyclerView.setHasFixedSize(false);


        return view;
    }

}
