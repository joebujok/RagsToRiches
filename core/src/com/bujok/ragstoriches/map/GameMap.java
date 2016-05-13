package com.bujok.ragstoriches.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bujok.ragstoriches.map.tiles.MapTile;

/**
 * Created by Tojoh on 5/13/2016.
 */
public class GameMap
{
    Texture bgrImage;

    public GameMap(MapTile[][] tiles, Texture bgrImage)
    {
        this.bgrImage = bgrImage;
    }

    public void render(float delta, SpriteBatch batch)
    {
        // draw the hop background
        batch.begin();
        batch.draw(this.bgrImage, 0, 0, 1200, 720);
        batch.end();
    }
}
