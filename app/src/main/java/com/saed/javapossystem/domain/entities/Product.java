package com.saed.javapossystem.domain.entities;

import java.util.Objects;

public class Product {
    final private int id;
    private String name;
    private int quantity;
    private int selldQuantity;
    private double price;
    private String code;

    public Product(int id, String barcode, String name, int qty, double price) {
        this.id = id;
        this.code = barcode;
        this.name = name;
        this.quantity = qty;
        this.selldQuantity = 0;
        this.price = price;

    }

    public Product(int id, String barcode, String name, int qty, double price, int selldQuantity) {
        this.id = id;
        this.code = barcode;
        this.name = name;
        this.quantity = qty;
        this.selldQuantity = selldQuantity;
        this.price = price;

    }


    public double getPrice() {
        return price;
    }

    public int getQty() {
        return quantity - selldQuantity;
    }

    public int getSelldQuantity() {
        return selldQuantity;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return code;
    }

    public int getId() {
        return id;
    }


    public double getTotalPrice() {
        return selldQuantity * price;
    }

    public void setQty(int i) {

        selldQuantity = i;
    }

    @Override
    public boolean equals(Object o) {
        // 1. Check if it's the exact same memory reference
        if (this == o) return true;

        // 2. Check if the other object is null or a different class
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Cast the object to Product
        Product product = (Product) o;

        // 4. Compare the unique identifier (barcode)
        boolean barcodeCond = Objects.equals(code, product.getBarcode());
        boolean idCond = Objects.equals(id, product.getId());
        boolean nameCond = Objects.equals(name, product.getName());
        return barcodeCond && idCond && nameCond;
    }

    public Product copyWith(int selldQuantity) {
        return new Product(id, code, name, quantity, price, selldQuantity);
    }


}
