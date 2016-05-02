package com.bujok.ragstoriches.buildings;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bujok.ragstoriches.NativeFunctions.DBContract;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.items.StockItem;
import com.bujok.ragstoriches.messages.MessageType;
import com.bujok.ragstoriches.shop.StockContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Buje on 02/05/2016.
 */
public class Shop extends Building implements Telegraph {
    private int shopID;
    private String shopName;
    private HashMap<String, StockItem> shopStockListing;
    private HashMap<String, StockContainer> shopContainers;

    public Shop(Stage stage, RagsGame game, int shopID) {
        super(stage, game);
        this.shopID = shopID;
        this.shopStockListing = getShopItems();
        this.shopContainers = new HashMap<String, StockContainer>();
        this.createStockContainers();
        //subscribe to messages relating to stocktable changes.
        MessageManager.getInstance().addListener(this, MessageType.StockLevelUpdate);
    }


    public  HashMap<String, StockItem> getShopItems()
    {
        HashMap<String, StockItem> stockItems = new HashMap<String, StockItem>();
        Database.Result result =  database.query("Select * FROM " + DBContract.StockTable.TABLE_NAME +
                " INNER JOIN " + DBContract.ProductsTable.TABLE_NAME + " ON "
                + DBContract.ProductsTable.TABLE_NAME + "." + DBContract.ProductsTable.KEY_PRODUCTID + " = "
                + DBContract.StockTable.TABLE_NAME + "." + DBContract.StockTable.KEY_PRODUCTID + " WHERE "
                + DBContract.StockTable.KEY_SHOPID + " = " + shopID);
        while(result.moveToNext()){
            StockItem s = new StockItem(result.getInt(result.getColumnIndex(DBContract.StockTable.KEY_PRODUCTID)),
                    result.getString(result.getColumnIndex(DBContract.ProductsTable.KEY_PRODUCT)),
                    shopID,
                    result.getInt(result.getColumnIndex(DBContract.StockTable.KEY_QUANTITYHELD)));
            stockItems.put(s.getItemName() ,s);

        }

        return stockItems;

    }

    public void buyItem(int itemID, int quantity){
        int rowsUpdated = database.executeUpdate("UPDATE " + DBContract.StockTable.TABLE_NAME + " SET "
                + DBContract.StockTable.KEY_QUANTITYHELD + "=" + DBContract.StockTable.KEY_QUANTITYHELD + " - " + quantity +  " WHERE "
                + DBContract.StockTable.KEY_SHOPID + "= " + this.shopID + " AND " + DBContract.StockTable.KEY_PRODUCTID + " = " + itemID);

        if (rowsUpdated != 1) {
            Gdx.app.error("Database", rowsUpdated + " rows updated, expected 1.");
        }
        else{
            Database.Result r = database.query("SELECT " + DBContract.ProductsTable.KEY_PRODUCT + " FROM " + DBContract.ProductsTable.TABLE_NAME
            + " WHERE " + DBContract.KEY_PRODUCTID + " = " + itemID);
            String stockName = r.getString(1);
            StockItem stockItem = new StockItem(itemID, stockName,shopID,quantity * -1);
            MessageManager.getInstance().dispatchMessage(MessageType.StockLevelUpdate,stockItem);
        }

    }

    private void createStockContainers()
    {
        // add crates to the scene
        StockContainer melonCrate = new StockContainer("Melons", shopStockListing.get("Melon").getQuantity(), new Texture(Gdx.files.internal("crates_melon.png")) );
        stage.addActor(melonCrate);
        melonCrate.setX(180);
        melonCrate.setY(110);

        StockContainer potatoCrate = new StockContainer("Potatoes", shopStockListing.get("Potato").getQuantity(), new Texture(Gdx.files.internal("crates_potatoes.png")) );
        stage.addActor(potatoCrate);
        potatoCrate.setX(180);
        potatoCrate.setY(220);

        StockContainer fishCrate = new StockContainer("Fish", shopStockListing.get("Fish").getQuantity(), new Texture(Gdx.files.internal("crates_fish.png")) );
        stage.addActor(fishCrate);
        fishCrate.setX(795);
        fishCrate.setY(110);

        StockContainer strawbCrate = new StockContainer("Strawberries", shopStockListing.get("Strawberry").getQuantity(), new Texture(Gdx.files.internal("crates_strawberries.png")) );
        stage.addActor(strawbCrate);
        strawbCrate.setX(795);
        strawbCrate.setY(220);

        this.shopContainers.put("melon", melonCrate);
        this.shopContainers.put("potato", potatoCrate);
        this.shopContainers.put("fish", fishCrate);
        this.shopContainers.put("strawb", strawbCrate);
    }

    public StockContainer getContainer(String containerName)
    {
        return this.shopContainers.get(containerName);
    }

    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message){
            case MessageType.StockLevelUpdate:
                Object extra = msg.extraInfo;
                return true;


        }
        return false;
    }
}
