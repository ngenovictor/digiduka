package com.digiduka.digiduka.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by victor on 10/11/17.
 */
@Parcel
public class Category {
    private String categoryTitle;
    private String categoryDescription;
    private String categoryId;
    private String shopId;
    private Date date;
    private String imageEncoded;

    public Category(){}

    public Category(String categoryTitle, String categoryDescription) {
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        date = new Date();
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getShopId() {
        return shopId;
    }

    public Date getDate() {
        return date;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setImageEncoded(String imageEncoded) {
        this.imageEncoded = imageEncoded;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }
}
