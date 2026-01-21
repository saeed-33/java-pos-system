package com.saed.javapossystem.domain.repository;

import com.saed.javapossystem.domain.entities.Product;

import java.util.List;

public interface PosRepository {
    void addProduct(Product product);

    void removeProduct(Product barcode);

    List<Product> getAllProducts();

    void clearCart();

    double getTotalPrice();

    int getTotalQty();

    boolean changeQtyOfProduct(int id, int newQty);

}