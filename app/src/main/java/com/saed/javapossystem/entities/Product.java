package com.saed.javapossystem.entities;

public class Product {
    public Product(int id, String barcode, String name, int qty, double price) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.qty = qty;
        this.price = price;

    }

    final int id;
    final String barcode;
    private String name;
    private int qty;
    private double price;

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public String getName() {
        return name;
    }

    public double getTotalPrice() {
        return qty * price;
    }

}
