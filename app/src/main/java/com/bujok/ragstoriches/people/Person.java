package com.bujok.ragstoriches.people;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bujok.ragstoriches.db.MyDbConnector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by joebu on 07/01/2016.
 */
public class Person {

    protected String mName;
    protected Integer mAge;
    protected final Context mContext;
    protected SQLiteDatabase mDb;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAge(Integer mAge) {
        this.mAge = mAge;
    }


    public Person(Context context, String name){
        mContext = context;
        mName = name;
        Random rand = new Random();
        mAge = rand.nextInt((95 - 15) + 1) + 15;
        //MyDbConnector db = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
        mDb = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
    }

}
