package com.bujok.ragstoriches.people;

import com.bujok.ragstoriches.R;
import com.bujok.ragstoriches.db.DBContract;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by joebu on 07/01/2016.
 */
public class Shopper extends Person {


    //money in pence
    private Integer mMoney;

    public Shopper(String name, Integer age) {
        super(name, age);
    }

    public Shopper(String name) {
        super(name);
        Random rand = new Random();
        Integer pounds = rand.nextInt((95 - 15) + 1) + 15;
        Integer pence = rand.nextInt((99 - 0) + 1) + 0;
        mMoney = (pounds * 100) + pence;


    }

    public Integer getMoney() {
        return mMoney;
    }

    public void setMoney(Integer mMoney) {
        this.mMoney = mMoney;
    }
    public String getMoneyString(){
        String str = mMoney.toString();
        String pence = str.substring(str.length() - 2);
        String pounds = str.substring(0,str.length() - 2);
        str = pounds + "."+ pence;
        return str;
    }


}
