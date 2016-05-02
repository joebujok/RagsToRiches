package com.bujok.ragstoriches.buildings;


import com.bujok.ragstoriches.NativeFunctions.DBContract;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.items.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buje on 02/05/2016.
 */
public class Shop extends Building  {
    private int shopID;
    private String shopName;

    public Shop(RagsGame game, int shopID) {
        super(game);
        this.shopID = shopID;
    }


    public List<StockItem> getShopItems(){

        List<StockItem> stockItems = new ArrayList<StockItem>();
        Database.Result result =  database.query("Select * FROM " + DBContract.StockTable.TABLE_NAME +
                " INNER JOIN " + DBContract.ProductsTable.TABLE_NAME + " ON "
                + DBContract.ProductsTable.TABLE_NAME + "." + DBContract.ProductsTable.KEY_PRODUCTID + " = "
                + DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID + " WHERE "
                + DBContract.StockTable.KEY_SHOPID + " = " + shopID);
        while(result.moveToNext()){
            StockItem s = new StockItem(result.getInt(result.getColumnIndex(DBContract.StockTable.KEY_PRODUCTID)),
                    result.getString(result.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT)),
                    result.getInt(result.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD)));
            stockItems.add(s);

        }

        return stockItems;

    }
}
