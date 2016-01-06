package com.bujok.ragstoriches.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joebu on 06/01/2016.
 */
public class MyDbConnector extends SQLiteOpenHelper {

    public MyDbConnector(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.StockTable.CREATE_TABLE);
        db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}