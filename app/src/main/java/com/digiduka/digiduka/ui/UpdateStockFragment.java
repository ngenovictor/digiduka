package com.digiduka.digiduka.ui;


import android.content.Context;
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

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.StocksListViewHolder;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Stock;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateStockFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton addStockfab;
    private static ArrayList<Category> categories;
    private Context mContext;
    private RecyclerView stockItemsRecyclerView;
    private FirebaseUser user;
    private DatabaseReference stockRef;
    public UpdateStockFragment() {
        // Required empty public constructor
    }
    public static UpdateStockFragment newInstance(ArrayList<Category> mcategories){
        categories=mcategories;
        return new UpdateStockFragment();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_stock, container, false);
        addStockfab = view.findViewById(R.id.addStockfab);
        addStockfab.setOnClickListener(this);
        addStockfab.setVisibility(View.VISIBLE);
        mContext = view.getContext();


        user = FirebaseAuth.getInstance().getCurrentUser();
        stockItemsRecyclerView = view.findViewById(R.id.stockItemsRecyclerView);
        stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.STOCKS_DB_KEY);
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Stock, StocksListViewHolder>(Stock.class, R.layout.stocks_list_viewholder, StocksListViewHolder.class, stockRef) {
            @Override
            protected void populateViewHolder(StocksListViewHolder viewHolder, Stock model, int position) {
                viewHolder.bindStock(model);
            }
        };
        stockItemsRecyclerView.setHasFixedSize(false);
        stockItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        stockItemsRecyclerView.setAdapter(adapter);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(view == addStockfab){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            FragmentManager fm = getFragmentManager();
            AddStockItemFragment addStockItemFragment = AddStockItemFragment.newInstance(addStockfab);
            Bundle bundle=new Bundle();
            bundle.putParcelable("categories", Parcels.wrap(categories));
            fragmentTransaction.add(R.id.container, addStockItemFragment);
            addStockItemFragment.setArguments(bundle);
            fragmentTransaction.commit();
//            addStockItemFragment.show(fm, "dialog");
//            addStockfab.setVisibility(View.GONE);
        }
    }
}
