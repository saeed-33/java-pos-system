package com.saed.javapossystem.domain.entities;


public class Product {
    final private int id;
    final private String name;
    final private int quantity;
    final private double price;
    final private String code;

    public Product(int id, String name, int quantity, double price, String code) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.code = code;
    }
    public Product(String name, int quantity, double price, String code){
        this.id = -1;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.code = code;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public String getCode() {
        return code;
    }
}
