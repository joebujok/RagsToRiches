package com.bujok.ragstoriches;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.MyDbConnector;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = new MyDbConnector(this).getWritableDatabase();
        db.execSQL(DBContract.ProductsTable.DELETE_TABLE);
        db.execSQL(DBContract.ProductsTable.CREATE_TABLE);
        db.execSQL(DBContract.StockTable.DELETE_TABLE);
        db.execSQL(DBContract.StockTable.CREATE_TABLE);

        ContentValues values = new ContentValues();
        values.put(DBContract.ProductsTable.KEY_PRODUCT, "Twix Bar");
        long newRowId;
        newRowId = db.insert(DBContract.ProductsTable.TABLE_NAME,null,values);
        Log.d(TAG, String.valueOf(newRowId));
        values.clear();
        values.put(DBContract.StockTable.KEY_SHOPID, 1);
        values.put(DBContract.StockTable.KEY_PRODUCTID,newRowId);
        values.put(DBContract.StockTable.KEY_QUANTITYHELD,1000);
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
}
