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
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.shop.StockContainer;

import java.util.ArrayList;
import java.util.List;

public class OutsideScreen implements Screen , InputProcessor
{
    final RagsGame game;
    final String TAG = "OutsideScreen";


    Texture shopperImage;
    Texture dropImage;
    Texture bucketImage;
    Texture backgroundImage;
    Sound dropSound;
    Music shopMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Rectangle shopper;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    ShapeRenderer shapeRenderer;
    private Stage stage;
    private Vector2 latestTouch = new Vector2(0,0);
    private Skin skin;
    private Shop currentShop = null;
    boolean visible = false;


    List<Person> people = new ArrayList<Person>();


    public OutsideScreen(final RagsGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.currentShop = new Shop(this.stage, game,1);
        this.stage.addActor(currentShop);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this.stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        backgroundImage = new Texture(Gdx.files.internal("outside.png"));



       // game.nativeFunctions.HelloWorld();

        final TextButton toggleMapButton = new TextButton("Toggle Map", skin, "green");

        toggleMapButton.setWidth(200f);
        toggleMapButton.setHeight(20f);
        toggleMapButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 50f);

        toggleMapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                    OutsideScreen.this.game.showShopScreen();
            }
        });

        this.stage.addActor(toggleMapButton);

        final TextButton showGridButton = new TextButton("Toggle Grid", skin, "green");

        showGridButton.setWidth(200f);
        showGridButton.setHeight(20f);
        showGridButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 90f);

        showGridButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Person p = OutsideScreen.this.people.get(0);
                if (p != null)
                {
                    p.setGridVisible(!p.isGridVisible());
                }
            }
        });

        this.stage.addActor(showGridButton);
    }






    @Override
    public void render(float delta)
    {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(delta);

            // draw the hop background
            this.game.batch.begin();
            this.game.batch.draw(this.backgroundImage, 0, 0, 1200, 720);
            this.game.batch.end();

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
    public void dispose() {
        if (this.dropImage != null) {
            dropImage.dispose();
        }
        if (this.bucketImage != null) {
            bucketImage.dispose();
        }
        if (this.dropSound != null) {
            dropSound.dispose();
        }
        if (this.shopMusic != null) {
            shopMusic.dispose();
        }
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
        if (visible) {
            latestTouch.set((float) screenX, (float) screenY);
            latestTouch = stage.screenToStageCoordinates(latestTouch);
            Actor hitActor = stage.hit(latestTouch.x, latestTouch.y, false);
            Gdx.app.log("HIT", "Touch at : " + latestTouch.toString());
            if (hitActor != null)
                Gdx.app.log("HIT", hitActor.toString() + " hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());
            if (hitActor instanceof Person) {
                ((Person) hitActor).toggleInfoBox(stage, skin);
            } else if (hitActor instanceof StockContainer) {
                ((StockContainer) hitActor).toggleInfoBox(stage, skin);
            }

            return true;
        }
        return false;
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

    public Shop getCurrentShop() {
        return currentShop;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}