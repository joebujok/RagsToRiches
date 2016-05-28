package com.bujok.ragstoriches.screens.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.bujok.ragstoriches.utils.RagsUIUtility;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tojoh on 11/05/2016.
 */
public class UISideBar {

    private Label headerLabel;
    private final Button[] butArray = new Button[14];
    private Table totalTable;
    private Stage stage;
    private Button showBlueSidebarButton;
    private Button showRedSidebarButton;
    private boolean visible;

    int money = 0;

    public UISideBar(Stage stage)
    {
        this.stage = stage;

        this.initialiseTable();
        this.initialiseShowButtons();
    }

    private void initialiseShowButtons()
    {
        this.showBlueSidebarButton = RagsUIUtility.getInstance().createToggleButton("A", "blue");
        this.showBlueSidebarButton.setPosition(44, 20);
        this.showBlueSidebarButton.setWidth(80f);
        this.showBlueSidebarButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                UISideBar.this.setVisible(!UISideBar.this.visible);
            }
        });
        this.stage.addActor(this.showBlueSidebarButton);

        this.showRedSidebarButton = RagsUIUtility.getInstance().createToggleButton("B", "blue");
        this.showRedSidebarButton.setPosition(130, 20);
        this.showRedSidebarButton.setWidth(80f);
        this.showRedSidebarButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                // todo: show sidebar b
                //UISideBar.this.setVisible(!UISideBar.this.visible);
            }
        });
        this.stage.addActor(this.showRedSidebarButton);

    }

    private void initialiseTable()
    {
        this.headerLabel = RagsUIUtility.getInstance().createDefaultLabel("Sidebar A");

        float menuBarWidth = 200f;
        float menuBarHeight = 560f;
        float menuBarHeaderHeight = 46f;
        float padding = 10f;

        this.totalTable = RagsUIUtility.getInstance().createPanelTable(menuBarWidth, menuBarHeight, "panel_blue");
        Table contentTable = RagsUIUtility.getInstance().createPanelTable(menuBarWidth, menuBarHeight - menuBarHeaderHeight, "panel_grey");
        totalTable.addActor(contentTable);

        totalTable.setPosition(30, 40);

        totalTable.defaults().expandY().top();
        totalTable.padTop(padding);
        totalTable.padLeft(padding);
        totalTable.add(this.headerLabel);    // Row 0, column 0.

        contentTable.defaults().pad(4f).align(Align.center);
        contentTable.columnDefaults(0).width(80f);
        contentTable.columnDefaults(1).width(80f);
        butArray[0] = RagsUIUtility.getInstance().createDefaultButton("1", "blue");
        contentTable.add(butArray[0]);
        butArray[1] = RagsUIUtility.getInstance().createDefaultButton("2", "blue");
        contentTable.add(butArray[1]);
        contentTable.row();
        butArray[2] = RagsUIUtility.getInstance().createDefaultButton("3", "blue");
        contentTable.add(butArray[2]);
        butArray[3] = RagsUIUtility.getInstance().createDefaultButton("4", "blue");
        contentTable.add(butArray[3]);
        contentTable.row();
        butArray[4] = RagsUIUtility.getInstance().createDefaultButton("5", "blue");
        contentTable.add(butArray[4]);
        butArray[5] = RagsUIUtility.getInstance().createDefaultButton("6", "blue");
        contentTable.add(butArray[5]);
        contentTable.row();
        butArray[6] = RagsUIUtility.getInstance().createDefaultButton("7", "blue");
        contentTable.add(butArray[6]);
        butArray[7] = RagsUIUtility.getInstance().createDefaultButton("8", "blue");
        contentTable.add(butArray[7]);
        contentTable.row();
        butArray[8] = RagsUIUtility.getInstance().createDefaultButton("9", "blue");
        contentTable.add(butArray[8]);
        butArray[9] = RagsUIUtility.getInstance().createDefaultButton("10", "blue");
        contentTable.add(butArray[9]);
        contentTable.row();
        butArray[10] = RagsUIUtility.getInstance().createDefaultButton("11", "blue");
        contentTable.add(butArray[10]);
        butArray[11] = RagsUIUtility.getInstance().createDefaultButton("12", "blue");
        contentTable.add(butArray[11]);
        contentTable.row();
        butArray[12] = RagsUIUtility.getInstance().createDefaultButton("13", "blue");
        contentTable.add(butArray[12]);
        butArray[13] = RagsUIUtility.getInstance().createDefaultButton("14", "blue");
        contentTable.add(butArray[13]);
    }

    public void setVisible(boolean visible)
    {
        if (visible)
        {
            this.stage.addActor(this.totalTable);
        }
        else
        {
            this.totalTable.remove();
        }
        this.resetButtonOrders();
        this.visible = visible;
    }

    private void resetButtonOrders()
    {
        showBlueSidebarButton.remove();
        showRedSidebarButton.remove();
        this.stage.addActor(showBlueSidebarButton);
        this.stage.addActor(showRedSidebarButton);
    }
}
