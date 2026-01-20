package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class UpdateProductUseCase {
    private final ProductRepository repo;
    public UpdateProductUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public void execute(Product product) {
        repo.updateProduct(product);
    }
}
