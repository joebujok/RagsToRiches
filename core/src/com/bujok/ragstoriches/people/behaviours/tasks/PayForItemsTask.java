package com.bujok.ragstoriches.people.behaviours.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.bujok.ragstoriches.people.behaviours.ShoppingBehaviour;

/**
 * Created by Tojoh on 03/05/2016.
 */
public class PayForItemsTask extends LeafTask<ShoppingBehaviour>
{

    @Override
    public Status execute ()
    {
        ShoppingBehaviour shoppingBehaviour = getObject();

        shoppingBehaviour.payForItems();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<ShoppingBehaviour> copyTo (Task<ShoppingBehaviour> task)
    {
        return task;
    }
}
