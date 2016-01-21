package com.bujok.ragstoriches;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;

import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.DatabaseChangedReceiver;
import com.bujok.ragstoriches.db.MyDbConnector;

public class ShopStockLevelActivity extends AppCompatActivity {

    private static final String TAG = "ShopStockAct";

    TableLayout table_layout;
    EditText firstname_et, lastname_et;
    Button addmem_btn;

    MyDbConnector myDbConnector;
    protected SQLiteDatabase myDb;
    private DatabaseChangedReceiver mReceiver;

    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_stock_level);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        registerDatabaseRecevier();
    }
    private void registerDatabaseRecevier(){
        // this receives notification when stock table updates and should update the table.
        mReceiver = new DatabaseChangedReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // update your list
                Log.i(TAG, "database change recevied.");
                new MyAsync().execute();
            }

        };

    }


    public void loadTable(View v) {

        new MyAsync().execute();

    }

    private void BuildTable() {
        myDbConnector = new MyDbConnector(this);
        myDb = myDbConnector.getWritableDatabase();
        Integer ShopID = 1;


        String[] projection = {
                DBContract.StockTable.KEY_SHOPID,
                DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID,
                DBContract.ProductsTable.KEY_PRODUCT,
                DBContract.StockTable.KEY_QUANTITYHELD


        };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        FeedEntry.COLUMN_NAME_UPDATED + " DESC";
        String queryTable = DBContract.StockTable.TABLE_NAME + " INNER JOIN " + DBContract.ProductsTable.TABLE_NAME + " ON "
                + DBContract.ProductsTable.TABLE_NAME + "." + DBContract.ProductsTable.KEY_PRODUCTID + " = "
                + DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID;
        Cursor c = myDb.query(queryTable, // The table to query
                projection,                               // The columns to return
                DBContract.StockTable.KEY_SHOPID + "= " + ShopID,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        //myDbConnector.open();
        //Cursor c = sqlcon.readEntry();

        int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);

                tv.setText(c.getString(j));

                row.addView(tv);

            }

            c.moveToNext();

            table_layout.addView(row);

        }
        myDbConnector.close();
    }

    private class MyAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            table_layout.removeAllViews();

            PD = new ProgressDialog(ShopStockLevelActivity.this);
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {



            // inserting data
           // sqlcon.open();
           // sqlcon.insertData(firstname, lastname);
            // BuildTable();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            BuildTable();
            PD.dismiss();
        }
    }

}
