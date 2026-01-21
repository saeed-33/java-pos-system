package com.saed.javapossystem.data.repository;

import android.content.Context;

import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.BillRowRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.framework.db.BillRowDaoSQLite;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;

import java.util.Collections;
import java.util.List;

public class BillRowRepositoryImpl implements BillRowRepository {
    final private BillRowDaoSQLite billRowDaoSQLite;

    public BillRowRepositoryImpl(Context context, BillRowDaoSQLite billRowDaoSQLite) {
        this.billRowDaoSQLite = billRowDaoSQLite;
    }

    @Override
    public void createBillRow(BillRow billRow) {
        billRowDaoSQLite.createBillRow(billRow);
    }

    @Override
    public List<BillRow> getAllBillRows(int billId) {
        return Collections.emptyList();
    }
}
