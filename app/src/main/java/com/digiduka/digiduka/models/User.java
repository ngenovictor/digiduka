package com.digiduka.digiduka.models;

import java.util.Date;

/**
 * Created by waracci on 10/11/17.
 */
public class User {
    private String name;
    private Date date;

    public User(String name) {
        this.name = name;

        this.date = new Date();
    }

    public String getName() {
        return name;
    }


    public Date getDate() {
        return date;
    }



}
