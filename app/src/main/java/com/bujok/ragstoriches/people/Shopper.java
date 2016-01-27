package com.bujok.ragstoriches.people;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;

import com.bujok.ragstoriches.R;
import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.DatabaseChangedReceiver;
import com.bujok.ragstoriches.db.MyDbConnector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bujok.ragstoriches.utils.Random.getRandInteger;


/**
 * Created by joebu on 07/01/2016.
 */
public class Shopper extends Person {

    private static final String TAG = "ShopperClass";

    //money in pence
    private Integer mMoney;
    private Boolean mCanAffordShop;

    public Shopper(Context context, String name, Bitmap bitmap, int x, int y) {
        super(context, name, bitmap, x , y);
        Random rand = new Random();
        Integer pounds = rand.nextInt((95 - 15) + 1) + 15;
        Integer pence = rand.nextInt((99 - 0) + 1) + 0;
        this.mMoney = (pounds * 100) + pence;
        this.mCanAffordShop = true;

    }

    public Integer getMoney() {
        return mMoney;
    }

    public void setMoney(Integer mMoney) {
        this.mMoney = mMoney;
    }
    public String getMoneyString(){
        String str = mMoney.toString();
        if(str.length() > 2){
            String pence = str.substring(str.length() - 2);
            String pounds = str.substring(0,str.length() - 2);
            str = pounds + "."+ pence;
        }

        return str;
    }
    // Returns true if shopper want to stay in shop
    public boolean simShopperActions(){
        buyItemInShop(1);
        return mCanAffordShop;
    }

    private void buyItemInShop(int ShopID) {
        String[] projection = {
                DBContract.StockTable.KEY_SHOPID,
                DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID,
                DBContract.StockTable.KEY_QUANTITYHELD,
                DBContract.ProductsTable.KEY_PRODUCT

        };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        FeedEntry.COLUMN_NAME_UPDATED + " DESC";
        String queryTable = DBContract.StockTable.TABLE_NAME + " INNER JOIN " + DBContract.ProductsTable.TABLE_NAME + " ON "
                + DBContract.ProductsTable.TABLE_NAME + "." + DBContract.ProductsTable.KEY_PRODUCTID + " = "
                + DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID;
        Cursor c = mDb.query(queryTable, // The table to query
                projection,                               // The columns to return
                DBContract.StockTable.KEY_SHOPID + "= " + ShopID,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        //c.moveToFirst();
        //// TODO: 09/01/2016
        //// hard coded the cost of an item for now, needs to be looked up for each item
        Integer costOfItem = 2500;
        Integer randomIndexToPick;
        List<StockItem> affordableItems = new ArrayList<StockItem>();
        String itemNameChoosenToBuy;
        while (c.moveToNext()) {
            Log.d(TAG, DatabaseUtils.dumpCursorToString(c));
            if (c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD)) > 0) {
                if (costOfItem < mMoney) {
                    StockItem stockItem = new StockItem(c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_PRODUCTID)),c.getString(c.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT)));
                    affordableItems.add(stockItem);
                }
            }

        }
        c.close();
        if(affordableItems.size() < 1 ){
            //shopper can't afford anything in shop so leaves
            mCanAffordShop = false;
            return;
        }
        Integer itemIDChoosenToBuy;
        if(affordableItems.size() == 1){
            itemIDChoosenToBuy = affordableItems.get(0).getItemID();
            itemNameChoosenToBuy = affordableItems.get(0).getItemName();
        }
        else{
            randomIndexToPick = getRandInteger(1, affordableItems.size());
            itemIDChoosenToBuy = affordableItems.get(randomIndexToPick-1).getItemID();
            itemNameChoosenToBuy = affordableItems.get(randomIndexToPick-1).getItemName();
        }
        //ToDo - make probability a variable

        //todo - decided if user can buy more than one of item, code above will need amending when calculating cost
        Integer numberOfItemBought = 1;
        Integer probabilityOfBuyingItem = 30;
        if(getRandInteger(1,100) <= probabilityOfBuyingItem){
            mDb.execSQL("UPDATE " + DBContract.StockTable.TABLE_NAME + " SET "
                            + DBContract.StockTable.KEY_QUANTITYHELD + "=" + DBContract.StockTable.KEY_QUANTITYHELD + " - 1 WHERE "
                            + DBContract.StockTable.KEY_SHOPID + "= " + ShopID + " AND " + DBContract.StockTable.KEY_PRODUCTID + " = " + itemIDChoosenToBuy
            );
            //Integer stocklevel = c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD));
            mMoney = mMoney - costOfItem;
            TextView textView = (TextView) ((Activity) mContext).findViewById(R.id.edit_message);
            textView.append("\n" + mName + " just bought a " + itemNameChoosenToBuy + ", they now have " + getMoneyString() + " left.");

        }
        Intent intent = new Intent();
        intent.setAction(DatabaseChangedReceiver.ACTION_STOCK_LEVEL_DATABASE_CHANGED);
        intent.putExtra("ShopID", ShopID);
        intent.putExtra("StockID", itemIDChoosenToBuy);
        intent.putExtra("Stock Name", itemNameChoosenToBuy);
        intent.putExtra("Quantity Change", numberOfItemBought * -1);
        mContext.sendBroadcast(intent);


    }

    private class StockItem{
        private Integer itemID;
        private String itemName;

        public StockItem(Integer itemID, String itemName) {
            this.itemID = itemID;
            this.itemName = itemName;
        }

        public Integer getItemID() {
            return itemID;
        }

        public void setItemID(Integer itemID) {
            this.itemID = itemID;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
    }
}
