package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class GetProductByCodeUseCase {
    private final ProductRepository repo;
    public GetProductByCodeUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public Product execute(String code) {
        return repo.getProduct(code);
    }
}
