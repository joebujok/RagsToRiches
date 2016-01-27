package com.bujok.ragstoriches;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.Gravity;

import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.bujok.ragstoriches.db.DBContract;
import com.bujok.ragstoriches.db.DatabaseChangedReceiver;
import com.bujok.ragstoriches.db.MyDbConnector;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopStockLevelActivity extends AppCompatActivity {

    private static final String TAG = "ShopStockAct";

    TableLayout mTable_layout;
    EditText firstname_et, lastname_et;
    Button addmem_btn;

    MyDbConnector myDbConnector;
    protected SQLiteDatabase myDb;
    private DatabaseChangedReceiver mReceiver;

    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        registerDatabaseRecevier();
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
        mTable_layout = (TableLayout) findViewById(R.id.tableLayout1);

    }
    private void registerDatabaseRecevier(){
        // this receives notification when stock table updates and should update the table.
        mReceiver = new DatabaseChangedReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // update your list
                Log.i(TAG, "database change recevied.");
                int StockIdChange = intent.getIntExtra("StockID", 0);
                int QuantityChange = intent.getIntExtra("Quantity Change", 0);
                //TextView TvToUpdate = null;
                Integer NumRows = mTable_layout.getChildCount();
                int rowIndexToUpdate;
                if(NumRows > 1){
                    exitloop:
                    for(int i = 1; i < NumRows; i++){
                        TableRow curRow = (TableRow)mTable_layout.getChildAt(i);
                        TextView TVStockId = (TextView)curRow.getChildAt(1);
                        if(Integer.parseInt(TVStockId.getText().toString()) == StockIdChange){
                            TextView TvToUpdate = (TextView)curRow.getChildAt(3);
                            int newStockLevel = Integer.parseInt(TvToUpdate.getText().toString()) + QuantityChange;
                            TvToUpdate.setText(String.valueOf(newStockLevel));
                            int colorChange;
                            if(QuantityChange > 0){
                                colorChange = Color.GREEN;
                            }
                            else{
                                colorChange = Color.RED;
                            }
                            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(TvToUpdate, "textColor", colorChange, Color.BLACK);
                            objectAnimator.setDuration(1000);
                            objectAnimator.setEvaluator(new ArgbEvaluator());
                            objectAnimator.start();
                            break exitloop;
                        }

                    }


                }
            }

        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DatabaseChangedReceiver.ACTION_STOCK_LEVEL_DATABASE_CHANGED);
        this.registerReceiver(this.mReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
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
        List<String> headings = new ArrayList<String>();
        headings.add("Shop ID");
        headings.add("Stock Item ID");
        headings.add("Item");
        headings.add("Quantity");

        //set table column headings
        TableRow headingRow = new TableRow(this);
        for(Iterator<String> i = headings.iterator(); i.hasNext(); ) {

            headingRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            TextView headingTV = new TextView(this);
            headingTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            headingTV.setBackgroundResource(R.drawable.cell_shape);
            headingTV.setGravity(Gravity.CENTER);
            headingTV.setTextSize(18);
            headingTV.setPadding(0, 5, 0, 5);
            headingTV.setText(i.next());
            headingRow.addView(headingTV);


        }
        mTable_layout.addView(headingRow);


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

            mTable_layout.addView(row);

        }
        myDbConnector.close();
    }

    private class MyAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            mTable_layout.removeAllViews();

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
