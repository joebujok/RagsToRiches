package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.items.StockItem;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.shop.StockContainer;
import com.bujok.ragstoriches.utils.StockType;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen implements Screen , InputProcessor
{
    public static ShopScreen INSTANCE = null;

    final RagsGame game;
    final String TAG = "ShopScreen";


    Texture shopperImage;
    Texture dropImage;
    Texture bucketImage;
    Texture shopImage;
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

    List<Person> people = new ArrayList<Person>();


    public ShopScreen(final RagsGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 720));
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.currentShop = new Shop(stage, game,1);
        stage.addActor(currentShop);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        shopImage = new Texture(Gdx.files.internal("shop.png"));

        // test harness for btree
        ShopScreen.INSTANCE = this;


        // Keep your code clean by creating widgets separate from layout.
        Label nameLabel = new Label("Name:", skin);
        Label addressLabel = new Label("Address:", skin);


        float menuBarHeight = stage.getHeight() * 0.04f;
        Table menuBartable = new Table();
        menuBartable.setWidth(stage.getWidth());
        menuBartable.setHeight(menuBarHeight);
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        //pm1.setColor(new Color(0x0190C3D4));
        pm1.setColor(new Color(135f/255f,131f/255f,131f/255f,1f));
        pm1.fill();
        menuBartable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));

        menuBartable.setPosition(0,stage.getHeight() - menuBarHeight);
        stage.addActor(menuBartable);
        menuBartable.debug();      // Turn on all debug lines (table, cell, and widget).
        menuBartable.debugTable(); // Turn on only table lines.
        menuBartable.add(nameLabel);    // Row 0, column 0.


        menuBartable.add(addressLabel); // Row 0, column 1.


        final TextButton button = new TextButton("Buy a Melon", skin, "green");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);


        stage.addActor(button);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("Buy Another Melon");
                currentShop.buyItem(1,1);
            }
        });

       // game.nativeFunctions.HelloWorld();

        final TextButton gotoButton = new TextButton("Go to", skin, "green");

        gotoButton.setWidth(200f);
        gotoButton.setHeight(20f);
        gotoButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 50f);

        gotoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Person p = ShopScreen.this.people.get(1);
                if (p != null)
                {
                    p.moveAlongPath();
                }
            }
        });

        stage.addActor(gotoButton);

        final TextButton showGridButton = new TextButton("Toggle Grid", skin, "green");

        showGridButton.setWidth(200f);
        showGridButton.setHeight(20f);
        showGridButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 90f);

        showGridButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Person p = ShopScreen.this.people.get(0);
                if (p != null)
                {
                    p.setGridVisible(!p.isGridVisible());
                }
            }
        });

        stage.addActor(showGridButton);
//
        //DelayAction da = new DelayAction();
        // float f = i*5;
        //da.setDuration(f);
        // Gdx.app.debug(TAG, "Delay duration : "+ f );

        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1.1f);
        sba.setDuration(0f);
        SequenceAction sa = new SequenceAction(sba);

        int xpadding = 126;
        int ypadding = 0;


        Person p = new Person("Leader",new TextureRegion(new Texture(Gdx.files.internal("shopper.png"))) );
        stage.addActor(p);
        p.setX(520);
        p.setY(470);
        p.addAction(sa);
        this.people.add(p);

        // loop through and get each subsequent person following the last.
        for (int i = 1; i < 2 ; i++)
        {
            ScaleByAction sba2 = new ScaleByAction();
            sba.setAmount(1.1f);
            sba.setDuration(0f);
            SequenceAction sa2 = new SequenceAction(sba);

            Person lastPerson = p;
            p = new Person("Follower" + i, new TextureRegion(new Texture(Gdx.files.internal("shopper.png"))) );
            stage.addActor(p);
            p.addAction(sa2);
            this.people.add(p);
        }


        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        shopMusic = Gdx.audio.newMusic(Gdx.files.internal("Groove_It_Now.mp3"));
        shopMusic.setLooping(true);
        //shopMusic.play();


    }






    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        // draw the hop background
        this.game.batch.begin();
        this.game.batch.draw(this.shopImage, 0,0, 1200,720);
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        shopMusic.dispose();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        latestTouch.set((float)screenX,(float)screenY);
        latestTouch = stage.screenToStageCoordinates(latestTouch);
        Actor hitActor = stage.hit(latestTouch.x,latestTouch.y,false);
        Gdx.app.log("HIT","Touch at : "+ latestTouch.toString());
        if(hitActor != null)
            Gdx.app.log("HIT",hitActor.toString()+" hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());
            if(hitActor instanceof Person)
            {
                ((Person) hitActor).toggleInfoBox(stage,skin);
            }
            else if (hitActor instanceof StockContainer)
            {
                ((StockContainer) hitActor).toggleInfoBox(stage,skin);
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

    public Shop getCurrentShop() {
        return currentShop;
    }
}