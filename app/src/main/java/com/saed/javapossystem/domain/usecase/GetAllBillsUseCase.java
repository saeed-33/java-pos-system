package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.repository.BillRepository;

import java.util.List;

public class GetAllBillsUseCase {
    private final BillRepository repo;
    public GetAllBillsUseCase(BillRepository repo) {
        this.repo = repo;
    }
    public List<Bill> execute(){
        return repo.getAllBills();
    }
}
