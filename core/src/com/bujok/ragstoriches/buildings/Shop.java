package com.bujok.ragstoriches.buildings;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bujok.ragstoriches.NativeFunctions.DBContract;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.buildings.items.StockItem;
import com.bujok.ragstoriches.messages.MessageType;
import com.bujok.ragstoriches.buildings.items.StockContainer;
import com.bujok.ragstoriches.utils.StockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Buje on 02/05/2016.
 */
public class Shop extends Building implements Telegraph {

    final String TAG = "Shop";

    private int shopID;
    private String shopName;
    private HashMap<Integer, StockItem> shopStockListing;
    private HashMap<Integer, StockContainer> shopContainers;

    public Shop(Stage stage, RagsGame game, int shopID) {
        super(stage, game);
        this.shopID = shopID;
        this.shopStockListing = getShopItems();
        this.shopContainers = new HashMap<Integer, StockContainer>();
        this.createStockContainers();
        //subscribe to messages relating to stocktable changes.
        MessageManager.getInstance().addListener(this, MessageType.StockLevelUpdate);
    }


    public  HashMap<Integer, StockItem> getShopItems()
    {
        HashMap<Integer, StockItem> stockItems = new HashMap<Integer, StockItem>();
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
            stockItems.put(s.getItemID() ,s);

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
            String stockName = null;
            while(r.moveToNext()){
                stockName = r.getString(0);
            }

            int costPerItem = 1;
            StockItem stockItem = new StockItem(itemID, stockName,shopID,quantity * -1);
            MessageManager.getInstance().dispatchMessage(MessageType.StockLevelUpdate,stockItem);
            MessageManager.getInstance().dispatchMessage(MessageType.CashUpdate, new Integer(quantity * costPerItem));
        }

    }

    private void createStockContainers()
    {
        // add crates to the scene
        StockContainer melonCrate = new StockContainer(1,"Melons", shopStockListing.get(StockType.MELON).getQuantity(), new Texture(Gdx.files.internal("crates_melon.png")) );
        stage.addActor(melonCrate);
        melonCrate.setX(180);
        melonCrate.setY(110);

        StockContainer potatoCrate = new StockContainer(2,"Potatoes", shopStockListing.get(StockType.POTATO).getQuantity(), new Texture(Gdx.files.internal("crates_potatoes.png")) );
        stage.addActor(potatoCrate);
        potatoCrate.setX(180);
        potatoCrate.setY(220);

        StockContainer fishCrate = new StockContainer(3,"Fish", shopStockListing.get(StockType.FISH).getQuantity(), new Texture(Gdx.files.internal("crates_fish.png")) );
        stage.addActor(fishCrate);
        fishCrate.setX(795);
        fishCrate.setY(110);

        StockContainer strawbCrate = new StockContainer(4,"Strawberries", shopStockListing.get(StockType.STRAWBERRY).getQuantity(), new Texture(Gdx.files.internal("crates_strawberries.png")) );
        stage.addActor(strawbCrate);
        strawbCrate.setX(795);
        strawbCrate.setY(220);

        this.shopContainers.put(StockType.MELON, melonCrate);
        this.shopContainers.put(StockType.POTATO, potatoCrate);
        this.shopContainers.put(StockType.FISH, fishCrate);
        this.shopContainers.put(StockType.STRAWBERRY, strawbCrate);
    }

    public StockContainer getContainer(int containerName)
    {
        return this.shopContainers.get(containerName);
    }

    @Override
    public boolean handleMessage(Telegram msg)
    {
        boolean result = false;
        switch (msg.message){
            case MessageType.StockLevelUpdate:
                StockItem stockItem = (StockItem) msg.extraInfo;

                // update the stocklisting shop var
                if(stockItem.getShopID() == this.shopID){
                    int StockLevelBeforeUpdate = shopStockListing.get(stockItem.getItemID()).getQuantity();
                    shopStockListing.get(stockItem.getItemID()).setQuantity(StockLevelBeforeUpdate += stockItem.getQuantity());

                }
                //update the stockcontaineramount
                StockContainer stockContainer = shopContainers.get(stockItem.getItemID());
                if(stockContainer!= null){
                    int stockLevelBeforeUpdate = stockContainer.getStockQuantity();
                    Gdx.app.debug(TAG, "Stock Table message received, stock for " + stockContainer.getStockType() + " currently = " +stockLevelBeforeUpdate);
                    stockContainer.setStockQuantity(stockLevelBeforeUpdate += stockItem.getQuantity());
                    Gdx.app.debug(TAG, "Stock updated, stock for " + stockContainer.getStockType() + " is now = " + stockContainer.getStockQuantity());
                }
                result = true;
                break;
        }
        return result;
    }

    public List<Vector2> getBrowsePath()
    {
        // hardcoded for now.
        List<Vector2> path = new ArrayList<Vector2>();
        path.add(new Vector2(200, 200));
        path.add(new Vector2(500, 200));
        path.add(new Vector2(500, 250));
        path.add(new Vector2(200, 300));
        path.add(new Vector2(500, 300));
        return path;
    }

    public HashMap<Integer, StockItem> getShopStockListing() {
        return shopStockListing;
    }
}
