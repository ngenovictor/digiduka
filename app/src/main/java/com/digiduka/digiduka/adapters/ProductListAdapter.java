package com.digiduka.digiduka.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;


import java.util.ArrayList;

/**
 * Created by mosoti on 10/18/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private Context mContext;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private ArrayList<Product>selectedproducts=new ArrayList<>();



    public ProductListAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        mProducts = products;


    }

    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ProductViewHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        // Log.v("items",mItems.get(0).getName());
        return mProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder  {
        private TextView name;
        private TextView description;
        private TextView quantity;
        private TextView amount;
        private Context mContext1;


        public ProductViewHolder(View itemView) {
            super(itemView);
            mContext1 = itemView.getContext();
            name = itemView.findViewById(R.id.textView);
            description=itemView.findViewById(R.id.textView2);
            amount=itemView.findViewById(R.id.textView4);
            quantity=itemView.findViewById(R.id.textView3);



        }

        public void bindProduct(final Product product) {
            name.setText(product.getNameOfProduct());
            quantity.setVisibility(View.INVISIBLE);
            description.setText(product.getDescription());
            amount.setText(String.valueOf(product.getSellingPrice()));
            amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedproducts.add(product);
                    Log.v("selected",String.valueOf(selectedproducts.size()));
                }
            });



        }




    }
}
