package com.digiduka.digiduka.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mosoti on 10/11/17.
 */

public class Transactions {
  private  Date date;
   private  Integer total;
   private  String uid;


    ArrayList<Product> products=new ArrayList<>();

    public Transactions(){};

    public Transactions(Date date,Integer total,String uid,ArrayList<Product> products){
        this.date=new Date();
        this.uid=uid;
        this.products=products;
    }

    public Date getDate() {
        return date;
    }

    public Integer getTotal() {

        return total;
    }

    public void setTotal(Integer total) {
         total=0;
        for (Product product:products) {
            for(Item item:product.getVariation()) {
                total+=item.getPrice();
            }
        }
        this.total = total;
    }

    public String getUid() {


        return uid;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }



}
