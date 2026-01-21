package com.saed.javapossystem.data.repository;

import android.content.Context;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    public ProductRepositoryImpl(Context context) {
    }

    @Override
    public void createProduct(Product product) {

    }

    @Override
    public Product getProduct(int id) {
        return null;
    }

    @Override
    public Product getProduct(String code) {
        return new Product(2, "444", "ddd", 1, 500);
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int id) {

    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.emptyList();
    }
}
