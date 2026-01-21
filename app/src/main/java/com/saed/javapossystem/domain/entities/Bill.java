package com.saed.javapossystem.domain.entities;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bill {

    private int id;
    private Date date;
    private double totalPrice;

    public Bill(double totalPrice){
        this.totalPrice = totalPrice;
        this.date=null;

    }

    public Bill(int id, String date, double totalPrice) {
        this.id = id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try{
            this.date = sdf.parse(date);
        }catch (ParseException e){
            this.date = new Date();
        }
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


}
