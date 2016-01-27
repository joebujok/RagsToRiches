package com.bujok.ragstoriches.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by joebu on 21/01/2016.
 */
public class DatabaseChangedReceiver extends BroadcastReceiver{

    private static final String TAG = "DatabaseChangedReceiver";
    public static String ACTION_STOCK_LEVEL_DATABASE_CHANGED = "com.bujok.ragstoriches.STOCK_LEVEL_DATBASE_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Database change received");
    }
}
