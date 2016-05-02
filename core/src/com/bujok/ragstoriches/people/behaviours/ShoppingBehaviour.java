package com.bujok.ragstoriches.people.behaviours;

import com.bujok.ragstoriches.items.StockItem;
import com.bujok.ragstoriches.people.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tojoh on 02/05/2016.
 */
public class ShoppingBehaviour
{
    Person parent;

    List<String> shoppingList = new ArrayList<String>();
    List<StockItem> shoppingCart = new ArrayList<StockItem>();
    List<StockItem> boughtGoods = new ArrayList<StockItem>();


    public ShoppingBehaviour()
    {

    }

    public void shop()
    {
        // enter
        // find and pickup an item
        // go to counter
        // leave
        this.enterShop();
        for (String item : this.shoppingList)
        {
            this.collectGoods(item);
        }
        this.payForItems();
        this.leaveShop();
    }

    private void enterShop()
    {

    }

    private void collectGoods(String itemName)
    {

    }

    private void payForItems()
    {

    }

    private void leaveShop()
    {

    }
}
