package com.bujok.ragstoriches.map.tiles;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Tojoh on 5/13/2016.
 */
public class MapMaterial
{
    String description;

    Texture texture;

    boolean blocker;

    public MapMaterial(String description, Texture texture, boolean blocker)
    {
        this.description = description;
        this.texture = texture;
        this.blocker = blocker;
    }

    public String getDescription() {
        return description;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isBlocker() {
        return blocker;
    }
}
