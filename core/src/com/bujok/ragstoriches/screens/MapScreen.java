package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.buildings.items.StockContainer;
import com.bujok.ragstoriches.map.GameMap;
import com.bujok.ragstoriches.people.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tojoh on 5/13/2016.
 */
public abstract class MapScreen implements Screen, InputProcessor
{
    final protected RagsGame game;
    final protected Stage stage;
    final protected Skin skin;

    OrthographicCamera camera;

    private Vector2 latestTouch = new Vector2(0,0);
    protected GameMap map;
    private Music bgrMusic;

    public MapScreen(final RagsGame game)
    {
        this.game = game;
        this.stage = new Stage(new FitViewport(1200, 720));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this.stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.playMusic();
    }

    private void playMusic() {
        this.bgrMusic = Gdx.audio.newMusic(Gdx.files.internal("Groove_It_Now.mp3"));
        this.bgrMusic.setLooping(true);
        //shopMusic.play();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        if (this.map != null)
        {
            this.map.render(delta, this.game.batch);
        }
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

        // shopMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }


    @Override
    public boolean keyDown(int keycode) {
        // this wont ever be called on android unless the keypad is visible

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        // this wont ever be called on android unless the keypad is visible
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        latestTouch.set((float) screenX, (float) screenY);
        latestTouch = stage.screenToStageCoordinates(latestTouch);
        Actor hitActor = stage.hit(latestTouch.x, latestTouch.y, false);
        Gdx.app.log("HIT", "Touch at : " + latestTouch.toString());
        if (hitActor != null)
            Gdx.app.log("HIT", hitActor.toString() + " hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());
        if (hitActor instanceof Person)
        {
            ((Person) hitActor).toggleInfoBox(stage, skin);
        } else if (hitActor instanceof StockContainer)
        {
            ((StockContainer) hitActor).toggleInfoBox(stage, skin);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void dispose()
    {
        if (this.bgrMusic != null)
        {
            this.bgrMusic.dispose();
        }
    }
}
