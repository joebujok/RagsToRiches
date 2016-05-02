package com.bujok.ragstoriches.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.bujok.ragstoriches.NativeFunctions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Buje on 28/03/2016.
 */
public class DatabaseAndroid extends com.bujok.ragstoriches.NativeFunctions.Database{
    protected SQLiteOpenHelper db_connection;
    protected SQLiteDatabase stmt;

    public DatabaseAndroid(Context context) {
        db_connection = new AndroidDB(context, database_name, null, version);
        stmt=db_connection.getWritableDatabase();
    }

    public void execute(String sql){
        stmt.execSQL(sql);
    }

    public int executeUpdate(String sql){
        stmt.execSQL(sql);
        SQLiteStatement tmp = stmt.compileStatement("SELECT CHANGES()");
        return (int) tmp.simpleQueryForLong();
    }

    @Override
    public int executeInsert(String tableName, HashMap<String, Object> values, String nullColumnHack) {
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,Object> entry : values.entrySet()){

            if( entry.getValue() instanceof String){
                contentValues.put(entry.getKey(), (String) entry.getValue());
            }
            if( entry.getValue() instanceof Integer ){
                contentValues.put(entry.getKey(), (Integer) entry.getValue());
            }
            if( entry.getValue() instanceof Float){
                contentValues.put(entry.getKey(), (Float) entry.getValue());
            }
            if( entry.getValue() instanceof Boolean){
                contentValues.put(entry.getKey(), (Boolean) entry.getValue());
            }


        }
        return (int) stmt.insert(tableName,nullColumnHack,contentValues);
    }

    public Result query(String sql) {
        ResultAndroid result=new ResultAndroid(stmt.rawQuery(sql,null));
        return result;
    }

    class AndroidDB extends SQLiteOpenHelper {

        public AndroidDB(Context context, String name, CursorFactory factory,
                         int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            stmt=db;
            DatabaseAndroid.this.onCreate();
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            stmt=db;
            DatabaseAndroid.this.onUpgrade();
        }

    }

    public class ResultAndroid implements Result{
        Cursor cursor;

        public ResultAndroid(Cursor cursor) {
            this.cursor=cursor;
        }

        public boolean isEmpty() {
            return cursor.getCount()==0;
        }

        @Override
        public boolean moveToNext() {
            return cursor.moveToNext();
        }

        public int getColumnIndex(String name) {
            return cursor.getColumnIndex(name);
        }

        public String[] getColumnNames() {
            return cursor.getColumnNames();
        }

        public float getFloat(int columnIndex) {
            return cursor.getFloat(columnIndex);
        }

        public String getString(int columnIndex) {
            return cursor.getString(columnIndex);
        }

        public int getInt(int columnIndex) {return cursor.getInt(columnIndex);        }


    }

}
