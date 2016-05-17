package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.utils.RagsFontUtility;

/**
 * Created by Buje on 22/02/2016.
 */
public class MainMenuScreen implements Screen {
    final RagsGame game;
    private final Stage stage;
    private OrthographicCamera camera;


    public MainMenuScreen(final RagsGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(1200, 720));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 720);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        RagsFontUtility.HEADER_FONT.draw(game.batch, "Rags to Riches", this.stage.getWidth()/2 - 200, 600);

        RagsFontUtility.NORMAL_FONT.draw(game.batch, "Welcome to Rags To Riches!!! ", 100, 150);
        RagsFontUtility.NORMAL_FONT.draw(game.batch, "Tap anywhere to begin!", 100, 120);
        game.batch.end();

        if (Gdx.input.isTouched())
        {
           this.game.showShopScreen();
            dispose();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
