package com.bujok.ragstoriches.utils;

/**
 * Created by joebu on 09/01/2016.
 */
public class Random {

    private Random() {
    }

    public static Integer getRandInteger(Integer minInclusive, Integer maxInclusive){

        java.util.Random rand = new java.util.Random();
        return rand.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;

    }
}
