package com.saed.javapossystem.framework.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saed.javapossystem.domain.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoSQLite {
    private final SQLiteDatabase db;
    public ProductDaoSQLite(SQLiteDatabase db) { this.db = db; }

    public void createProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.COL_NAME, product.getName());
        values.put(ProductContract.COL_QUANTITY, product.getQuantity());
        values.put(ProductContract.COL_PRICE, product.getPrice());
        values.put(ProductContract.COL_CODE,product.getCode());
        db.insert(ProductContract.TABLE_NAME, null, values );

    }

    public Product getProduct(int id) {

        String selection = ProductContract.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(ProductContract.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()){
            Product product = new Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_QUANTITY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ProductContract.COL_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_CODE))
            );
            cursor.close();
            return product;
        }else{
            return null;
        }
    }

    public Product getProduct(String code) {
        String selection = ProductContract.COL_CODE + " = ?";
        String[] selectionArgs = { code };
        Cursor cursor = db.query(ProductContract.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()){
            Product product = new Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_QUANTITY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ProductContract.COL_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_CODE))
            );
            cursor.close();
            return product;
        }else{
            return null;
        }
    }

    public void updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.COL_NAME, product.getName());
        values.put(ProductContract.COL_QUANTITY, product.getQuantity());
        values.put(ProductContract.COL_PRICE, product.getPrice());
        values.put(ProductContract.COL_CODE,product.getCode());
        db.update(ProductContract.TABLE_NAME, values, ProductContract.COL_ID + " = ?", new String[]{String.valueOf(product.getId())});
    }


    public void deleteProduct(int id){
        db.delete(ProductContract.TABLE_NAME, ProductContract.COL_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = db.query(ProductContract.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.COL_QUANTITY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(ProductContract.COL_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.COL_CODE))
                );
                products.add(product);
            } while (cursor.moveToNext());
        } else {
            // Handle the case where there are no products in the database
            cursor.close();
        }
        cursor.close();
        return products;
    }




}
