package com.saed.javapossystem.data.datasource.local;

import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.framework.db.BillRowDaoSQLite;

import java.util.List;

public class BillRowDataSource {
    private final BillRowDaoSQLite dao;

    public BillRowDataSource(BillRowDaoSQLite dao) {
        this.dao = dao;
    }

    public void createBillRow(BillRow billRow) {
        dao.createBillRow(billRow);
    }

    public List<BillRow> getAllBillRows(int billId) {
        return dao.getAllBillRows(billId);
    }
}
