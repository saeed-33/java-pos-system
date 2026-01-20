package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.repository.ProductRepository;

public class DeleteProductUseCase {
    private final ProductRepository repo;
    public DeleteProductUseCase(ProductRepository repo) {
        this.repo = repo;
    }
    public void execute(int id) {
        repo.deleteProduct(id);
    }
}
