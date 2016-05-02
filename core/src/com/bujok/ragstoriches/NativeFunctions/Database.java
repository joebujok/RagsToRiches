package com.bujok.ragstoriches.NativeFunctions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.bujok.ragstoriches.NativeFunctions.DBContract;

import java.util.HashMap;


/**
 * Created by Buje on 28/03/2016.
 */
public abstract class Database {

    protected final String TAG = "CommonDatabase";

    protected static String database_name="recycling_separation";
    protected static Database instance = null;
    protected static int version=1;

    //Runs a sql query like "create".
    public abstract void execute(String sql);

    //Identical to execute but returns the number of rows affected (useful for updates)
    public abstract int executeUpdate(String sql);


    /**
     * Identical to execute but returns the row ID of the last row inserted (useful for inserts)
     * Execute this SQL statement and return the ID of the row inserted due to this call.
     * The SQL statement should be an INSERT for this to be a useful call.
     * @param values Name of column, value for column
     * @return the row ID of the last row inserted, if this insert is successful. -1 otherwise.
     *
     */
    public abstract int executeInsert(String tableName, HashMap<String, Object> values, String nullColumnHack);

    //Runs a query and returns an Object with all the results of the query. [Result Interface is defined below]
    public abstract Result query(String sql);

    public void onCreate(){
        //Example of Highscore table code (You should change this for your own DB code creation)
        execute(DBContract.ProductsTable.CREATE_TABLE);
        execute(DBContract.StockTable.CREATE_TABLE);
        int newRowId;

        HashMap<String,Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(DBContract.ProductsTable.KEY_PRODUCT,"Twix Bar");
        newRowId =  executeInsert(DBContract.ProductsTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();
        valuesMap.put(DBContract.StockTable.KEY_SHOPID, 1);
        valuesMap.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
        valuesMap.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
        newRowId =  executeInsert(DBContract.StockTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();

        valuesMap.put(DBContract.ProductsTable.KEY_PRODUCT,"Mars Bar");
        newRowId =  executeInsert(DBContract.ProductsTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();
        valuesMap.put(DBContract.StockTable.KEY_SHOPID, 1);
        valuesMap.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
        valuesMap.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
        newRowId =  executeInsert(DBContract.StockTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();

        valuesMap.put(DBContract.ProductsTable.KEY_PRODUCT,"Freddo");
        newRowId =  executeInsert(DBContract.ProductsTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();
        valuesMap.put(DBContract.StockTable.KEY_SHOPID, 1);
        valuesMap.put(DBContract.StockTable.KEY_PRODUCTID, newRowId);
        valuesMap.put(DBContract.StockTable.KEY_QUANTITYHELD, 1000);
        newRowId =  executeInsert(DBContract.StockTable.TABLE_NAME,valuesMap, null);
        valuesMap.clear();


       // Result r = query(query);
        execute("CREATE TABLE 'highscores' ('_id' INTEGER PRIMARY KEY  NOT NULL , 'name' VARCHAR NOT NULL , 'score' INTEGER NOT NULL );");
        execute("INSERT INTO 'highscores'(name,score) values ('Cris',1234)");
        //Example of query to get DB data of Highscore tableq.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT)


        Result q=query("SELECT * FROM highscores");
        if (!q.isEmpty()){
            q.moveToNext();
           // logger.debug("Highscore of "+q.getString(q.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT))+": "+q.getString(q.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCTID)));
          //  System.out.println("Highscore of "+q.getString(q.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT))+": "+q.getString(q.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCTID)));
        }

    }

    public void onUpgrade(){
        //Example code (You should change this for your own DB code)
        execute("DROP TABLE IF EXISTS 'highscores';");
        onCreate();
        System.out.println("DB Upgrade maded because I changed DataBase.version on code");
    }

    //Interface to be implemented on both Android and Desktop Applications
    public interface Result{
        public boolean isEmpty();
        public boolean moveToNext();
        public int getColumnIndex(String name);
        public float getFloat(int columnIndex);
        public String getString(int columnIndex);
        public int getInt(int columnIndex);
        //more to be added...
    }
}