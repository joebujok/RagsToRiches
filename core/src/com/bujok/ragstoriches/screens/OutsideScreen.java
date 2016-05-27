package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.map.GameMap;
import com.bujok.ragstoriches.people.Person;

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
        this.mapFrame = new GameMap(this.mapLayer, null, new Texture(Gdx.files.internal("outside_small.png")));
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

        this.mapLayer.addActor(toggleMapButton);

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

        this.mapLayer.addActor(showGridButton);
    }

}