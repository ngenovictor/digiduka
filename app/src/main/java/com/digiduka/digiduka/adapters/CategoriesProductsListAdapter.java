package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Variation;
import com.digiduka.digiduka.ui.AddStockItemFragment;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 10/17/17.
 */

public class CategoriesProductsListAdapter extends RecyclerView.Adapter<CategoriesProductsListAdapter.CategoriesProductsListViewHolder> {
    private FirebaseUser currentUser;
    private Category category;
    private Context mContext;
    private ArrayList<Product> products = new ArrayList<>();
    private CategoriesProductsListAdapter mAdapter;
    public CategoriesProductsListAdapter(Category category, Context context , ArrayList<Product> products){
        this.category = category;
        mContext = context;
        mAdapter = this;
        this.products.addAll(products);
    }

    @Override
    public CategoriesProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_products_list, parent,false);
        CategoriesProductsListViewHolder holder = new CategoriesProductsListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriesProductsListViewHolder holder, int position) {
        holder.bindProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CategoriesProductsListViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productVariationSize;
        private TextView productVariationPrice;
        private Button pickProductButton;
        public CategoriesProductsListViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productVariationSize = itemView.findViewById(R.id.productVariationSize);
            productVariationPrice = itemView.findViewById(R.id.productVariationPrice);
            pickProductButton = itemView.findViewById(R.id.pickProductButton);



        }
        public void bindProduct(Product product){
            productName.setText(product.getNameOfProduct());
            pickProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//
//                        FragmentManager fm = getFragmentManager();
//                        AddStockItemFragment addStockItemFragment = new AddStockItemFragment();
//                        Bundle bundle=new Bundle();
//                        bundle.putParcelable("categories", Parcels.wrap(categories));
//                        fragmentTransaction.add(R.id.container, addStockItemFragment);
//                        addStockItemFragment.setArguments(bundle);
//                        fragmentTransaction.commit();
//            addStockItemFragment.show(fm, "dialog");
                }
            });

            }
        }
    }

