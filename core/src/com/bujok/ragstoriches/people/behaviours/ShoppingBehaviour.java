package com.bujok.ragstoriches.people.behaviours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StreamUtils;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.buildings.items.RagsItem;
import com.bujok.ragstoriches.buildings.items.StockContainer;
import com.bujok.ragstoriches.buildings.items.StockItem;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.utils.StockType;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tojoh on 02/05/2016.
 */
public class ShoppingBehaviour
{
    Person parent;

    HashMap<Integer, RagsItem> shoppingList = new HashMap<Integer, RagsItem>();
    HashMap<Integer, StockItem> shoppingCart = new HashMap<Integer, StockItem>();
    HashMap<Integer, StockItem> boughtGoods = new HashMap<Integer, StockItem>();

    Shop target;
    BehaviorTree<ShoppingBehaviour> tree;

    final String TAG = "Shopbehaviour";

    int currentContainer = 0;
    private Task.Status moveToStatus;

    boolean btreeRunning = false;

    public ShoppingBehaviour(Person parent)
    {
        this.parent = parent;
        this.initialiseBehaviourTree();
        this.target = parent.getCurrentShop();
        this.testharnessPopulateShoppingList();
    }

    private void testharnessPopulateShoppingList()
    {
        this.shoppingList.put(StockType.FISH, new RagsItem(StockType.FISH, "Fish", 5));
        this.shoppingList.put(StockType.MELON, new RagsItem(StockType.MELON, "Melons", 2));

        // Test failure to find something
        this.shoppingList.put(99, new RagsItem(99, "Sniper Rifle", 2));
    }

    private void initialiseBehaviourTree()
    {
        Reader reader = null;
        try
        {
            reader = Gdx.files.internal("data/shopbehaviour2.tree").reader();
            BehaviorTreeParser<ShoppingBehaviour> parser = new BehaviorTreeParser<ShoppingBehaviour>(BehaviorTreeParser.DEBUG_NONE);
            this.tree = parser.parse(reader, this);
            this.tree.reset();
        }
        finally
        {
            StreamUtils.closeQuietly(reader);
        }
    }


    public Task.Status enter(Shop target)
    {
        Gdx.app.debug(TAG, "Entering shop");

        this.parent.setVisible(true);
       // this.target = target;
        //set at constructor level for now

        return Task.Status.SUCCEEDED;
    }

    public Task.Status leave()
    {
        if (this.moveToStatus == null)
        {
            Gdx.app.debug(TAG, "Leaving shop");
            Vector2 origin = this.parent.getStartEndPosition();
            this.moveToStatus = Task.Status.RUNNING;
            this.parent.moveTo(new Vector2(origin.x, origin.y));
        }
        else if (this.moveToStatus == Task.Status.SUCCEEDED)
        {
            this.moveToStatus = null;
            this.currentContainer = 0;
            this.parent.setVisible(false);
            return Task.Status.SUCCEEDED;
        }
        return Task.Status.RUNNING;
    }

    public Task.Status findCrate()
    {
        if (this.moveToStatus == null)
        {
            Gdx.app.debug(TAG, "Finding the next crate");
            this.currentContainer++;
            StockContainer container = this.target.getContainer(this.currentContainer);
            if (container != null && container.getStock() != null)
            {
                Gdx.app.debug(TAG, "Moving to " + container.getStock().getItemName());
                this.moveToStatus = Task.Status.RUNNING;
                this.parent.moveTo(new Vector2(container.getX(), container.getY()));
            }
            // temp fix to reset current container and stop infinite loop
            else
            {
                Gdx.app.debug(TAG, "Failed to find container with stock " + this.currentContainer);
                this.moveToStatus = null;
                this.currentContainer = 0;
                return Task.Status.FAILED;
            }
        }
        else
        {
            if (this.moveToStatus == Task.Status.SUCCEEDED)
            {
                StockContainer container = this.target.getContainer(this.currentContainer);
                Gdx.app.debug(TAG, "Found " + container.getStock().getItemName());
                this.moveToStatus = null;
                return Task.Status.SUCCEEDED;
            }
        }
        return Task.Status.RUNNING;
    }

