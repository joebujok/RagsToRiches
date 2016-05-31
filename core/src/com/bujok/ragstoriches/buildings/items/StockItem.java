package com.bujok.ragstoriches.items;

/**
 * Created by Buje on 02/05/2016.
 */
public class StockItem {
    private int itemID;
    private String itemName;
    private int quantity;
    private int shopID;
    private Integer buyPrice;
    private Integer sellPrice;

    public StockItem(String itemName, int quantity)
    {
        this.itemID = -1;
        this.itemName = itemName;
        this.shopID = -1;
        this.quantity = quantity;
    }

    public StockItem(int itemID, String itemName, int shopID, int quantity, Integer buyPrice, Integer sellPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.shopID = shopID;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        if (sellPrice != null){
            this.sellPrice = sellPrice;
        }
        else this.sellPrice = Math.round( buyPrice * 1.5f );

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

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
}
