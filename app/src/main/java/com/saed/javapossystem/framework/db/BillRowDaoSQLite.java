package com.saed.javapossystem.framework.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.repository.BillRowRepository;

import java.util.ArrayList;
import java.util.List;

public class BillRowDaoSQLite {
    private final SQLiteDatabase db;
    public BillRowDaoSQLite(SQLiteDatabase db) { this.db = db; }

    public void createBillRow(BillRow billRow) {
        ContentValues values = new ContentValues();
        values.put(BillRowContract.COL_BILL_ID, billRow.getBill_id());
        values.put(BillRowContract.COL_PRODUCT_ID, billRow.getProduct_id());
        values.put(BillRowContract.COL_QUANTITY, billRow.getQuantity());
        values.put(BillRowContract.COL_PRICE, billRow.getPrice());
        long res = db.insert(BillRowContract.TABLE_NAME, null, values );

    }

    public List<BillRow> getAllBillRows(int billId) {
        System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
        System.out.println(billId);
        System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");

        List<BillRow> billRows = new ArrayList<>();
        String selection = BillRowContract.COL_BILL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(billId) };

        Cursor cursor = db.query(BillRowContract.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        System.out.println(cursor.moveToFirst());
        if (cursor.moveToFirst()) {
            int i =0;
            do {
                System.out.println(i++);
                BillRow billRow = new BillRow(
                        cursor.getInt(cursor.getColumnIndexOrThrow(BillRowContract.COL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BillRowContract.COL_BILL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BillRowContract.COL_PRODUCT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BillRowContract.COL_QUANTITY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(BillRowContract.COL_PRICE))
                );
                billRows.add(billRow);
            } while (cursor.moveToNext());
        } else {
            // Handle the case where there are no products in the database
            cursor.close();
        }
        cursor.close();
        return billRows;

    }

}
