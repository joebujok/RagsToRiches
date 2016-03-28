package com.bujok.ragstoriches;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bujok.ragstoriches.NativeFunctions.NativeFunctions;
import com.bujok.ragstoriches.db.DatabaseAndroid;
import com.bujok.ragstoriches.db.MyDbConnector;

public class AndroidLauncher extends AndroidApplication implements NativeFunctions {


	@Override
	protected void onCreate (Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new RagsGame(this), config);
		initialize(new RagsGame(new DatabaseAndroid(this.getBaseContext())), config);
	}


	@Override
	public void HelloWorld() {
		String s =  "sadasda";
		Gdx.app.log("HIT","hello world = from android!!");
	}

	@Override
	public void createDatabase() {
		SQLiteDatabase db = new MyDbConnector(this).getWritableDatabase();
	}
}
