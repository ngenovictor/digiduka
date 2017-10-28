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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.ProductListAdapter;
import com.digiduka.digiduka.adapters.StocksListViewHolder;
import com.digiduka.digiduka.adapters.TransactionsViewHolder;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Stock;
import com.digiduka.digiduka.models.Transaction;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProductsFragment extends Fragment implements View.OnClickListener{
    public static ArrayList<Category> categories;

    public static ArrayList<Product>selectedproducts1=new ArrayList<>();
    private static RecyclerView selectedView;
    private static ProductListAdapter mAdapter;
    private static Context context;
    private static TextView total;
    private Button makesale;
    private static RelativeLayout totalsec;
    private FirebaseAuth mAuth;
    private FloatingActionButton makeNewSale;
    private RecyclerView transactionItemsRecyclerView;

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
        makeNewSale = view.findViewById(R.id.makeNewSale);
        mAuth = FirebaseAuth.getInstance();
        ProductListAdapter.selectedproducts=selectedproducts1;
        context=getActivity();
        makeNewSale.setOnClickListener(this);
        transactionItemsRecyclerView = view.findViewById(R.id.transactionItemsRecyclerView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.TRANSACTIONS_DB_KEY);
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Transaction, TransactionsViewHolder>(Transaction.class, R.layout.transaction_list_viewholder, TransactionsViewHolder.class, transactionRef) {
            @Override
            protected void populateViewHolder(TransactionsViewHolder viewHolder, Transaction model, int position) {
                viewHolder.bindTransaction(model);
            }
        };
        transactionItemsRecyclerView.setHasFixedSize(false);
        transactionItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        transactionItemsRecyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onClick(View view) {
        if(view == makeNewSale){

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AddSaleItemFragment fragment = AddSaleItemFragment.newInstance();
            Bundle bundle=new Bundle();
            bundle.putParcelable("categories", Parcels.wrap(categories));
            fragment.setArguments(bundle);
            ft.add(R.id.saleProductsFragment, fragment);
            ft.commit();
        }
    }
}
