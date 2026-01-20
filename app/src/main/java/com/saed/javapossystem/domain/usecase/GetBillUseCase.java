package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.repository.BillRepository;

public class GetBillUseCase {
    private final BillRepository repo;
    public GetBillUseCase(BillRepository repo) {
        this.repo = repo;
    }
    public Bill execute(int id) {
        return repo.getBill(id);
    }
}
