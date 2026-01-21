package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.repository.PosRepository;

public class ClearCartUseCase {
    private final PosRepository repository;

    public ClearCartUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.clearCart();
    }
}
