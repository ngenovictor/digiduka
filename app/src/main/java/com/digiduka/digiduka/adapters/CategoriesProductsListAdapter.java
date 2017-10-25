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
import com.digiduka.digiduka.models.Transaction;
import com.digiduka.digiduka.ui.AddSaleItemFragment;
import com.digiduka.digiduka.ui.AddStockItemFragment;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
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
    private SaleProductsAdapter salesAdapter;
    private String mSource;

    //constructor from the stock side
    public CategoriesProductsListAdapter(Category category, Context context, ArrayList<Product> products, StockItemsAdapter adapter, String source) {
        this.category = category;
        mContext = context;
        mAdapter = adapter;
        this.products.addAll(products);
        mSource = source;
    }

    //constructor from the sales side
    public CategoriesProductsListAdapter(Category category, Context context, ArrayList<Product> products, SaleProductsAdapter adapter, String source) {
        this.category = category;
        mContext = context;
        salesAdapter = adapter;
        this.products.addAll(products);
        mSource = source;
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

            if (!(AddStockItemFragment.stock==null) && AddStockItemFragment.stock.containsProduct(product)||
                    !(AddSaleItemFragment.transaction==null) && AddSaleItemFragment.transaction.containsProduct(product)){
                pickProductButton.setVisibility(View.GONE);
                dialProductsHolder.setVisibility(View.VISIBLE);
            }else{
                dialProductsHolder.setVisibility(View.GONE);
            }


            pickProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSource.equals(Constants.STOCK_SIDE)){
                        try{
                            AddStockItemFragment.stock.addProducts(product);
                        }catch (NullPointerException e){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                            String date = dateFormat.format(new Date());
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AddStockItemFragment.stock = new Stock(date, user.getUid());
                            AddStockItemFragment.stock.addProducts(product);
                        }

                        pickProductButton.setVisibility(View.GONE);
                        dialProductsHolder.setVisibility(View.VISIBLE);
                    }else if(mSource.equals(Constants.SALES_SIDE)){
                        try{
                            AddSaleItemFragment.transaction.addProducts(product);
                        }catch (NullPointerException e){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                            String date = dateFormat.format(new Date());
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AddSaleItemFragment.transaction = new Transaction(date, user.getUid());
                            AddSaleItemFragment.transaction.addProducts(product);
                        }

                        pickProductButton.setVisibility(View.GONE);
                        dialProductsHolder.setVisibility(View.VISIBLE);
                    }
                    refreshUI();
                }
            });
            incrementProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSource.equals(Constants.STOCK_SIDE)){
                        AddStockItemFragment.stock.addProducts(product);
                    }else if(mSource.equals(Constants.SALES_SIDE)){
                        AddSaleItemFragment.transaction.addProducts(product);
                    }

                    refreshUI();
                }
            });
            deductProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSource.equals(Constants.STOCK_SIDE)){
                        AddStockItemFragment.stock.removeProduct(product);
                        if (!AddStockItemFragment.stock.containsProduct(product)) {
                            dialProductsHolder.setVisibility(View.GONE);
                            pickProductButton.setVisibility(View.VISIBLE);
                            if (AddStockItemFragment.stock.getProducts().size()<1){
                                AddStockItemFragment.stock = null;
                            }
                        }
                    }else if(mSource.equals(Constants.SALES_SIDE)){
                        AddSaleItemFragment.transaction.removeProduct(product);
                        if (!AddSaleItemFragment.transaction.containsProduct(product)) {
                            dialProductsHolder.setVisibility(View.GONE);
                            pickProductButton.setVisibility(View.VISIBLE);
                            if (AddSaleItemFragment.transaction.getProducts().size()<1){
                                AddSaleItemFragment.transaction = null;
                            }
                        }
                    }

                    refreshUI();
                }
            });
        }

        public void refreshUI() {
            if(mSource.equals(Constants.STOCK_SIDE)){
                mAdapter.notifyDataSetChanged();
                AddStockItemFragment.refreshUi();

            }else if(mSource.equals(Constants.SALES_SIDE)){
                salesAdapter.notifyDataSetChanged();
                AddSaleItemFragment.refreshUi();
            }
        }
    }
}
