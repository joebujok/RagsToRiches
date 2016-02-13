package com.bujok.ragstoriches.utils;

import com.bujok.ragstoriches.building.Shop;
import com.bujok.ragstoriches.people.Shopper;

import java.util.ArrayList;

/**
 * Created by Buje on 13/02/2016.
 */
public class Globals {

    private static Globals instance;

    // Global variable
    private ArrayList<Shopper> shopperList;
    private ArrayList<Shop> shopList;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
    //getters and setters
    public void createShopperList(){
        this.shopperList = new ArrayList<Shopper>();
    }
    public ArrayList<Shopper> getShopperList(){
        return this.shopperList;
    }

    public void createShopList() {
        this.shopList = new ArrayList<Shop>();;
    }

    public ArrayList<Shop> getShopList() {
        return shopList;
    }

}