    public boolean isOpen()
    {
        return true;
    }

    public Task.Status payForItems()
    {
        if (this.moveToStatus == null)
        {
            if (!this.shoppingList.isEmpty())
            {
                Gdx.app.debug(TAG, "Couldn't find: ");
                for (RagsItem item : this.shoppingList.values())
                {
                    Gdx.app.debug(TAG, item.getItemName());
                }
            }
            else
            {
                Gdx.app.debug(TAG, "Found everything! Boom!");
            }

            final Vector2 counter = new Vector2(600f, 390f);
            Gdx.app.debug(TAG, "Moving to counter");
            this.moveToStatus = Task.Status.RUNNING;
            this.parent.moveTo(counter);
        }
        else
        {
            if (this.moveToStatus == Task.Status.SUCCEEDED)
            {
                Gdx.app.debug(TAG, "Paying for items");
                for (StockItem item : this.shoppingCart.values())
                {
                    parent.buyItem(item, this.shoppingList.get(item.getItemID()).getQuantity());
                }
                this.moveToStatus = null;
                return Task.Status.SUCCEEDED;
            }
        }
        return Task.Status.RUNNING;
    }

    public void takeItems()
    {
        Gdx.app.debug(TAG, "Taking items from the crate");
        StockContainer container = this.target.getContainer(this.currentContainer);
        if (container != null && container.getStock() != null)
        {
            this.shoppingCart.put(container.getStock().getItemID(), container.getStock());
            //this.shoppingList.remove(container.getStockID());
        }
    }


    //    public void shop(Shop target)
//    {
//        // enter
//        // find and pickup an item
//        // go to counter
//        // leave
//        this.enterShop(target);
//        this.browseCollectGoods(target);
//        this.payForItems(target);
//        this.leaveShop(target);
//    }


//    private void browseCollectGoods(Shop target)
//    {
//        List<Vector2> path = target.getBrowsePath();
//        for (Vector2 poi : path)
//        {
//            this.parent.browseTo(poi);
//        }
//
//        if (!this.shoppingList.isEmpty())
//        {
//            this.findAssistant(target);
//        }
//
//    }
//
//    private void findAssistant(Shop target)
//    {
//        // to add
//    }
//
//    private void payForItems(Shop target)
//    {
//
//    }
    public void run()
    {
        this.tree.reset();
        this.btreeRunning = true;
    }

    public boolean want()
    {
        StockContainer container = this.target.getContainer(this.currentContainer);
        if (container != null) {
            Gdx.app.debug(TAG, "Do I want " + container.getStock().getItemName() + "?");
            boolean inShoppingList = this.shoppingList.containsKey(container.getStock().getItemID());
            boolean inShoppingCart = this.shoppingCart.containsKey(container.getStock().getItemID());

            if (inShoppingList && !inShoppingCart)
            {
                Gdx.app.debug(TAG, "Yes!");
                return true;
            }
            Gdx.app.debug(TAG, "No!");
        }
        return false;
    }

    public void update(float delta)
    {
        if (this.btreeRunning)
        {
            this.tree.step();
        }
    }

    public void notifyArrivalAtTarget()
    {
        if (this.moveToStatus == Task.Status.RUNNING && this.parent.getActions().size == 0)
        {
            this.moveToStatus = Task.Status.SUCCEEDED;
        }
    }

    public Task.Status finish()
    {
        this.btreeRunning = false;
        return Task.Status.SUCCEEDED;
    }

    public Task.Status isShoppingListComplete()
    {
        Gdx.app.debug(TAG, "Have I got everything?");
        if (this.shoppingList.isEmpty())
        {
            Gdx.app.debug(TAG, "Yes!");
            return Task.Status.SUCCEEDED;
        }
        Gdx.app.debug(TAG, "No!");
        return Task.Status.FAILED;
    }

    public Task.Status isCheckedEverywhere()
    {
        Gdx.app.debug(TAG, "Have I been everywhere?");
        if (this.currentContainer == 4)
        {
            Gdx.app.debug(TAG, "Yes!");
            return Task.Status.SUCCEEDED;
        }
        Gdx.app.debug(TAG, "No!");
        return Task.Status.FAILED;
    }
}
