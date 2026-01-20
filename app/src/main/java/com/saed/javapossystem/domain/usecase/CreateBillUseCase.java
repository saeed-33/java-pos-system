package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.repository.BillRepository;

public class CreateBillUseCase {
    final BillRepository repo;
    public CreateBillUseCase(BillRepository repo){
        this.repo = repo;
    }
    public void execute(Bill bill){
        repo.createBill(bill);
    }
}
