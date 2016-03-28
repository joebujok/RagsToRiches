package com.bujok.ragstoriches.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by joebu on 06/01/2016.
 */
public class MyDbConnector extends SQLiteOpenHelper {


    private static final String TAG = "MyDbConnector";

    public MyDbConnector(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.StockTable.CREATE_TABLE);
        db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
        createDefaultDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createDefaultDatabase(SQLiteDatabase db) {
        //db = new MyDbConnector().getWritableDatabase();
        //db.execSQL(DBContract.ProductsTable.DELETE_TABLE);
        //db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
        //db.execSQL(DBContract.StockTable.DELETE_TABLE);
        //db.execSQL(DBContract.StockTable.CREATE_TABLE);
        String count = "SELECT count(*) FROM " + DBContract.StockTable.TABLE_NAME;
        Cursor c = db.rawQuery(count, null);
        c.moveToFirst();
        int icount = c.getInt(0);
        if (icount == 0) {
            long newRowId;
            ContentValues values = new ContentValues();
            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Twix Bar");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME, null, values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME, null, values);
            values.clear();

            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Mars Bar");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME, null, values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME, null, values);
            values.clear();

            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Freddo");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME, null, values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME, null, values);
            values.clear();
        }
    }
}