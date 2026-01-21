package com.saed.javapossystem.domain.entities;

public class BillRow {
    private int id;
    private int bill_id;
    private int product_id;
    private int quantity;
    private double price;

    public BillRow(int id, int bill_id, int product_id, int quantity, double price) {
        this.id = id;
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }


    public BillRow(int bill_id, int product_id, int quantity, double price) {
        this.id = -1;
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public int getBill_id() {
        return bill_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
}
