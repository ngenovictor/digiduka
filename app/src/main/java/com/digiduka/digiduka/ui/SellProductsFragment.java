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
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
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
        selectedView = view.findViewById(R.id.selectedView);
        total=view.findViewById(R.id.textView6);
        makesale=view.findViewById(R.id.button2);
        totalsec=view.findViewById(R.id.totalsec);
        totalsec.setVisibility(View.GONE);
        context=getActivity();
        showSavedItems();
        makeNewSale.setOnClickListener(this);
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
        if(view==makesale){
            final ArrayList<String> productIds = new ArrayList<>();
            for (Product product: selectedproducts1){
                productIds.add(product.getPushId());
            }

            DatabaseReference productsRef = FirebaseDatabase.getInstance()
                    .getReference(mAuth.getCurrentUser().getUid())
                    .child(Constants.PRODUCTS_DB_KEY);


            productsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (productIds.contains(dataSnapshot.getKey())){
                        Product thisProduct = dataSnapshot.getValue(Product.class);
                        int amount = thisProduct.getAmount()-1;
                        thisProduct.setAmount(amount);
                        DatabaseReference thisProductRef = dataSnapshot.getRef();
                        thisProductRef.setValue(thisProduct);
                    }
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
            ProductListAdapter.selectedproducts.clear();
            selectedproducts1.clear();
            mAdapter.notifyDataSetChanged();
            totalsec.setVisibility(View.GONE);
            showSavedItems();
        }
    }
}
