package com.saed.javapossystem.domain.repository;

import com.saed.javapossystem.domain.entities.Product;

import java.util.List;

public interface ProductRepository {
    void createProduct(Product product);
    Product getProduct(int id);
    Product getProduct(String code);
    void updateProduct(Product product);
    void deleteProduct(int id);
    List<Product> getAllProducts();

}
