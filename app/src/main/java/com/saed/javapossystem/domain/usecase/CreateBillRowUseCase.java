package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.repository.BillRowRepository;

public class CreateBillRowUseCase {
    final BillRowRepository repo;
    public CreateBillRowUseCase(BillRowRepository repo){
        this.repo = repo;
    }
    public void execute(BillRow row){
        repo.createBillRow(row);
    }
}
