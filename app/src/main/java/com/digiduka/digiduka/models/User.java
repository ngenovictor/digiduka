package com.digiduka.digiduka.models;

/**
 * Created by waracci on 10/11/17.
 */
public class User {
    private String name;
    private String shop;
    private String email;
    private String phone;
    private String image;
    private String gender;

    public User(){};

    public User(String name, String shop, String email, String phone, String image, String gender) {
        this.name = name;
        this.shop = shop;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getShop() {
        return shop;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }
}
