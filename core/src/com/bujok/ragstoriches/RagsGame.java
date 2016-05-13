package com.bujok.ragstoriches;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.screens.OutsideScreen;
import com.bujok.ragstoriches.screens.ShopScreen;


/**
 * Created by Buje on 22/02/2016.
 */
public class RagsGame extends Game  {

    public SpriteBatch batch;
    public BitmapFont font;
    public Database database;
    private Stage stage;
    public RagsGame(Database database) {
        this.database = database;
       }

    public void create() {
        batch = new SpriteBatch();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        //nativeFunctions.createDatabase();
        this.stage = new Stage(new FitViewport(1200, 720));

        this.setScreen(new com.bujok.ragstoriches.screens.MainMenuScreen(this, stage));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }


    public void showOutScreen()
    {
        this.getScreen().dispose();
        this.setScreen(new OutsideScreen(this, stage));
    }

    public void showShopScreen()
    {
        this.getScreen().dispose();
        this.setScreen(new ShopScreen(this, stage));
    }
}