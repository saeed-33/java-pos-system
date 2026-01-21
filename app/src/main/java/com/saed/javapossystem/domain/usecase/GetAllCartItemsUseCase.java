package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;

import java.util.List;

public class GetAllCartItemsUseCase {
    final private PosRepository repository;

    public GetAllCartItemsUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public List<Product> execute() {
        return repository.getAllProducts();
    }
}
