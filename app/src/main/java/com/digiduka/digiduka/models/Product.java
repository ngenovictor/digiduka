package com.digiduka.digiduka.models;

/**
 * Created by victor on 10/11/17.
 */

public class Product {
    private String nameOfProduct;
    private int buyingPrice;
    private int sellingPrice;
    private String size;
    private String description;
    private String categoryId;
    private String pushId;
    private int amount;

    public Product() {
    }

    public Product(String nameOfProduct, String description, String categoryId, String size, int buyingPrice, int sellingPrice) {
        this.nameOfProduct = nameOfProduct;
        this.description = description;
        this.categoryId = categoryId;
        this.size = size;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.amount = 0;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public String getSize() {
        return size;
    }

    public String getPushId() {
        return pushId;
    }
    public void setProfitAmount(Product product1,Product product2){
        amount= product1.getAmount()+product2.getAmount();
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
