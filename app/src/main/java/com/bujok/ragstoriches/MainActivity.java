package com.bujok.ragstoriches;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.MyDbConnector;
import com.bujok.ragstoriches.people.Shopper;

import java.util.ArrayList;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";
    private SQLiteDatabase db;
    private TextView textView;
    private ScrollView mScrollView;


    private ArrayList<Shopper> mShopperList ;
    //nick vars
    long startTime = 0L;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.edit_message);
        textView.setMovementMethod(new ScrollingMovementMethod());
        db = new MyDbConnector(this).getWritableDatabase();
        createDefaultDatabase();

        Shopper s1 = new Shopper(this, "Shopper 1");
        textView.append("\n  New Shopper created : " + s1.getName() + "\n Age: " + s1.getAge() + "\n Money in wallet : " + s1.getMoneyString());
        Shopper s2 = new Shopper(this, "Shopper 2");
        textView.append("\n  New Shopper created : " + s2.getName() + "\n Age: " + s2.getAge() + "\n Money in wallet : " + s2.getMoneyString());
        Shopper s3 = new Shopper(this, "Shopper 3");
        textView.append("\n  New Shopper created : " + s3.getName() + "\n Age: " + s3.getAge() + "\n Money in wallet : " + s3.getMoneyString());
        mShopperList = new ArrayList<Shopper>();
        boolean pointlessVar = mShopperList.add(s1);
        mShopperList.add(mShopperList.size(),s2);
        mShopperList.add(mShopperList.size(),s3);


    }

    private void createDefaultDatabase(){
        SQLiteDatabase db = new MyDbConnector(this).getWritableDatabase();
        //db.execSQL(DBContract.ProductsTable.DELETE_TABLE);
        //db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
        //db.execSQL(DBContract.StockTable.DELETE_TABLE);
        //db.execSQL(DBContract.StockTable.CREATE_TABLE);
        String count = "SELECT count(*) FROM " + DBContract.StockTable.TABLE_NAME;
        Cursor c = db.rawQuery(count, null);
        c.moveToFirst();
        int icount = c.getInt(0);
        if(icount == 0){
            long newRowId;
            ContentValues values = new ContentValues();
            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Twix Bar");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME,null,values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID,newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME,null,values);
            values.clear();

            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Mars Bar");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME,null,values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID,newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME,null,values);
            values.clear();

            values.put(DBContract.ProductsTable.KEY_PRODUCT, "Freddo");
            newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME,null,values);
            Log.d(TAG, String.valueOf(newRowId));
            values.clear();
            values.put(DBContract.StockTable.KEY_SHOPID, 1);
            values.put(DBContract.StockTable.KEY_PRODUCTID,newRowId);
            values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
            newRowId = db.insert(DBContract.StockTable.TABLE_NAME,null,values);
            values.clear();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Buttons!!
    private Runnable shopperTimerThread = new Runnable() {
	public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            if (timeInMilliseconds > 5000) {

                startTime = SystemClock.uptimeMillis();

                for(Shopper s : mShopperList){
                    s.simShopperActions();
                }
                textView.append("\n Simulation finished");

                customHandler.postDelayed(this, 0);
            }
            else{
                customHandler.postDelayed(this, 0);
                //to kill thread
                //customHandler.removeCallbacks(updateTimerThread);
            }
        }
    };

    public void simulateButton(View view) {
        textView.append("\n Timer thread sarted");
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(shopperTimerThread, 0);
    }

    public void stopButton(View view) {
        customHandler.removeCallbacks(shopperTimerThread);
        textView.append("\n Timer thread stopped");
    }



    public Integer getStockAmount(){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DBContract.StockTable.KEY_SHOPID,
                DBContract.StockTable.KEY_PRODUCTID,
                DBContract.StockTable.KEY_QUANTITYHELD

        };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        Cursor c = db.query(
                DBContract.StockTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                DBContract.StockTable.KEY_PRODUCTID + "= 1",                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        Integer stocklevel = c.getInt(c.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD));
        textView.append("\n Current Twix Stock Level is : " + stocklevel);
        return stocklevel;
    }


}
