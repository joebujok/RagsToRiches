package com.bujok.ragstoriches.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joebu on 06/01/2016.
 */
public class MyDbConnector extends SQLiteOpenHelper {

    private static final String TAG = "MyDbConnector";

    private static final String DATABASE_NAME = "myDB.db";
    private static final int DATABASE_VERSION = 1;

    //Product table creation
    private static final String PRODUCTS_TABLE_NAME = "Products";
    private static final String KEY_PRODUCTID = "ProductID";
    private static final String KEY_PRODUCT = "Product";


    private static final String PRODUCTS_TABLE_CREATE =
            "CREATE TABLE " + PRODUCTS_TABLE_NAME + " (" +
                    KEY_PRODUCTID + " INTEGER PRIMARY KEY, " +
                    KEY_PRODUCT + " INTEGER);";

    //Shop stock level table creation
    private static final String SHOP_STOCK_LEVELS_TABLE_NAME = "ShopStockLevels";
    private static final String KEY_SHOPID = "shopID";
    private static final String KEY_QUANTITYHELD = "QuantityHeld";

    private static final String SHOP_STOCK_LEVELS_TABLE_CREATE =
            "CREATE TABLE " + SHOP_STOCK_LEVELS_TABLE_NAME + " (" +
                    KEY_SHOPID + " INTEGER , " +
                    KEY_PRODUCTID + " INTEGER , " +
                    KEY_QUANTITYHELD + " INTEGER);";




    public MyDbConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SHOP_STOCK_LEVELS_TABLE_CREATE);
       // db.execSQL(PRODUCTS_TABLE_CREATE);
        db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}