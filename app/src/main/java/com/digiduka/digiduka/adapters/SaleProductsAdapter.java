package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.ui.AddSaleItemFragment;

import java.util.ArrayList;

/**
 * Created by victor on 10/25/17.
 */

public class SaleProductsAdapter extends RecyclerView.Adapter<SaleProductsAdapter.SaleProductsViewHolder> {
    private Context mContext;
    private TextView stockProductName;
    private TextView stockProductSize;
    private TextView stockProductBuyingPrice;
    private TextView stockProductAmount;
    public SaleProductsAdapter(Context context){
        mContext = context;
    }
    @Override
    public SaleProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisView = LayoutInflater.from(mContext).inflate(R.layout.stock_show_items, parent, false);
        stockProductName = thisView.findViewById(R.id.stockProductName);
        stockProductSize = thisView.findViewById(R.id.stockProductSize);
        stockProductBuyingPrice = thisView.findViewById(R.id.stockProductBuyingPrice);
        stockProductAmount = thisView.findViewById(R.id.stockProductAmount);
        return new SaleProductsViewHolder(thisView);
    }

    @Override
    public void onBindViewHolder(SaleProductsViewHolder holder, int position) {
        holder.bindProduct(AddSaleItemFragment.transaction.getProducts().get(position));
    }

    @Override
    public int getItemCount() {
        if (AddSaleItemFragment.transaction!=null){
            return AddSaleItemFragment.transaction.getProducts().size();
        }else{
            return 0;
        }
    }

    public class SaleProductsViewHolder extends RecyclerView.ViewHolder {
        public SaleProductsViewHolder(View itemView) {
            super(itemView);
        }
        public void bindProduct(Product product){
            stockProductName.setText(product.getNameOfProduct());
            stockProductSize.setText(product.getSize());
            stockProductBuyingPrice.setText("KSH. "+Integer.toString(product.getSellingPrice()));
            stockProductAmount.setText(Integer.toString(product.getAmount()));
        }
    }
}
