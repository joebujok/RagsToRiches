package com.bujok.ragstoriches.buildings;

import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.RagsGame;

/**
 * Created by Buje on 02/05/2016.
 */
public class Building {

    protected Database database;

    public Building(final RagsGame game) {
        this.database = game.database;
    }
}
