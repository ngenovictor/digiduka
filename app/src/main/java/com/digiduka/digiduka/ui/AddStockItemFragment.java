package com.digiduka.digiduka.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockItemFragment extends DialogFragment implements View.OnClickListener {
    private RecyclerView categoriesRecyclerView;
    private Button addCategoryButton;
    private CategoryListAdapter listAdapter;
    private ArrayList<Category> categories=new ArrayList<>();
    private DatabaseReference reference;



    public AddStockItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        reference = FirebaseDatabase.getInstance()
                .getReference(Constants.CATEGORY_DB_KEY);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("data", dataSnapshot.toString());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    categories.add(data.getValue(Category.class));
                    Log.v("data", data.toString());
                }

                Log.v("data", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(this);



            listAdapter = new CategoryListAdapter(getContext(), categories);
            categoriesRecyclerView.setAdapter(listAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            categoriesRecyclerView.setLayoutManager(layoutManager);
            categoriesRecyclerView.setHasFixedSize(false);



        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == addCategoryButton) {
            FragmentManager fm = getFragmentManager();
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            addStockItemFragment.show(fm, "dialog");
        }
    }
}
