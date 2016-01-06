package com.bujok.ragstoriches.db;

import android.provider.BaseColumns;

/**
 * Created by joebu on 06/01/2016.
 */
public class DBContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DBContract() {}

    public static final String KEY_PRODUCTID = "ProductID";

    /* Inner class that defines the table contents */
    public static abstract class ProductsTable implements BaseColumns {
        public static final String TABLE_NAME = "Products";
        public static final String KEY_PRODUCTID = DBContract.KEY_PRODUCTID;
        public static final String KEY_PRODUCT = "Product";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_PRODUCTID + " INTEGER PRIMARY KEY, " +
                        KEY_PRODUCT + " INTEGER);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class StockTable implements BaseColumns {
        public static final String TABLE_NAME = "ShopStockLevels";
        public static final String KEY_SHOPID = "shopID";
        public static final String KEY_QUANTITYHELD = "QuantityHeld";
        public static final String KEY_PRODUCTID = DBContract.KEY_PRODUCTID;


        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_SHOPID + " INTEGER , " +
                        KEY_PRODUCTID + " INTEGER , " +
                        KEY_QUANTITYHELD + " INTEGER);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
