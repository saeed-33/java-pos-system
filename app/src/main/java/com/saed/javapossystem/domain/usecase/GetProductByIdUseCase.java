package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class GetProductByIdUseCase {
    private final ProductRepository repo;
    public GetProductByIdUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public Product execute(int id) {
        return repo.getProduct(id);
    }
}
