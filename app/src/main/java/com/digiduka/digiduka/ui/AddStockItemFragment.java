package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;

import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Stock;
import com.digiduka.digiduka.utils.Constants;

import com.digiduka.digiduka.adapters.CategoriesRecyclerViewAdapter;
import com.digiduka.digiduka.databaseHandlers.TableControllerCategory;
import com.digiduka.digiduka.adapters.FirebaseCategoriesViewHolder;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


public class AddStockItemFragment extends Fragment implements View.OnClickListener{

    private RecyclerView categoriesRecyclerView;
    private Button addCategoryButton;
    private CategoryListAdapter mAdapter;
    private Button cancelAddStockButton;
    private static FloatingActionButton button;
    private Button doneButton;
    public static Stock stock;
    private StockItemsAdapter mAdapter2;
    /**
     * initializes the SQL Database TableController
     * **/
    TableControllerCategory tableControllerCategory = new TableControllerCategory(getContext());
    private static ArrayList<Category> categories = new ArrayList<>();




    public AddStockItemFragment() {
        // Required empty public constructor
    }
    public static AddStockItemFragment newInstance(FloatingActionButton comingButton){
        button = comingButton;//remove this
        return new AddStockItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories= Parcels.unwrap(getArguments().getParcelable("categories"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(this);

        //setting the adapter for the recycler view
//        mAdapter = new CategoryListAdapter(getContext(), categories);
//        categoriesRecyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        categoriesRecyclerView.setLayoutManager(layoutManager);
//        categoriesRecyclerView.setHasFixedSize(false);

        //
        mAdapter2 = new StockItemsAdapter(getContext());
        categoriesRecyclerView.setAdapter(mAdapter2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoriesRecyclerView.setLayoutManager(layoutManager);
        categoriesRecyclerView.setHasFixedSize(false);

        cancelAddStockButton = view.findViewById(R.id.cancelAddStockButton);
        cancelAddStockButton.setOnClickListener(this);
        doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == addCategoryButton) {
            Bundle bundle = new Bundle();
            FragmentManager fm = getFragmentManager();
            DisplayCategoriesFragment fragment = DisplayCategoriesFragment.newInstance(categories, mAdapter2);
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            fragment.show(fm, "dialog");
        }else if(view == cancelAddStockButton){
            //how do we close the fragment??
            Toast.makeText(getContext(), "Cancel button pressed", Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
        }else if(view ==doneButton){
            if (stock==null){
                Toast.makeText(getContext(), "You haven't added any new items", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(), "You have added new items", Toast.LENGTH_LONG).show();
            }
        }
    }

}
