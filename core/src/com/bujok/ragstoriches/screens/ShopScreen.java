package com.bujok.ragstoriches.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.people.Person2;

public class ShopScreen implements Screen , InputProcessor {
    final RagsGame game;
    final String TAG = "ShopScreen";

    Texture shopperImage;
    Texture dropImage;
    Texture bucketImage;
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


    public ShopScreen(final RagsGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 720));
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage,this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        final TextButton button = new TextButton("Click me", skin, "default");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        stage.addActor(button);


        for (int i = 0; i < 10 ; i++) {
            Person2 p = new Person2("Shopper" + i,new Texture(Gdx.files.internal("shopper.png")) );
            stage.addActor(p);
            p.setX((i*50) + 20);
            MoveByAction mba = new MoveByAction();
            //mba.setAmountY(500f);
           // mba.setDuration(50f);
//
            //DelayAction da = new DelayAction();
           // float f = i*5;
            //da.setDuration(f);
           // Gdx.app.debug(TAG, "Delay duration : "+ f );

            ScaleByAction sba = new ScaleByAction();
            sba.setAmount(1.1f);
            sba.setDuration(0f);
            SequenceAction sa = new SequenceAction(sba,mba);
            p.addAction(sa);

        }



        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        shopMusic = Gdx.audio.newMusic(Gdx.files.internal("Groove_It_Now.mp3"));
        shopMusic.setLooping(true);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    /*    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Person p: game.shopperList) {
            if(p.isInfoShowing()){
                float rectHeight = 60;
                float rectWidth = 120;
                Color mycolor = new Color(0.90f,0.90f,0.90f,1);
                shapeRenderer.setColor(mycolor);
                shapeRenderer.rect(p.getCurrentPosition().x + ((p.getWidth()/2) - (rectWidth / 2)), p.getCurrentPosition().y + p.getHeight() + 5, rectWidth, rectHeight);

            }

        }
        shapeRenderer.end();

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);

        game.batch.draw(bucketImage, bucket.x, bucket.y);

        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }

        for (Person p: game.shopperList) {
            game.batch.draw(shopperImage,p.getCurrentPosition().x,p.getCurrentPosition().y,80 ,60);
            if(p.isInfoShowing()){
                float rectHeight = 60;
                float rectWidth = 120;
                Color mycolor = new Color(0.31f,0.60f,0.79f,1);
                game.font.setColor(mycolor);

                //shapeRenderer.rect(p.getCurrentPosition().x + ((p.getWidth()/2) - (rectWidth / 2)) +5, p.getCurrentPosition().y + p.getHeight() + rectHeight -10, rectWidth, rectHeight);
                game.font.draw(game.batch, "Name : " + p.getName(), p.getCurrentPosition().x + ((p.getWidth()/2) - (rectWidth / 2)) +5, p.getCurrentPosition().y + p.getHeight() + rectHeight - 5);
                game.font.draw(game.batch, "Age : " + p.getAge(), p.getCurrentPosition().x + ((p.getWidth()/2) - (rectWidth / 2)) +5, p.getCurrentPosition().y + p.getHeight() + rectHeight - 20);
            }
        }



        game.batch.end();

        for (Person p: game.shopperList) {
            p.render(delta);
        }


        // process user input
*//*        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
            for (Person p: game.shopperList) {
                p.getCurrentPosition();
                if(p.checkIfTouched(touchPos)){
                    Gdx.app.debug(TAG, "Object Touched!");
                }
                //Gdx.app.debug(TAG, "Object not touched");
            }
        }*//*
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                // your touch down code here
                Vector3 touchPos = new Vector3();
                touchPos.set(x, y, 0);
                camera.unproject(touchPos);
                bucket.x = touchPos.x - 64 / 2;
                return true; // return true to indicate the event was handled
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {
                // your touch up code here
                Vector3 touchPos = new Vector3();
                touchPos.set(x, y, 0);
                camera.unproject(touchPos);
                for (Person p : game.shopperList) {
                    p.getCurrentPosition();

                    if (p.checkIfTouched(touchPos)) {
                        Gdx.app.debug(TAG, "Object Touched!");
                    }

                }
                return true; // return true to indicate the event was handled
            }
        });


        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 10000000){
           // spawnRaindrop();

        }

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }*/

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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        shopMusic.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        latestTouch.set((float)screenX,(float)screenY);
        latestTouch = stage.screenToStageCoordinates(latestTouch);
        Actor hitActor = stage.hit(latestTouch.x,latestTouch.y,false);
        Gdx.app.log("HIT","Touch at : "+ latestTouch.toString());
        if(hitActor != null)
            Gdx.app.log("HIT",hitActor.getName()+" hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());

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
}