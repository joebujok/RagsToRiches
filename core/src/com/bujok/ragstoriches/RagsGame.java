package com.bujok.ragstoriches;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bujok.ragstoriches.NativeFunctions.NativeFunctions;


/**
 * Created by Buje on 22/02/2016.
 */
public class RagsGame extends Game  {

    public SpriteBatch batch;
    public BitmapFont font;
    public NativeFunctions nativeFunctions;


    public RagsGame(NativeFunctions nativeFunctions) {
        this.nativeFunctions = nativeFunctions;
       }

    public void create() {
        batch = new SpriteBatch();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        nativeFunctions.createDatabase();

        this.setScreen(new com.bujok.ragstoriches.screens.MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}