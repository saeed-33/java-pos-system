package com.saed.javapossystem.data.reposiotyImpl;

import com.saed.javapossystem.data.datasource.local.ProductLocalDataSource;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    final ProductLocalDataSource local;
    public ProductRepositoryImpl(ProductLocalDataSource local) {
        this.local = local;
    }

    @Override
    public void createProduct(Product product) {
        local.createProduct(product);
    }

    @Override
    public Product getProduct(int id) {
        return local.getProduct(id);
    }

    @Override
    public Product getProduct(String code) {
        return local.getProduct(code);
    }

    @Override
    public void updateProduct(Product product) {
        local.updateProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        local.deleteProduct(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return local.getAllProducts();
    }
}
