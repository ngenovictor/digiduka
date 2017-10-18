package com.digiduka.digiduka.models;

/**
 * Created by victor on 10/17/17.
 */

public class Variation{
    String quantity;
    Integer price;

    public Variation(){}
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