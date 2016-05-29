package com.bujok.ragstoriches.NativeFunctions;

/**
 * Created by Tojoh on 5/29/2016.
 */
public abstract class StockTable implements DBContract.BaseColumns
{
    public static final String TABLE_NAME = "ShopStockLevels";
    public static final String KEY_SHOPID = "shopID";
    public static final String KEY_QUANTITYHELD = "QuantityHeld";
    public static final String KEY_PRODUCTID = DBContract.KEY_PRODUCTID;
    public static final String KEY_PRICE = "price";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_SHOPID + " INTEGER , " +
                    KEY_PRODUCTID + " INTEGER , " +
                    KEY_QUANTITYHELD + " INTEGER,"+
                    KEY_PRICE + " FLOAT);";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
