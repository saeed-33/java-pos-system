package com.saed.javapossystem.framework.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saed.javapossystem.domain.entities.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDaoSQLite {
    private final SQLiteDatabase db;

    public BillDaoSQLite(SQLiteDatabase db) {
        this.db = db;
    }

    public int createBill(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(BillContract.COL_TOTAL_PRICE, bill.getTotalPrice());
        long id = db.insert(BillContract.TABLE_NAME, null, values);
        return (int) id;
    }

    public Bill getBill(int id) {
        String selection = BillContract.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(BillContract.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            Bill bill = new Bill(
                    cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BillContract.COL_DATE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(BillContract.COL_TOTAL_PRICE))
            );
            cursor.close();
            return bill;
        } else {
            return null;
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        Cursor cursor = db.query(BillContract.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill(
                        cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BillContract.COL_DATE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(BillContract.COL_TOTAL_PRICE))
                );
                bills.add(bill);
            } while (cursor.moveToNext());
        } else {
            // Handle the case where there are no products in the database
            cursor.close();
        }
        cursor.close();
        return bills;
    }

}
