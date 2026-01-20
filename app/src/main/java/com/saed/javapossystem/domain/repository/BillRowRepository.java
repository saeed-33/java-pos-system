package com.saed.javapossystem.domain.repository;

import com.saed.javapossystem.domain.entities.BillRow;

import java.util.List;

public interface BillRowRepository {
    void createBillRow(BillRow billRow);
    List<BillRow> getAllBillRows(int billId);
}
