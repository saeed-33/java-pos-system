package com.saed.javapossystem.data.datasource.local;

import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.framework.db.BillDaoSQLite;

import java.util.List;

public class BillLocalDataSource {
    private final BillDaoSQLite dao;
    public BillLocalDataSource(BillDaoSQLite dao){
        this.dao = dao;
    }

    public int createBill(Bill bill){
        return dao.createBill(bill);
    }

    public Bill getBill(int id){
        return dao.getBill(id);
    }

    public List<Bill> getAllBills(){
        return dao.getAllBills();
    }



}
