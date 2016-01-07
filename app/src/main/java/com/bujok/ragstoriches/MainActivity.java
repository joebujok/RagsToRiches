package com.bujok.ragstoriches;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.MyDbConnector;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";
    private SQLiteDatabase db;
    private TextView textView;

    //nick vars
    long startTime = 0L;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.edit_message);
        db = new MyDbConnector(this).getWritableDatabase();
        createDefaultDatabase();
        textView.setText("Hi there!!!!1");
    }

    private void createDefaultDatabase(){
        SQLiteDatabase db = new MyDbConnector(this).getWritableDatabase();
        //db.execSQL(DBContract.ProductsTable.DELETE_TABLE);
        //db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
        //db.execSQL(DBContract.StockTable.DELETE_TABLE);
        //db.execSQL(DBContract.StockTable.CREATE_TABLE);

        ContentValues values = new ContentValues();
        values.put(DBContract.ProductsTable.KEY_PRODUCT, "Twix Bar");
        long newRowId;
        newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME,null,values);
        Log.d(TAG, String.valueOf(newRowId));
        values.clear();
        values.put(DBContract.StockTable.KEY_SHOPID, 1);
        values.put(DBContract.StockTable.KEY_PRODUCTID,newRowId);
        values.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
        newRowId = db.insert(DBContract.StockTable.TABLE_NAME,null,values);


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
    public void buyButton(View view) {

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(shopperTimerThread, 0);

    }

    private Runnable shopperTimerThread = new Runnable() {

        public void run() {
                         timeInMilliseconds = SystemClock.uptimeMillis() - startTime;


                         /*updatedTime = timeSwapBuff + timeInMilliseconds;
                         int secs = (int) (updatedTime / 1000) + 1;
                         int mins = secs / 60;
                         secs = secs % 60;
                         timerValue.setText("" + mins + ":"
                                                 + String.format("%02d", secs));*/
                         if (timeInMilliseconds > 5000) {
                                 /*customHandler.removeCallbacks(updateTimerThread);
                                 myButton.setEnabled(true);
                                 timerValue.setText("" + 0 + ":"
                                                 + String.format("%02d", 0));*/
                             startTime = SystemClock.uptimeMillis();

                             Integer currentStock = getStockAmount();
                             ContentValues cv = new ContentValues();
                             cv.put(DBContract.StockTable.KEY_QUANTITYHELD, currentStock - 1);
                             db.update(DBContract.StockTable.TABLE_NAME, cv, DBContract.StockTable.KEY_PRODUCTID + "= 1", null);
                             getStockAmount();

                             customHandler.postDelayed(this, 0);
                             }
                         else{
                             customHandler.postDelayed(this, 0);
                             }
                     }
             };


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
        textView.setText("Current Twix Stock Level is : " + stocklevel);
        return stocklevel;
    }
}
