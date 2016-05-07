package com.bujok.ragstoriches.ai.pathfinding;

import java.util.Arrays;

/**
 * Created by Tojoh on 06/05/2016.
 */
public class TestHarnessUtils
{

    public static final int MAPSIZEX = 47;
    public static final int MAPSIZEY = 33;

    public static int[][] generatePathFindingMap(int sizeX, int sizeY)
    {
        int[][] map = new int[sizeX][sizeY];

        for (int x = 0; x < sizeX; x++)
        {
            for (int y = 0; y < sizeY; y++)
            {
                if (x <= 2 || y <= 2 || x >= sizeX - 3 || y >= sizeY - 3)
                {
                    map[x][y] = TiledNode.TILE_WALL;
                }
                else {
                    map[x][y] = TiledNode.TILE_FLOOR;
                }
            }
        }
        TestHarnessUtils.maskBackCupboards(map, sizeX, sizeY);
        TestHarnessUtils.maskCounterTop(map, sizeX, sizeY);
        TestHarnessUtils.maskCrates(map, sizeX, sizeY);
        return map;
    }

    private static void maskCrates(int[][] map, int sizeX, int sizeY)
    {
        for (int x = 0; x < sizeX; x++)
        {
            for (int y = 0; y < sizeY; y++)
            {
                if ((y >= 5 && y <= 7) && (x <= 13 || x >=34))
                {
                    map[x][y] = TiledNode.TILE_WALL;
                }
                else if ((y >= 11 && y <= 13) && (x <= 13 || x >=34))
                {
                    map[x][y] = TiledNode.TILE_WALL;
                }
            }
        }
    }

    private static void maskCounterTop(int[][] map, int sizeX, int sizeY)
    {
//        for (int x = 0; x < sizeX; x++)
//        {
//            for (int y = 0; y < sizeY; y++)
//            {
//                if (x <= 1 || y <= 1 || x >= sizeX - 2 || y >= sizeY - 2)
//                {
//                    map[x][y] = TiledNode.TILE_WALL;
//                }
//                else {
//                    map[x][y] = TiledNode.TILE_FLOOR;
//                }
//            }
//        }
    }

    private static void maskBackCupboards(int[][] map, int sizeX, int sizeY)
    {
//        for (int x = 0; x < sizeX; x++)
//        {
//            for (int y = 0; y < sizeY; y++)
//            {
//                if (x <= 1 || y <= 1 || x >= sizeX - 2 || y >= sizeY - 2)
//                {
//                    map[x][y] = TiledNode.TILE_WALL;
//                }
//                else {
//                    map[x][y] = TiledNode.TILE_FLOOR;
//                }
//            }
//        }
    }

}
