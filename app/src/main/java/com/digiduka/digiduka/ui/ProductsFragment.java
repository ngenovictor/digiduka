package com.digiduka.digiduka.ui;


import android.graphics.Rect;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.adapters.ProductListAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class ProductsFragment extends DialogFragment {
    private Category category;
    private RecyclerView productsRecycler;
    private ProductListAdapter mAdapter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ArrayList<Product> products=new ArrayList<>();

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_products, container, false);
        category=Parcels.unwrap(getArguments().getParcelable("category"));
        productsRecycler=view.findViewById(R.id.productsRecyclerView);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference(mAuth.getCurrentUser().getUid()).child(Constants.CATEGORY_DB_KEY).child(category.getCategoryId()).child("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("data",dataSnapshot.toString());
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    products.add(data.getValue(Product.class));
                }
                Log.v("size",String.valueOf(products.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new ProductListAdapter(getActivity(), products);
        productsRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        productsRecycler.setLayoutManager(layoutManager);
        productsRecycler.setHasFixedSize(false);

        return view;
    }

}
