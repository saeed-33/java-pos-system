package com.saed.javapossystem.domain.repository;

import com.saed.javapossystem.domain.entities.Bill;

import java.util.List;

public interface BillRepository {
    int createBill(Bill bill);
    Bill getBill(int id);
    List<Bill> getAllBills();
}