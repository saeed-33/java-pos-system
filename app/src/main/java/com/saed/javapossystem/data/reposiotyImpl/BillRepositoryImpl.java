package com.saed.javapossystem.data.reposiotyImpl;

import com.saed.javapossystem.data.datasource.local.BillLocalDataSource;
import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.repository.BillRepository;

import java.util.List;

public class BillRepositoryImpl implements BillRepository {
    private final BillLocalDataSource local;

    public BillRepositoryImpl(BillLocalDataSource local) {
        this.local = local;
    }

    @Override
    public int createBill(Bill bill) {
        return local.createBill(bill);
    }

    @Override
    public Bill getBill(int id) {
        return local.getBill(id);
    }

    @Override
    public List<Bill> getAllBills() {
        return local.getAllBills();
    }
}
