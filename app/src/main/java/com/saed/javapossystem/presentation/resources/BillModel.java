package com.saed.javapossystem.presentation.resources;

import com.saed.javapossystem.domain.entities.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillModel extends Bill {
    boolean isExpanded;
    public BillModel(double totalPrice,String date) {
        super(totalPrice,date);
        this.isExpanded =false;

    }
    public BillModel(int id, double totalPrice,String date) {
        super(id,date,totalPrice);
    }
    public boolean isExpanded() {
        return isExpanded;
    }
    public void toggleExpanded() {
        isExpanded = !isExpanded;
    }

    public static List<BillModel> billModelsFromBills(List<Bill> bills){
        List<BillModel> billModels = new ArrayList<>();
        for(Bill bill : bills){
            billModels.add(new BillModel(bill.getId(),bill.getTotalPrice(),bill.getDate()));
        }
        return billModels;
    }

}
