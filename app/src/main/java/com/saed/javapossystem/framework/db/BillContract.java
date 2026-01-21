package com.saed.javapossystem.framework.db;

public class BillContract {
    public static final String TABLE_NAME = "products";
    public static final String COL_ID = "id";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_DATE = "date";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TOTAL_PRICE + "NUMBER," +
                    COL_DATE + "TEXT DEFAULT (datetime('now'))" +
                    ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
