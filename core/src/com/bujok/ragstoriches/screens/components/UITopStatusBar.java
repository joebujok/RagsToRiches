package com.bujok.ragstoriches.screens.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bujok.ragstoriches.utils.RagsUIUtility;

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
    private final Label moneyLabel;
    private final Label timeLabel;
    private final Table topPanel;

    // for some reason the currency formatter is not adding the pound sign. Set to dollars for now.
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);

    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm EEEE MMMM yyyy");

    int money = 0;

    public UITopStatusBar(Stage stage)
    {
        float padding = 6f;
        this.stage = stage;

        this.currencyFormatter.setMinimumIntegerDigits(4);
        Calendar cal = Calendar.getInstance();
        this.timeLabel = RagsUIUtility.getInstance().createDefaultLabel("");
        this.moneyLabel = RagsUIUtility.getInstance().createDefaultLabel("");

        this.addMoney(0);

        float menuBarHeight = 34f;
        this.topPanel = RagsUIUtility.getInstance().createPanelTable(this.stage.getWidth(), menuBarHeight, "panel_blue");
        this.topPanel.setPosition(0, stage.getHeight() - menuBarHeight);
        this.topPanel.defaults().expand().fill();
        this.topPanel.columnDefaults(0).left();
        this.topPanel.columnDefaults(1).right();
        stage.addActor(this.topPanel);

        this.topPanel.padLeft(padding);
        this.topPanel.add(timeLabel).left();

        this.topPanel.padRight(padding);
        this.topPanel.add(this.moneyLabel).width(156f).right();
    }

    public void setTimeValue(long timeValue)
    {
        this.timeLabel.setText(sdf.format(timeValue));
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
        this.moneyLabel.setText(moneyValue);
    }

    public void update(float delta)
    {
        this.setTimeValue(Calendar.getInstance(Locale.UK).getTimeInMillis());
    }
}
