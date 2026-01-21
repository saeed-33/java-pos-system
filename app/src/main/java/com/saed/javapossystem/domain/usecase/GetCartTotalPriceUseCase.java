package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;

public class GetCartTotalPriceUseCase {
    private final PosRepository repository;

    public GetCartTotalPriceUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public double execute() {
        return  repository.getTotalPrice();
    }
}