package com.saed.javapossystem.framework.db;

public class BillRowContract {
    public static final String TABLE_NAME = "bill_rows";
    public static final String COL_ID = "id";
    public static final String COL_BILL_ID = "bill_id";

    public static final String COL_PRODUCT_ID = "product_id";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_PRODUCT_ID + " INTEGER NOT NULL, " +
                    COL_QUANTITY + " INTEGER NOT NULL, " +
                    COL_PRICE + " NUMBER NOT NULL, " +
                    COL_BILL_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + COL_BILL_ID + ") REFERENCES " + BillContract.TABLE_NAME + "(" + BillContract.COL_ID + "), " +
                    "FOREIGN KEY(" + COL_PRODUCT_ID + ") REFERENCES " +ProductContract.TABLE_NAME + "(" + ProductContract.COL_ID + ") "+
                    ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


}
