package com.bujok.ragstoriches.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.people.Person;

public class ShopScreen implements Screen {
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

    public ShopScreen(final RagsGame game) {
        this.game = game;


        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        shopperImage = new Texture(Gdx.files.internal("shopper.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        shopMusic = Gdx.audio.newMusic(Gdx.files.internal("Groove_It_Now.mp3"));
        shopMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 720);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;
        Rectangle shopperRectangle = new Rectangle(100,100,80,60);
        Person shopper1 = new Person("shopper 1", shopperRectangle, 100,100);
        game.shopperList.add(shopper1);

        shapeRenderer = new ShapeRenderer();
        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
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
/*        if (Gdx.input.isTouched()) {
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
        }*/
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        shopMusic.dispose();
    }

}