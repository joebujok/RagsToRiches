package com.bujok.ragstoriches.people;

/**
 * Created by joebu on 07/01/2016.
 */
public class person {

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




    public person(String name, Integer age) {
        this.mName = name;
        this.mAge = age;
    }
}
