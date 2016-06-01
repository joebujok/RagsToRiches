package com.bujok.ragstoriches;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.db.DatabaseAndroid;

public class AndroidLauncher extends AndroidApplication  {


	@Override
	protected void onCreate (Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new RagsGame(this), config);
		RagsGame game = new RagsGame();
		initialize(game, config);
		Database db = new DatabaseAndroid(this.getBaseContext());
		game.setDatabase(db);

	}

}
