package com.bujok.ragstoriches.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.bujok.ragstoriches.map.tiles.MapMaterial;
import com.bujok.ragstoriches.map.tiles.MapTile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Tojoh on 5/13/2016.
 */
public class MapGenerator
{
    private static Map<String, MapMaterial> defaultMaterial = new HashMap<String, MapMaterial>();

    Random rnd = new Random();

    private enum MapType
    {
        OUTSIDE_TEMPERATE_VILLAGE("outside.ground.grass"), INSIDE_SHOP("inside.floor.wooden");

        final String defaultMaterialID;

        private MapType(String defaultMaterialID)
        {
            this.defaultMaterialID = defaultMaterialID;
        }

        public String getDefaultMaterialID()
        {
            return this.defaultMaterialID;
        }
    }

    private enum MapSize
    {
        SMALL(10), MEDIUM(20), LARGE(40), RANDOM(-1);

        final int maxDimension;

        private MapSize(int maxDimension)
        {
            this.maxDimension = maxDimension;
        }

        public int getMaxDimension()
        {
            return this.maxDimension;
        }
    }

    static
    {
        MapGenerator.defaultMaterial.put("outside.ground.grass", new MapMaterial("outside.ground.grass", new Texture(Gdx.files.internal("ground_grass.png")), false));
        MapGenerator.defaultMaterial.put("inside.floor.wooden", new MapMaterial("inside.floor.wooden", new Texture(Gdx.files.internal("floor_wooden.png")), false));
    }

    public MapGenerator()
    {

    }

    public GameMap generateMap(MapType type, MapSize size)
    {
        int[] sizeXY = this.generateMapSize(size, true);
        MapTile[][] tiles = this.generateMapTiles(sizeXY, type);
        return this.createMap(tiles);
    }

    private GameMap createMap(MapTile[][] tiles)
    {
        return new GameMap(tiles);
    }


    private MapTile[][] generateMapTiles(int[] sizeXY, MapType type)
    {
        MapTile[][] tileset = new MapTile[sizeXY[0]][sizeXY[1]];
        for(int y = 0; y < tileset[0].length; y++)
        {
            for(int x = 0; x < tileset.length; x++)
            {
                tileset[x][y] = this.getDefaultTile(x, y, type);
            }
        }
        return tileset;
    }

    private MapTile getDefaultTile(int x, int y, MapType type)
    {
        String materialID = type.getDefaultMaterialID();
        return new MapTile(x, y, MapGenerator.defaultMaterial.get(materialID));
    }

    public int[] generateMapSize(MapSize size, boolean randomize)
    {
        MapSize sizeToUse = size;
        switch(size)
        {
            case RANDOM:
                int index = rnd.nextInt(3);
                sizeToUse = MapSize.values()[index];
                break;
            default:
                break;
        }

        int x = sizeToUse.getMaxDimension();
        int y = sizeToUse.getMaxDimension();
        if (randomize)
        {
            x = rnd.nextInt(x) + 1;
            y = rnd.nextInt(y) + 1;
        }

        return new int[]{x, y};
    }
}
