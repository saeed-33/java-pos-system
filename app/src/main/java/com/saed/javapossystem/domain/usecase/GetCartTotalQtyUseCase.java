package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;

public class GetCartTotalQtyUseCase {
    private final PosRepository repository;

    public GetCartTotalQtyUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public int execute() {
        return  repository.getTotalQty();
    }
}