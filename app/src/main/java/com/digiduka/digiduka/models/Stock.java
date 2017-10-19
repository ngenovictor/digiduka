package com.digiduka.digiduka.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by victor on 10/11/17.
 */

public class Stock {
    private String stockId;
    private String dateCreated;
    private ArrayList<Product> products = new ArrayList<>();
    private int totalCost;

    public Stock(){}

    public Stock(String dateCreated) {
        this.dateCreated = dateCreated;
        this.products = products;
        totalCost = 0;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public int getTotalCost() {
        return totalCost;
    }
    public void addProducts(Product product){
        this.products.add(product);
        totalCost+=product.getBuyingPrice();
    }

    public void removeProduct(Product product){
        for(Product product1:products){
            if (product1.getPushId().equals(product.getPushId())){
                products.remove(products.indexOf(product1));
                totalCost-=product.getBuyingPrice();
                return;
            }
        }
    }
    public boolean containsProduct(Product product){
        for (Product product1:products){
            if (product1.getPushId().equals(product.getPushId())){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
