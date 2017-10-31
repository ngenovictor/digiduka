package com.digiduka.digiduka.models;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by victor on 10/11/17.
 */
@Parcel
public class Category {
    private String categoryTitle;
    private String categoryDescription;
    private String categoryId;
    private String dateCreated;

    //private ArrayList<Product> products;

    private String imageEncoded;


    public Category(){}

    public Category(String categoryTitle, String categoryDescription) {
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateCreated = dateFormat.format(new Date());
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

    public String getDateCreated() {
        return dateCreated;
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
