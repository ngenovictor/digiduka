package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;

import java.util.ArrayList;

/**
 * Created by victor on 10/24/17.
 */

public class StockProductsListAdapter extends RecyclerView.Adapter<StockProductsListAdapter.StockProductsListViewHolder> {
    private Context mContext;
    private ArrayList<Product> mProducts;
    public StockProductsListAdapter(Context context,ArrayList<Product> products){
        mContext = context;
        mProducts = products;
    }
    @Override
    public StockProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_product_list, parent, false);
        StockProductsListViewHolder holder = new StockProductsListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StockProductsListViewHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class StockProductsListViewHolder extends RecyclerView.ViewHolder{
        private TextView productName;
        private TextView buyingPrice;
        private TextView productAmount;

        public StockProductsListViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            buyingPrice = itemView.findViewById(R.id.buyingPrice);
            productAmount = itemView.findViewById(R.id.productAmount);

        }
        public void bindProduct(Product product){
            productName.setText(product.getNameOfProduct());
            buyingPrice.setText("KSH. "+Integer.toString(product.getBuyingPrice()));
            productAmount.setText(Integer.toString(product.getAmount()));
        }
    }
}
