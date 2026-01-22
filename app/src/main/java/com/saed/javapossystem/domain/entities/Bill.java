package com.saed.javapossystem.domain.entities;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bill {

    private int id;
    private String date;
    private double totalPrice;

    public Bill(double totalPrice,String date) {
        this.totalPrice = totalPrice;
        this.date=date;

    }
    public Bill(double totalPrice){
        this.totalPrice = totalPrice;
        this.date=null;

    }

    public Bill(int id, String date, double totalPrice) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


}
