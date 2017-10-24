package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Stock;

import java.util.ArrayList;

/**
 * Created by victor on 10/23/17.
 */

public class StocksListViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    private Context mContext;
    private TextView stockId;
    private TextView stockDate;
    private TextView totalCost;
    private RecyclerView stockProductsRecyclerView;
    private Button revealStockProducts;
    private ConstraintLayout productsHolder;
    public StocksListViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        stockDate = itemView.findViewById(R.id.stockDate);
        totalCost = itemView.findViewById(R.id.totalCost);
        stockProductsRecyclerView = itemView.findViewById(R.id.stockProductsRecyclerView);
        productsHolder = itemView.findViewById(R.id.productsHolder);
        productsHolder.setVisibility(View.GONE);
        revealStockProducts = itemView.findViewById(R.id.revealStockProducts);
        revealStockProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (revealStockProducts.getText().equals("Close")){
                    productsHolder.setVisibility(View.GONE);
                    revealStockProducts.setText("Show Products");
                }else{
                    productsHolder.setVisibility(View.VISIBLE);
                    revealStockProducts.setText("Close");
                }

            }
        });
    }
    public void bindStock(Stock stock){
        stockId = mView.findViewById(R.id.stockId);
        stockId.setText(stock.getStockId());
        stockDate.setText(stock.getDateCreated());
        totalCost.setText("KSH. "+stock.getTotalCost());
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(stock.getProducts());
        StockProductsListAdapter adapter = new StockProductsListAdapter(mContext, products);
        stockProductsRecyclerView.setHasFixedSize(true);
        stockProductsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        stockProductsRecyclerView.setAdapter(adapter);



    }
}
