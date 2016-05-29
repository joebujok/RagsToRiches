package com.bujok.ragstoriches.NativeFunctions;

/**
 * Created by Tojoh on 5/29/2016.
 */
public abstract class ProductsTable implements DBContract.BaseColumns
{
    public static final String TABLE_NAME = "Products";
    public static final String KEY_PRODUCTID = DBContract.KEY_PRODUCTID;
    public static final String KEY_PRODUCT = "Product";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_PRODUCTID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    KEY_PRODUCT + " INTEGER);";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
