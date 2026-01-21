package com.saed.javapossystem.data.datasource.local;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;

import java.util.List;

public class ProductLocalDataSource {
    private final ProductDaoSQLite dao;
    public ProductLocalDataSource(ProductDaoSQLite dao){
        this.dao = dao;
    }
    public void createProduct(Product product){
        dao.createProduct(product);
    }

    public Product getProduct(int id){
        return dao.getProduct(id);
    }

    public Product getProduct(String code){
        return dao.getProduct(code);
    }

    public void updateProduct(Product product){
        dao.updateProduct(product);
    }

    public void deleteProduct(int id){
        dao.deleteProduct(id);
    }

    public List<Product> getAllProducts(){
        return dao.getAllProducts();
    }

}
