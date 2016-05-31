package com.bujok.ragstoriches.buildings.items;

/**
 * Created by Buje on 02/05/2016.
 */
public class StockItem extends RagsItem
{

    private int shopID;
    private float buyPrice;
    private float sellPrice;

    public StockItem(int itemID, String itemName, int shopID, int quantity, float buyPrice, float sellPrice) {
        super(itemID, itemName, quantity);
        this.shopID = shopID;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
}
