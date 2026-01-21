package com.saed.javapossystem.data.repository;

import android.content.Context;

import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.BillRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.framework.db.BillDaoSQLite;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;

import java.util.Collections;
import java.util.List;

public class BillRepositoryImpl implements BillRepository {
    final private BillDaoSQLite billDaoSQLite;

    public BillRepositoryImpl(Context context, BillDaoSQLite billDaoSQLite) {
        this.billDaoSQLite = billDaoSQLite;
    }

    @Override
    public int createBill(Bill bill) {
        return billDaoSQLite.createBill(bill);
    }

    @Override
    public Bill getBill(int id) {
        return null;
    }

    @Override
    public List<Bill> getAllBills() {
        return Collections.emptyList();
    }
}
