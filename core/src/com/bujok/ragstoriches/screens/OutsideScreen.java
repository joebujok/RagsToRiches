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

public class OutsideScreen extends MapScreen
{
    final String TAG = "OutsideScreen";
    OrthographicCamera camera;
    List<Person> people = new ArrayList<Person>();


    public OutsideScreen(final RagsGame game)
    {
        super(game);
        this.initialiseMap();
        this.initialiseComponents();
    }

    private void initialiseMap()
    {
        this.mapFrame = new GameMap(this.stage, null, new Texture(Gdx.files.internal("outside_small.png")));
    }

    private void initialiseComponents()
    {
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

}