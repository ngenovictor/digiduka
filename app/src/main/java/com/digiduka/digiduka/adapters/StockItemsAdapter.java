package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.ui.AddStockItemFragment;

/**
 * Created by victor on 10/19/17.
 */

public class StockItemsAdapter extends RecyclerView.Adapter<StockItemsAdapter.StockItemsViewHolder> {
    private Context mContext;
    private TextView stockProductName;
    private TextView stockProductSize;
    private TextView stockProductBuyingPrice;
    private TextView stockProductAmount;
    public StockItemsAdapter(Context context){
        mContext = context;
    }
    @Override
    public StockItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_show_items, parent, false);
        stockProductName = view.findViewById(R.id.stockProductName);
        stockProductSize = view.findViewById(R.id.stockProductSize);
        stockProductBuyingPrice = view.findViewById(R.id.stockProductBuyingPrice);
        stockProductAmount = view.findViewById(R.id.stockProductAmount);
        return new StockItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockItemsViewHolder holder, int position) {
        holder.bindProduct(AddStockItemFragment.stock.getProducts().get(position));
    }

    @Override
    public int getItemCount() {
        if (AddStockItemFragment.stock!=null){
            return AddStockItemFragment.stock.getProducts().size();
        }else{
            return 0;
        }
    }

    public class StockItemsViewHolder extends RecyclerView.ViewHolder{

        public StockItemsViewHolder(View itemView) {
            super(itemView);
        }
        public void bindProduct(Product product){
            stockProductName.setText(product.getNameOfProduct());
            stockProductSize.setText(product.getSize());
            stockProductBuyingPrice.setText("KSH. "+Integer.toString(product.getBuyingPrice()));
            stockProductAmount.setText(Integer.toString(product.getAmount()));
        }
    }
}
