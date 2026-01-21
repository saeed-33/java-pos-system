package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.repository.PosRepository;

public class ChangeQtyOfProductUseCase {
    private final PosRepository repository;

    public ChangeQtyOfProductUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public void execute(int id, int newQty) {
        boolean success = repository.changeQtyOfProduct(id, newQty);
        if (!success) {
            throw new RuntimeException("Product not found");
        }
    }
}
