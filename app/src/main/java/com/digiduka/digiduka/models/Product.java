package com.digiduka.digiduka.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by victor on 10/11/17.
 */

public class Product {
    private String nameOfProduct;
    private Integer price;
    private String description;
    private String categoryId;
    ArrayList<Item> variation;

    public Product(){};

    public Product(String nameOfProduct, Integer price, String description, String categoryId, ArrayList<Item> variation) {
        this.nameOfProduct = nameOfProduct;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.variation = variation;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public ArrayList<Item> getVariation() {
        return variation;
    }
}

 class Item{
    String quantity;
    Integer price;

     public Item(String quantity, Integer price) {
         this.quantity = quantity;
         this.price = price;
     }

     public String getQuantity() {
         return quantity;
     }

     public Integer getPrice() {
         return price;
     }
 }
