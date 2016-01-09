package com.bujok.ragstoriches.people;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.bujok.ragstoriches.R;
import com.bujok.ragstoriches.db.DBContract;
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

    public Shopper(Context context, String name) {
        super(context, name);
        Random rand = new Random();
        Integer pounds = rand.nextInt((95 - 15) + 1) + 15;
        Integer pence = rand.nextInt((99 - 0) + 1) + 0;
        mMoney = (pounds * 100) + pence;


    }

    public Integer getMoney() {
        return mMoney;
    }

    public void setMoney(Integer mMoney) {
        this.mMoney = mMoney;
    }
    public String getMoneyString(){
        String str = mMoney.toString();
        String pence = str.substring(str.length() - 2);
        String pounds = str.substring(0,str.length() - 2);
        str = pounds + "."+ pence;
        return str;
    }

    public void simShopperActions(){
        buyItemInShop(1);
    }

    private void buyItemInShop(int ShopID) {
        String[] projection = {
                DBContract.StockTable.KEY_SHOPID,
                DBContract.StockTable.KEY_PRODUCTID,
                DBContract.StockTable.KEY_QUANTITYHELD

        };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        Cursor c = mDb.query(
                DBContract.StockTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                DBContract.StockTable.KEY_SHOPID + "= " + ShopID,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        //c.moveToFirst();
        //// TODO: 09/01/2016
        // hard coded the cost of an item for now, needs to be looked up for each item
        Integer costOfItem = 50;
        Integer randomIndexToPick;
        List<Integer> affordableItems = new ArrayList<Integer>();
        while (c.moveToNext()) {
            Log.d(TAG, DatabaseUtils.dumpCursorToString(c));
            if (c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD)) > 0) {
                if (costOfItem < mMoney) {
                    affordableItems.add(c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_PRODUCTID)));
                }
            }

        }
        c.close();
        if(affordableItems.size() < 1 ){
            return;
        }
        Integer itemIDChoosenToBuy;
        if(affordableItems.size() == 1){
            itemIDChoosenToBuy = affordableItems.get(0);
        }
        else{
            randomIndexToPick = getRandInteger(1, affordableItems.size());
            itemIDChoosenToBuy = affordableItems.get(randomIndexToPick-1);
        }
        //ToDo - make probability a variable


        Integer probabilityOfBuyingItem = 30;
        if(getRandInteger(1,100) <= probabilityOfBuyingItem){
            mDb.execSQL("UPDATE " + DBContract.StockTable.TABLE_NAME + " SET "
                            + DBContract.StockTable.KEY_QUANTITYHELD + "=" + DBContract.StockTable.KEY_QUANTITYHELD + " - 1 WHERE "
                            + DBContract.StockTable.KEY_SHOPID + "= " + ShopID + " AND " + DBContract.StockTable.KEY_PRODUCTID + " = " + itemIDChoosenToBuy
            );
            //Integer stocklevel = c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD));
            mMoney = mMoney - costOfItem;
            TextView textView = (TextView) ((Activity) mContext).findViewById(R.id.edit_message);
            textView.append("\n" + mName + " just bought an item, they now have " + getMoneyString() + " left.");

        }


    }


}
