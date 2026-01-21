package com.saed.javapossystem.data.repository;

import android.content.Context;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;

import java.util.Collections;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    final private ProductDaoSQLite productDaoSQLite;

    public ProductRepositoryImpl(Context context, ProductDaoSQLite productDaoSQLite) {
        this.productDaoSQLite = productDaoSQLite;
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
        return productDaoSQLite.getProduct(code);
    }

    @Override
    public void updateProduct(Product product) {
        productDaoSQLite.updateProduct(product);
    }

    @Override
    public void deleteProduct(int id) {

    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.emptyList();
    }
}
