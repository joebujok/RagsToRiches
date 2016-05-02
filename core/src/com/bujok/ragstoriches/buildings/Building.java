package com.bujok.ragstoriches.buildings;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.RagsGame;

/**
 * Created by Buje on 02/05/2016.
 */
public class Building extends Image {

    protected Database database;
    protected Stage stage;

    public Building(Stage stage, final RagsGame game) {
        this.database = game.database;
        this.stage = stage;
    }
}
