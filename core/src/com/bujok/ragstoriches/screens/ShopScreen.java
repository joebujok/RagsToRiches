package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.people.Person;

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

        final TextButton button = new TextButton("Click me", skin, "green");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);


        final Table table = new Table(skin);
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(new Color(0x0190C3D4));
        pm1.fill();

        Label nameLabel = new Label("Name : Terry Tibbs",skin, "infobox");
        nameLabel.setAlignment(Align.left);
        final Label moneyLabel = new Label("Money : £123.23",skin);
        moneyLabel.setAlignment(Align.left);
        table.setPosition(300f,300f);
        TextButton closeTextButton = new TextButton("Close",skin,"default");
        closeTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                table.remove();
            }
        });
        closeTextButton.align(Align.right);
        table.add(closeTextButton);
        table.row();
        table.add(nameLabel);
        table.row();
        table.add(moneyLabel);
        Cell c = table.getCells().get(2);
        c.align(Align.left);
        c.pad(0,20,0,20);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        //table.setBackground("red");
        table.pack();
        table.debug();
        stage.addActor(button);
        stage.addActor(table);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");

                table.remove();
            }
        });

        for (int i = 0; i < 10 ; i++) {
            Person p = new Person("Shopper" + i,new Texture(Gdx.files.internal("shopper.png")) );
            stage.addActor(p);
            p.setX((i*50) + 20);
            MoveByAction mba = new MoveByAction();
            mba.setAmountY(500f);
            mba.setDuration(50f);
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
            Gdx.app.log("HIT",hitActor.toString()+" hit, x: " + hitActor.getX() + ", y: " + hitActor.getY());
            if(hitActor instanceof Person){
                ((Person) hitActor).toggleInfoBox(stage,skin);
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
}