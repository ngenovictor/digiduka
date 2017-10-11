package com.digiduka.digiduka.models;

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
    Map<String, Integer> variation = new HashMap<>();


    public Product(){}

    public Product(String nameOfProduct, Integer price, String description, String categoryId, Map<String, Integer> variation) {
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

    public String getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getVariation() {
        return variation;
    }
}
