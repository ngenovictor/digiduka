package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Stock;

/**
 * Created by victor on 10/23/17.
 */

public class StocksListViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    private Context mContext;
    private TextView stockId;
    public StocksListViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }
    public void bindStock(Stock stock){
        stockId = mView.findViewById(R.id.stockId);
        stockId.setText(stock.getStockId());
    }
}
