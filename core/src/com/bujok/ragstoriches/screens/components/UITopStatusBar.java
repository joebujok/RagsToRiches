package com.bujok.ragstoriches.screens.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Buje on 11/05/2016.
 */
public class UITopStatusBar {

    private Stage stage;
    private Skin skin;
    private final Label moneyLabel;
    private final Label timeLabel;

    // for some reason the currency formatter is not adding the pound sign. Set to dollars for now.
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm EEEE MMMM yyyy");

    int money = 0;

    public UITopStatusBar(Stage stage, Skin skin)
    {
        float padding = 6f;
        this.stage = stage;
        this.skin = skin;

        this.currencyFormatter.setMinimumIntegerDigits(4);
        Calendar cal = Calendar.getInstance();
        this.timeLabel = new Label(sdf.format(cal.getTime()), skin);
        this.moneyLabel = new Label("Money: ", skin);

        this.addMoney(0);

        float menuBarHeight = 24f;
        Table menuBartable = new Table();
        menuBartable.setWidth(stage.getWidth());
        menuBartable.setHeight(menuBarHeight);
        menuBartable.defaults().expand().fill();
        menuBartable.columnDefaults(0).left();
        menuBartable.columnDefaults(1).right();

        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(new Color(135f/255f,131f/255f,131f/255f,1f));
        pm1.fill();
        menuBartable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        menuBartable.setPosition(0, stage.getHeight() - menuBarHeight);
        stage.addActor(menuBartable);

        //menuBartable.debug();
        //menuBartable.debugAll();

        menuBartable.padLeft(padding);
       // menuBartable.left();
        menuBartable.add(timeLabel).left();    // Row 0, column 0.

        menuBartable.padRight(padding);
//        menuBartable.right();
        //menuBartable.add(moneyLabel).right().width(50f);    // Row 0, column 0.
        menuBartable.add(this.moneyLabel).width(130f).right();
    }


    public void addMoney(int changeValue)
    {
        this.money += changeValue;
        String formattedValue =  this.currencyFormatter.format(this.money);
        this.setMoneyValue(formattedValue);
    }

    public String getMoneyValue() {
        return this.moneyLabel.toString();
    }

    private void setMoneyValue(String moneyValue) {
        this.moneyLabel.setText("Money: " + moneyValue);
    }
}
