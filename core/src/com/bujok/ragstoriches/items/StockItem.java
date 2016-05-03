package com.bujok.ragstoriches.items;

/**
 * Created by Buje on 02/05/2016.
 */
public class StockItem {
    private int itemID;
    private String itemName;
    private int quantity;
    private int shopID;

    public StockItem(int itemID, String itemName, int shopID, int quantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.shopID = shopID;
        this.quantity = quantity;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
