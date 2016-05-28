package com.bujok.ragstoriches.people.behaviours.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.people.behaviours.ShoppingBehaviour;
import com.bujok.ragstoriches.screens.ShopScreen;

/**
 * Created by Tojoh on 03/05/2016.
 */
public class EnterTask  extends LeafTask<ShoppingBehaviour>
{

    @Override
    public Status execute ()
    {
        ShoppingBehaviour shoppingBehaviour = getObject();


        // check if open, if so enter.
        if (shoppingBehaviour.isOpen())
        {
            return shoppingBehaviour.enter(null);
        }
        return Status.FAILED;
    }

    @Override
    protected Task<ShoppingBehaviour> copyTo (Task<ShoppingBehaviour> task)
    {
        return task;
    }
}
