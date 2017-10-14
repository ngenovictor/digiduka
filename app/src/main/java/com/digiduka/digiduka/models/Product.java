package com.digiduka.digiduka.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 10/11/17.
 */

public class Product {
    private String nameOfProduct;
    private Integer price;
    private String description;
    private String categoryId;
    List<Variation> variations;
    private String pushId;

    public Product(){};

    public Product(String nameOfProduct, String description, String categoryId) {
        this.nameOfProduct = nameOfProduct;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        variations = new ArrayList<>();
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

    public List<Variation> getVariation() {
        return variations;
    }

    public void addVariations(String quantity,Integer price) {
        Variation variation = new Variation(quantity, price);
        this.variations.add(variation);
    }

}
class Variation{
    String quantity;
    Integer price;

    public Variation(String quantity, Integer price) {
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
