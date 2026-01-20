package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.repository.BillRowRepository;

import java.util.List;

public class GetBillRowsUseCase {
    final BillRowRepository repo;
    public GetBillRowsUseCase(BillRowRepository repo){
        this.repo = repo;
    }
    public List<BillRow> execute(int id){
        return repo.getAllBillRows(id);
    }

}
