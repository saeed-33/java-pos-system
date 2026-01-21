package com.saed.javapossystem.data.reposiotyImpl;

import com.saed.javapossystem.data.datasource.local.BillRowDataSource;
import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.repository.BillRowRepository;
import com.saed.javapossystem.framework.db.BillRowDaoSQLite;

import java.util.List;

public class BillRowRepositoryImpl implements BillRowRepository {
    private final BillRowDataSource local;

    public BillRowRepositoryImpl(BillRowDataSource local) {
        this.local = local;
    }

    @Override
    public void createBillRow(BillRow billRow) {
        local.createBillRow(billRow);
    }

    @Override
    public List<BillRow> getAllBillRows(int billId) {
        return local.getAllBillRows(billId);
    }

}
