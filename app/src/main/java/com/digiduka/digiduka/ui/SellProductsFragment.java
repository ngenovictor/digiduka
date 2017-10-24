package com.digiduka.digiduka.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.ProductListAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProductsFragment extends Fragment implements View.OnClickListener{
    public static ArrayList<Category> categories;
    private FloatingActionButton viewCategory;
    public static ArrayList<Product>selectedproducts1=new ArrayList<>();
    private static RecyclerView selectedView;
    private static ProductListAdapter mAdapter;
    private static Context context;
    private static TextView total;
    private Button makesale;
    private static RelativeLayout totalsec;

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
        ProductListAdapter.selectedproducts=selectedproducts1;
        selectedView = view.findViewById(R.id.selectedView);
        total=view.findViewById(R.id.textView6);
        makesale=view.findViewById(R.id.button2);
        totalsec=view.findViewById(R.id.totalsec);
        totalsec.setVisibility(View.GONE);
        context=getActivity();
        showSavedItems();
        viewCategory.setOnClickListener(this);
        makesale.setOnClickListener(this);
        return view;
    }
    public static void showSavedItems(){
        if (selectedproducts1.size()!=0){
            totalsec.setVisibility(View.VISIBLE);
            mAdapter = new ProductListAdapter(context,selectedproducts1 );
            selectedView.setAdapter(mAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            selectedView.setLayoutManager(layoutManager);
            selectedView.setHasFixedSize(false);
            int sum=0;
            for (Product product:selectedproducts1){
                sum+=product.getSellingPrice();
            }
            total.setText("Total: Ksh "+String.valueOf(sum));
        }
    }
    @Override
    public void onClick(View view) {
        if(view == viewCategory){
            Log.v("new items",String.valueOf(selectedproducts1.size()));
            FragmentManager fm = getFragmentManager();
            CategoryView moodDialogFragment = new CategoryView();
            Bundle bundle=new Bundle();
            bundle.putParcelable("category", Parcels.wrap(categories));
            moodDialogFragment.setArguments(bundle);
            moodDialogFragment.show(fm,categories.toString());
        }
        if(view==makesale){
            ProductListAdapter.selectedproducts.clear();
            selectedproducts1.clear();
            mAdapter.notifyDataSetChanged();
            totalsec.setVisibility(View.GONE);
            showSavedItems();
        }
    }
}
