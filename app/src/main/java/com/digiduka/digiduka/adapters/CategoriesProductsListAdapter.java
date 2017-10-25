package com.digiduka.digiduka.adapters;

import android.content.Context;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

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
import com.digiduka.digiduka.models.Stock;
import com.digiduka.digiduka.ui.AddStockItemFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by victor on 10/17/17.
 */

public class CategoriesProductsListAdapter extends RecyclerView.Adapter<CategoriesProductsListAdapter.CategoriesProductsListViewHolder> {
    private FirebaseUser currentUser;
    private Category category;
    private Context mContext;
    private ArrayList<Product> products = new ArrayList<>();
    private StockItemsAdapter mAdapter;

    public CategoriesProductsListAdapter(Category category, Context context, ArrayList<Product> products, StockItemsAdapter adapter) {
        this.category = category;
        mContext = context;
        mAdapter = adapter;
        this.products.addAll(products);
    }

    @Override
    public CategoriesProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_products_list, parent, false);
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
        private TextView productSize;
        private TextView productPrice;
        private Button pickProductButton;
        private ConstraintLayout dialProductsHolder;
        private Button incrementProduct;
        private Button deductProduct;

        public CategoriesProductsListViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productSize = itemView.findViewById(R.id.productSize);
            productPrice = itemView.findViewById(R.id.productPrice);
            pickProductButton = itemView.findViewById(R.id.pickProductButton);
            dialProductsHolder = itemView.findViewById(R.id.dialProductsHolder);
            incrementProduct = itemView.findViewById(R.id.incrementProduct);
            deductProduct = itemView.findViewById(R.id.deductProduct);

        }

        public void bindProduct(final Product product) {
            productName.setText(product.getNameOfProduct());
            productSize.setText(product.getSize());
            productPrice.setText(Integer.toString(product.getBuyingPrice()));

            if (!(AddStockItemFragment.stock==null) && AddStockItemFragment.stock.containsProduct(product)){
                pickProductButton.setVisibility(View.GONE);
                dialProductsHolder.setVisibility(View.VISIBLE);
            }else{
                dialProductsHolder.setVisibility(View.GONE);
            }


            pickProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        AddStockItemFragment.stock.addProducts(product);
                    }catch (NullPointerException e){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        String date = dateFormat.format(new Date());
                        AddStockItemFragment.stock = new Stock(date);
                        AddStockItemFragment.stock.addProducts(product);
                    }

                    pickProductButton.setVisibility(View.GONE);
                    dialProductsHolder.setVisibility(View.VISIBLE);
                    refreshUI();

                }
            });
            incrementProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddStockItemFragment.stock.addProducts(product);
                    refreshUI();
                }
            });
            deductProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddStockItemFragment.stock.removeProduct(product);
                    if (!AddStockItemFragment.stock.containsProduct(product)) {
                        dialProductsHolder.setVisibility(View.GONE);
                        pickProductButton.setVisibility(View.VISIBLE);
                        if (AddStockItemFragment.stock.getProducts().size()<1){
                            AddStockItemFragment.stock = null;
                        }
                    }
                    refreshUI();
                }
            });
        }

        public void refreshUI() {
            if ( AddStockItemFragment.stock!=null){
                Log.d("stockitems", AddStockItemFragment.stock.toString());
            }

            mAdapter.notifyDataSetChanged();
            AddStockItemFragment.refreshUi();
        }
    }
}
