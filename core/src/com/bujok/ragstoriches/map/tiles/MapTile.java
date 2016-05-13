package com.bujok.ragstoriches.map.tiles;

/**
 * Created by Tojoh on 5/13/2016.
 */
public class MapTile
{
    private int x, y;
    private MapMaterial type;

    public MapTile(int x, int y, MapMaterial type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MapMaterial getType() {
        return type;
    }
}
