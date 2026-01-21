package com.saed.javapossystem.framework.db;

public class ProductContract {
    public static final String TABLE_NAME = "products";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";
    public static final String COL_CODE = "code";
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT NOT NULL," +
                    COL_QUANTITY + " INTEGER," +
                    COL_PRICE + " NUMBER," +
                    COL_CODE + " TEXT UNIQUE" +
                    ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
