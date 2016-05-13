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
import com.bujok.ragstoriches.map.GameMap;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.buildings.items.StockContainer;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen extends MapScreen
{
    final String TAG = "ShopScreen";
    OrthographicCamera camera;
    private Shop currentShop = null;
    private GameMenuBar gameMenuBar;
    List<Person> people = new ArrayList<Person>();

    public ShopScreen(final RagsGame game)
    {
        super(game);
        this.initialiseMap();
        this.initialiseComponents();
    }

    private void initialiseMap()
    {
        this.map = new GameMap(null, new Texture(Gdx.files.internal("shop.png")));
    }

    private void initialiseComponents()
    {
        this.currentShop = new Shop(stage, game,1);
        stage.addActor(currentShop);

        this.gameMenuBar = new GameMenuBar(stage, skin);
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
                Integer i;
                i = Integer.parseInt(gameMenuBar.getMoneyValue() + 1);
                gameMenuBar.setMoneyValue(i.toString() );
            }
        });

        // game.nativeFunctions.HelloWorld();

        final TextButton toggleMapButton = new TextButton("Toggle Map", skin, "green");

        toggleMapButton.setWidth(200f);
        toggleMapButton.setHeight(20f);
        toggleMapButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 50f);

        toggleMapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                ShopScreen.this.game.showOutScreen();
            }
        });

        stage.addActor(toggleMapButton);

        final TextButton gotoButton = new TextButton("Go to", skin, "green");

        gotoButton.setWidth(200f);
        gotoButton.setHeight(20f);
        gotoButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 90f);

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
        showGridButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 130f);

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

        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1.1f);
        sba.setDuration(0f);
        SequenceAction sa = new SequenceAction(sba);


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
    }
}