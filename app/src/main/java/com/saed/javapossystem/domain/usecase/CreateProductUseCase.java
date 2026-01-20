package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class CreateProductUseCase {
    private final ProductRepository repo;
    public CreateProductUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public void execute(Product product) {
        repo.createProduct(product);
    }
}
