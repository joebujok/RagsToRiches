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
import com.bujok.ragstoriches.NativeFunctions.ProductsTable;
import com.bujok.ragstoriches.NativeFunctions.StockTable;
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
        Database.Result result =  database.query("Select * FROM " + StockTable.TABLE_NAME +
                " INNER JOIN " + ProductsTable.TABLE_NAME + " ON "
                + ProductsTable.TABLE_NAME + "." + ProductsTable.KEY_PRODUCTID + " = "
                + StockTable.TABLE_NAME + "." + StockTable.KEY_PRODUCTID + " WHERE "
                + StockTable.KEY_SHOPID + " = " + this.shopID);
        while(result.moveToNext())
        {
            StockItem s = new StockItem(result.getInt(result.getColumnIndex(StockTable.KEY_PRODUCTID)),
                    result.getString(result.getColumnIndex(ProductsTable.KEY_PRODUCT)),
                    this.shopID,
                    result.getInt(result.getColumnIndex(StockTable.KEY_QUANTITYHELD)),
                    result.getFloat(result.getColumnIndex(StockTable.KEY_BUY_PRICE)),
                    result.getFloat(result.getColumnIndex(StockTable.KEY_SELL_PRICE))
            );
            stockItems.put(s.getItemID() ,s);

        }

        return stockItems;

    }

    public void buyItem(StockItem item, int quantity){
        int rowsUpdated = database.executeUpdate("UPDATE " + StockTable.TABLE_NAME + " SET "
                + StockTable.KEY_QUANTITYHELD + "=" + StockTable.KEY_QUANTITYHELD + " - " + quantity +  " WHERE "
                + StockTable.KEY_SHOPID + "= " + this.shopID + " AND " + StockTable.KEY_PRODUCTID + " = " + item.getItemID());

        if (rowsUpdated != 1) {
            Gdx.app.error("Database", rowsUpdated + " rows updated, expected 1.");
        }
        else
        {
            item.setQuantity(item.getQuantity() - quantity);
            MessageManager.getInstance().dispatchMessage(MessageType.StockLevelUpdate, item);
            MessageManager.getInstance().dispatchMessage(MessageType.CashUpdate, new Float(quantity * item.getSellPrice()));
        }

    }

    private void createStockContainers()
    {
        // add crates to the scene
        StockContainer melonCrate = new StockContainer(shopStockListing.get(StockType.MELON), new Texture(Gdx.files.internal("crates_melon.png")) );
        stage.addActor(melonCrate);
        melonCrate.setX(180);
        melonCrate.setY(110);

        StockContainer potatoCrate = new StockContainer(shopStockListing.get(StockType.POTATO), new Texture(Gdx.files.internal("crates_potatoes.png")) );
        stage.addActor(potatoCrate);
        potatoCrate.setX(180);
        potatoCrate.setY(220);

        StockContainer fishCrate = new StockContainer(shopStockListing.get(StockType.FISH), new Texture(Gdx.files.internal("crates_fish.png")) );
        stage.addActor(fishCrate);
        fishCrate.setX(795);
        fishCrate.setY(110);

        StockContainer strawbCrate = new StockContainer(shopStockListing.get(StockType.STRAWBERRY), new Texture(Gdx.files.internal("crates_strawberries.png")) );
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
                    // todo: not sure we need to do this anymore
//                    int StockLevelBeforeUpdate = shopStockListing.get(stockItem.getItemID()).getQuantity();
//                    shopStockListing.get(stockItem.getItemID()).setQuantity(StockLevelBeforeUpdate += stockItem.getQuantity());

                }
                //update the stockcontaineramount
                StockContainer stockContainer = shopContainers.get(stockItem.getItemID());
                if(stockContainer!= null){
                    // todo: not sure we need to do this anymore
//                    int stockLevelBeforeUpdate = stockContainer.getStock().getQuantity();
//                    Gdx.app.debug(TAG, "Stock Table message received, stock for " + stockContainer.getStock().getStockType() + " currently = " +stockLevelBeforeUpdate);
//                    stockContainer.setStockQuantity(stockLevelBeforeUpdate += stockItem.getQuantity());
//                    Gdx.app.debug(TAG, "Stock updated, stock for " + stockContainer.getStock().getStockType() + " is now = " + stockContainer.getStockQuantity());
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
