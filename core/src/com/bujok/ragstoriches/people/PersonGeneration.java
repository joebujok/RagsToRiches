package com.bujok.ragstoriches.people;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.bujok.ragstoriches.NativeFunctions.Database;
import com.bujok.ragstoriches.NativeFunctions.ProductsTable;
import com.bujok.ragstoriches.NativeFunctions.StockTable;
import com.bujok.ragstoriches.buildings.items.StockItem;
import com.bujok.ragstoriches.utils.SocioClassType;

import java.util.HashMap;
import java.util.Random;

import static com.bujok.ragstoriches.utils.SocioClassType.*;

/**
 * Created by Buje on 18/05/2016.
 */
public class PersonGeneration{

    final String TAG = "PersonGeneration";
    private final Database database;

    protected int socioClass;
    protected String name;
    protected Integer age;
    protected Integer money;
    protected String titleName;
    protected String firstName;
    protected String surname;


    protected float socioGroupAPopulationPercentage = 0.05f;
    protected float socioGroupBPopulationPercentage = 0.15f;
    protected float socioGroupCPopulationPercentage = 0.3f;
    protected float socioGroupDPopulationPercentage = 0.3f;
    protected float socioGroupEPopulationPercentage = 0.2f;

    protected float socioGroupAWealthPercentage = 0.45f;
    protected float socioGroupBWealthPercentage = 0.25f;
    protected float socioGroupCWealthPercentage = 0.15f;
    protected float socioGroupDWealthPercentage = 0.1f;
    protected float socioGroupEWealthPercentage = 0.05f;

    protected float socioGroupAWealthFluctuation = 0.25f;
    protected float socioGroupBWealthFluctuation = 0.25f;
    protected float socioGroupCWealthFluctuation = 0.25f;
    protected float socioGroupDWealthFluctuation = 0.25f;
    protected float socioGroupEWealthFluctuation = 0.25f;

    protected float wealth = 500f;

    public PersonGeneration(Database database)
    {
        this.database = database;

        Gdx.app.debug(TAG, "Total float value of population = " + socioGroupAPopulationPercentage + socioGroupBPopulationPercentage + socioGroupCPopulationPercentage
              +  socioGroupDPopulationPercentage+  socioGroupEPopulationPercentage);

        float seed = MathUtils.random(0f, 1f);
        float aLB = 1f - socioGroupAPopulationPercentage;
        float bLB = aLB - socioGroupBPopulationPercentage;
        float cLB = bLB - socioGroupCPopulationPercentage;
        float dLB = cLB - socioGroupDPopulationPercentage;
        float eLB = Math.max(dLB - socioGroupEPopulationPercentage, 0f);
        if(1f >= seed && seed > aLB) {this.socioClass = SocioClassType.A;}
        else if(aLB >= seed && seed > bLB) {this.socioClass = SocioClassType.B;}
        else if(bLB >= seed && seed > cLB) {this.socioClass = SocioClassType.C;}
        else if(cLB >= seed && seed > dLB) {this.socioClass = SocioClassType.D;}
        else if(dLB >= seed && seed >= eLB) {this.socioClass = SocioClassType.E;}
        else{
            Gdx.app.debug(TAG,"seed didnt find a class, picking class D, this is most likely a bug that needs looking at!");
            this.socioClass = SocioClassType.D;
        }

        createPerson(this.socioClass);
    }

    public void createPerson(int socioClass){
        this.socioClass = socioClass;
        this.age = MathUtils.random(15,95);
        this.titleName = "Mr";
        this.firstName = this.getRandomFirstName();
        this.surname = this.getRandomLastName();
        switch(socioClass){
            case 1:
                this.money = Math.round(MathUtils.random((1f - socioGroupAWealthFluctuation)* socioGroupAWealthPercentage, (1f + socioGroupAWealthFluctuation)* socioGroupAWealthPercentage) * wealth* 100);
                break;
            case 2:
                this.money = Math.round(MathUtils.random((1f - socioGroupBWealthFluctuation)* socioGroupBWealthPercentage, (1f + socioGroupAWealthFluctuation)* socioGroupBWealthPercentage) * wealth* 100);
                break;
            case 3:
                this.money = Math.round(MathUtils.random((1f - socioGroupCWealthFluctuation)* socioGroupCWealthPercentage, (1f + socioGroupAWealthFluctuation)* socioGroupCWealthPercentage) * wealth* 100);
                break;
            case 4:
                this.money = Math.round(MathUtils.random((1f - socioGroupDWealthFluctuation)* socioGroupDWealthPercentage, (1f + socioGroupAWealthFluctuation)* socioGroupDWealthPercentage) * wealth* 100);
                break;
            case 5:
                this.money = Math.round(MathUtils.random((1f - socioGroupEWealthFluctuation)* socioGroupAWealthPercentage, (1f + socioGroupEWealthFluctuation)* socioGroupEWealthPercentage) * wealth* 100);
                break;
        }

    }

    public String getRandomFirstName()
    {
        String first = "";
        long row = MathUtils.random(0, 100);

        HashMap<Integer, StockItem> stockItems = new HashMap<Integer, StockItem>();
        Database.Result result =  database.query("Select firstname FROM nameFirst where id = " + row);
        while(result.moveToNext())
        {
            first = result.getString(0);
        }
        return first;
    }

    public String getRandomLastName()
    {
        String last = "";
        long row = MathUtils.random(0, 100);

        HashMap<Integer, StockItem> stockItems = new HashMap<Integer, StockItem>();
        Database.Result result =  database.query("Select lastname FROM nameLast where id = " + row);
        while(result.moveToNext())
        {
            last = result.getString(0);
        }
        return last;
    }

}
