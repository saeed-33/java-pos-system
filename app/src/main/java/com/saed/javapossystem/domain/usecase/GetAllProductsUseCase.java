package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

import java.util.List;

public class GetAllProductsUseCase {
    private final ProductRepository repo;
    public GetAllProductsUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public List<Product> execute() {
        return repo.getAllProducts();
    }
}
