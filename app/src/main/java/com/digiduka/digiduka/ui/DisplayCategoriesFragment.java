package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.adapters.SaleProductsAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayCategoriesFragment extends DialogFragment {
    private RecyclerView categoryDisplay;
    private static ArrayList<Category> mCategories= new ArrayList<>();
    private static StockItemsAdapter mAdapter;
    private static SaleProductsAdapter salesAdapter;
    private Button addCategoryHere;
    private ImageView closePickProducts;
    private static String mSource;

    public DisplayCategoriesFragment() {
        // Required empty public constructor
    }
    //constructor if request comes from stocks side
    public static DisplayCategoriesFragment newInstance(ArrayList<Category> categories, StockItemsAdapter adapter, String source) {
        mCategories = categories;
        mAdapter = adapter;
        mSource = source;
        return new DisplayCategoriesFragment();
    }
    //constructor if request comes from stocks side
    public static DisplayCategoriesFragment newInstance(ArrayList<Category> categories, SaleProductsAdapter adapter, String source) {
        mCategories = categories;
        salesAdapter = adapter;
        mSource = source;
        return new DisplayCategoriesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_categories, container, false);
        categoryDisplay = view.findViewById(R.id.categoryDisplay);
        if (mSource.equals(Constants.STOCK_SIDE)){
            CategoryListAdapter adapter = new CategoryListAdapter(getContext(),mCategories,Constants.STOCK_SIDE, mAdapter);
            categoryDisplay.setAdapter(adapter);
        }else if(mSource.equals(Constants.SALES_SIDE)){
            CategoryListAdapter adapter = new CategoryListAdapter(getContext(),mCategories,Constants.SALES_SIDE, salesAdapter);
            categoryDisplay.setAdapter(adapter);
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoryDisplay.setLayoutManager(layoutManager);
        categoryDisplay.setHasFixedSize(false);

        addCategoryHere = view.findViewById(R.id.addCategoryHere);
        closePickProducts = view.findViewById(R.id.closePickProducts);
        closePickProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        addCategoryHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryFragment fragment = new AddCategoryFragment();
                FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                fragment.show(fm, "dialog");
            }
        });

        return  view;
    }

}
