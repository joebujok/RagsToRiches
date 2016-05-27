package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.buildings.items.StockContainer;
import com.bujok.ragstoriches.map.GameMap;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.screens.components.UITopStatusBar;

/**
 * Created by Tojoh on 5/13/2016.
 */
public abstract class MapScreen implements Screen, InputProcessor
{
    final protected RagsGame game;
    final protected Stage mapLayer;
    final protected Skin skin;
    final protected Stage uiLayer;
    final protected Stage spriteLayer;

    OrthographicCamera camera;

    private Vector2 latestTouch = new Vector2(0,0);
    protected GameMap mapFrame;
    protected UITopStatusBar gameMenuBar;
    private Music bgrMusic;

    public MapScreen(final RagsGame game)
    {
        this.game = game;
        this.mapLayer = new Stage(new FitViewport(1200, 720));
        this.spriteLayer = new Stage(new FitViewport(1200, 720));
        this.uiLayer = new Stage(new FitViewport(1200, 720));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this.mapLayer, this.uiLayer, this.spriteLayer, this);
        Gdx.input.setInputProcessor(inputMultiplexer);


        this.gameMenuBar = new UITopStatusBar(this.uiLayer, skin);
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
        mapLayer.act(delta);

        this.renderMap(delta);
        this.renderStage(delta);
        this.renderUI(delta);
    }

    protected void renderMap(float delta)
    {
        if (this.mapFrame != null)
        {
            this.mapFrame.render(delta, this.game.batch);
        }
    }

    protected void renderStage(float delta)
    {
        mapLayer.draw();
        spriteLayer.draw();
    }

    protected void renderUI(float delta)
    {
        uiLayer.draw();

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
        latestTouch = mapLayer.screenToStageCoordinates(latestTouch);
        Actor hitActor = mapLayer.hit(latestTouch.x, latestTouch.y, false);
        Gdx.app.log("HIT", "Touch at : " + latestTouch.toString());
        if (hitActor != null)
            Gdx.app.log("HIT", hitActor.toString() + " hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());
        if (hitActor instanceof Person)
        {
            ((Person) hitActor).toggleInfoBox(mapLayer, skin);
        } else if (hitActor instanceof StockContainer)
        {
            ((StockContainer) hitActor).toggleInfoBox(mapLayer, skin);
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
