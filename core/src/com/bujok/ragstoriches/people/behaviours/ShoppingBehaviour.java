package com.bujok.ragstoriches.people.behaviours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StreamUtils;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.buildings.items.StockItem;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.buildings.items.StockContainer;

import java.io.Reader;
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

    Shop target;
    BehaviorTree<ShoppingBehaviour> tree;

    final String TAG = "Shopbehaviour";

    //btree test harness
    final String want = "Fish";
    int currentContainer = 0;

    public ShoppingBehaviour(Person parent)
    {
        this.parent = parent;
        this.initialiseBehaviourTree();
    }

    private void initialiseBehaviourTree()
    {
        Reader reader = null;
        try
        {
            reader = Gdx.files.internal("data/shopbehaviour2.tree").reader();
            BehaviorTreeParser<ShoppingBehaviour> parser = new BehaviorTreeParser<ShoppingBehaviour>(BehaviorTreeParser.DEBUG_HIGH);
            this.tree = parser.parse(reader, this);
            this.tree.reset();
        }
        finally
        {
            StreamUtils.closeQuietly(reader);
        }
    }


    public void enter(Shop target)
    {
        Gdx.app.debug(TAG, "Entering shop");
        this.target = target;
    }

    public void leave()
    {
        Gdx.app.debug(TAG, "Leaving shop");
    }

    public void findCrate() {
        Gdx.app.debug(TAG, "Finding the nearest crate");
        this.currentContainer++;
        StockContainer container = this.target.getContainer(this.currentContainer);
        if (container != null)
        {
            Gdx.app.debug(TAG, "Found " + container.getStockType());
            this.parent.moveTo(new Vector2(container.getX(), container.getY()));
        }
    }

    public boolean isOpen()
    {
        return true;
    }

    public void payForItems()
    {
        Gdx.app.debug(TAG, "Paying for items");
    }

    public void takeItems()
    {
        Gdx.app.debug(TAG, "Taking items from the crate");
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
        this.tree.step();
    }

    public boolean want()
    {
        Gdx.app.debug(TAG, "Picking up item");
        StockContainer container = this.target.getContainer(this.currentContainer);
        if (container != null)
        {
            Gdx.app.debug(TAG, "Do I want " + container.getStockType() + "?");
            if (container.getStockType().equals(this.want))
            {
                Gdx.app.debug(TAG, "Yes!");
                return true;
            }
            Gdx.app.debug(TAG, "No!");
        }
        return false;
    }
}
