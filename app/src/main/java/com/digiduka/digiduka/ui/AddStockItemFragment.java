package com.digiduka.digiduka.ui;


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
import com.digiduka.digiduka.adapters.CategoriesRecyclerViewAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockItemFragment extends DialogFragment implements View.OnClickListener{
    private RecyclerView categoriesRecyclerView;
    private Button addCategoryButton;
    private ArrayList<Category> categories = new ArrayList<>();
    private DatabaseReference reference;


    public AddStockItemFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance()
                .getReference(Constants.CATEGORY_DB_KEY);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("stuff", dataSnapshot.getValue(Category.class).getCategoryTitle());
                categories.add(dataSnapshot.getValue(Category.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);

        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(this);
        final CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(getContext(), categories);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == addCategoryButton){
            FragmentManager fm = getFragmentManager();
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            addStockItemFragment.show(fm, "dialog");
        }
    }
}
