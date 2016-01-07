package com.bujok.ragstoriches.people;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by joebu on 07/01/2016.
 */
public class Person {

    private String mName;
    private Integer mAge;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAge(Integer mAge) {
        this.mAge = mAge;
    }


    public Person(String name){
        Random rand = new Random();
        mAge = rand.nextInt((95 - 15) + 1) + 15;

    }

    public Person(String name, Integer age) {
        this.mName = name;
        this.mAge = age;
    }
}
