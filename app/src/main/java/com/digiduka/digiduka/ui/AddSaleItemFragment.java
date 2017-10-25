package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.SaleProductsAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Transaction;
import com.digiduka.digiduka.utils.Constants;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSaleItemFragment extends Fragment implements View.OnClickListener{
    private View thisView;
    private Button cancelSaleButton;
    private Button pickProducts;
    private Button doneButton;
    public static Transaction transaction;
    private RecyclerView saleItems;
    private SaleProductsAdapter mAdapter;
    private static ArrayList<Category> categories = new ArrayList<>();



    public AddSaleItemFragment() {
        // Required empty public constructor
    }
    public static AddSaleItemFragment newInstance(){
        return new AddSaleItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories= Parcels.unwrap(getArguments().getParcelable("categories"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_add_sale_item, container, false);
        cancelSaleButton = thisView.findViewById(R.id.cancelSaleButton);
        pickProducts = thisView.findViewById(R.id.pickProducts);
        doneButton = thisView.findViewById(R.id.doneButton);
        saleItems = thisView.findViewById(R.id.saleItems);

        mAdapter = new SaleProductsAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        saleItems.setLayoutManager(layoutManager);
        saleItems.setHasFixedSize(false);
        saleItems.setAdapter(mAdapter);
        pickProducts.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        cancelSaleButton.setOnClickListener(this);

        return thisView;
    }

    @Override
    public void onClick(View view) {
        if (view==pickProducts){
            //put action t pick products
            Bundle bundle = new Bundle();
            FragmentManager fm = getFragmentManager();
            DisplayCategoriesFragment fragment = DisplayCategoriesFragment.newInstance(categories, mAdapter, Constants.SALES_SIDE);
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            fragment.show(fm, "dialog");

        }else if (view==doneButton){
            //put actions to done button
        }else if (view==cancelSaleButton){
            getFragmentManager().popBackStack();
            thisView.setVisibility(View.GONE);
        }
    }
}
