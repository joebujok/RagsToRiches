package com.bujok.ragstoriches.people.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.bujok.ragstoriches.buildings.Shop;
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

    public void shop(Shop target)
    {
        // enter
        // find and pickup an item
        // go to counter
        // leave
        this.enterShop(target);
        this.browseCollectGoods(target);
        this.payForItems(target);
        this.leaveShop(target);
    }

    private void enterShop(Shop target)
    {

    }

    private void browseCollectGoods(Shop target)
    {
        List<Vector2> path = target.getBrowsePath();
        for (Vector2 poi : path)
        {
            this.parent.browseTo(poi);
        }

        if (!this.shoppingList.isEmpty())
        {
            this.findAssistant(target);
        }

    }

    private void findAssistant(Shop target)
    {
        // to add
    }

    private void payForItems(Shop target)
    {

    }

    private void leaveShop(Shop target)
    {

    }
}
