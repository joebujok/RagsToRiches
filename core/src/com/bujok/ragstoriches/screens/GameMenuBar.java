package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Buje on 11/05/2016.
 */
public class GameMenuBar {

    private Stage stage;
    private Skin skin;
    final Label moneyLabel;
    private Label moneyValue ;

    public GameMenuBar(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        this.moneyLabel = new Label("Money: ", skin);
        this.moneyValue = new Label("0", skin);




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
        menuBartable.add(moneyLabel).right();    // Row 0, column 0.
        menuBartable.add(moneyValue).right(); // Row 0, column 1.
    }


    public String getMoneyValue() {
        return moneyValue.toString();
    }

    public void setMoneyValue(String moneyValue) {
        this.moneyValue.setText( moneyValue);
    }
}
