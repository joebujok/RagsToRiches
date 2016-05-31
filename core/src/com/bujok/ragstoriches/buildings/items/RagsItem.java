package com.bujok.ragstoriches.buildings.items;

/**
 * Created by Tojoh on 5/31/2016.
 */
public class RagsItem
{
    protected int itemID;
    protected String itemName;
    protected int quantity;


    public RagsItem(int itemID, String itemName, int quantity)
    {
        this.itemID = itemID;
        this.itemName = itemName;
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
}
