package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        stockDate.setText(getDisplayDate(stock.getDateCreated()));
        totalCost.setText("KSH. "+stock.getTotalCost());
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(stock.getProducts());
        StockProductsListAdapter adapter = new StockProductsListAdapter(mContext, products);
        stockProductsRecyclerView.setHasFixedSize(true);
        stockProductsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        stockProductsRecyclerView.setAdapter(adapter);
    }
    public String getDisplayDate(String dateCreated){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        try{
            Date date = dateFormat.parse(dateCreated);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String dayOfWeek = "";
            String month = "";


            switch (calendar.get(Calendar.DAY_OF_WEEK)){
                case Calendar.SUNDAY:
                    dayOfWeek = "Sunday";
                    break;
                case Calendar.MONDAY:
                    dayOfWeek = "Monday";
                    break;
                case Calendar.TUESDAY:
                    dayOfWeek = "Tuesday";
                    break;
                case Calendar.WEDNESDAY:
                    dayOfWeek = "Wednesday";
                    break;
                case Calendar.THURSDAY:
                    dayOfWeek = "Thursday";
                    break;
                case Calendar.FRIDAY:
                    dayOfWeek = "Friday";
                    break;
                case Calendar.SATURDAY:
                    dayOfWeek = "Saturday";
                    break;
            }
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    month = "January";
                    break;
                case Calendar.FEBRUARY:
                    month = "February";
                    break;
                case Calendar.MARCH:
                    month = "March";
                    break;
                case Calendar.APRIL:
                    month = "April";
                    break;
                case Calendar.MAY:
                    month = "May";
                    break;
                case Calendar.JUNE:
                    month = "June";
                    break;
                case Calendar.JULY:
                    month = "July";
                    break;
                case Calendar.AUGUST:
                    month = "August";
                    break;
                case Calendar.SEPTEMBER:
                    month = "September";
                    break;
                case Calendar.OCTOBER:
                    month = "October";
                    break;
                case Calendar.NOVEMBER:
                    month = "November";
                    break;
                case Calendar.DECEMBER:
                    month = "December";
                    break;
            }
            //Wednesday, 20 December 2017
            String showDate = dayOfWeek+", "+Integer.toString(calendar.get(Calendar.DAY_OF_MONTH))+" "+month+" "+Integer.toString(calendar.get(Calendar.YEAR));
            return showDate;
        }catch (ParseException e){
            return dateCreated;
        }
    }
}
