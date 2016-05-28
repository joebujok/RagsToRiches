package com.bujok.ragstoriches.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bujok.ragstoriches.map.tiles.MapTile;

/**
 * Created by Tojoh on 5/13/2016.
 */
public class GameMap
{
    private final Stage stage;
    Texture bgrImage;

    public GameMap(Stage stage, MapTile[][] tiles, Texture bgrImage)
    {
        this.stage = stage;
        this.bgrImage = bgrImage;
    }

    public void render(float delta, Batch batch)
    {
        if (bgrImage != null)
        {
            batch.begin();
            batch.draw(this.bgrImage, (stage.getWidth() - this.bgrImage.getWidth())/2, (stage.getHeight() - this.bgrImage.getHeight())/2);
            batch.end();
        }
    }
}
