package com.saed.javapossystem.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE = "pos_java.db";
    // Database Version
    static final int DB_VERSION = 5;
    public DBHelper(Context context) {
        super(context, DATABASE, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ProductContract.CREATE_TABLE);
        sqLiteDatabase.execSQL(BillContract.CREATE_TABLE);
        sqLiteDatabase.execSQL(BillRowContract.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(ProductContract.DROP_TABLE);
        sqLiteDatabase.execSQL(BillContract.DROP_TABLE);
        sqLiteDatabase.execSQL(BillRowContract.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
