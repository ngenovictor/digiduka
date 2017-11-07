package com.digiduka.digiduka.models;

import java.util.Date;

/**
 * Created by victor on 10/11/17.
 */

public class Shop {
    //name, location, Date,

    private String name;
    private long longitude;
    private long latitude;
    private Date date;

    public Shop(String name, long longitude, long latitude, Date date) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = getDate();
    }

    public String getName() {
        return name;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public Date getDate() {
        return date;
    }
}
