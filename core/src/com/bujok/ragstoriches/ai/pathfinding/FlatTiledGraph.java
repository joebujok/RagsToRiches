package com.bujok.ragstoriches.ai.pathfinding;

        import com.badlogic.gdx.ai.pfa.Connection;
        import com.badlogic.gdx.utils.Array;

/** A random generated graph representing a flat tiled map.
 *
 * @author davebaol */
public class FlatTiledGraph implements TiledGraph<FlatTiledNode> {

    protected Array<FlatTiledNode> nodes;

    public boolean diagonal;
    public FlatTiledNode startNode;

    public FlatTiledGraph () {
        this.nodes = new Array<FlatTiledNode>(TestHarnessUtils.MAPSIZEX * TestHarnessUtils.MAPSIZEY);
        this.diagonal = false;
        this.startNode = null;
    }

    public void init (int sizeX, int sizeY) {
        int map[][] = TestHarnessUtils.generatePathFindingMap(sizeX, sizeY);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                nodes.add(new FlatTiledNode(x, y, map[x][y], 4));
            }
        }

        // Each node has up to 4 neighbors, therefore no diagonal movement is possible
        for (int x = 0; x < sizeX; x++) {
            int idx = x * sizeY;
            for (int y = 0; y < sizeY; y++) {
                FlatTiledNode n = nodes.get(idx + y);
                if (x > 0) addConnection(n, -1, 0);
                if (y > 0) addConnection(n, 0, -1);
                if (x < sizeX - 1) addConnection(n, 1, 0);
                if (y < sizeY - 1) addConnection(n, 0, 1);
            }
        }
    }

    @Override
    public FlatTiledNode getNode (int x, int y) {
        return nodes.get(x * TestHarnessUtils.MAPSIZEY + y);
    }

    @Override
    public FlatTiledNode getNode (int index) {
        return nodes.get(index);
    }

    public int getIndex (FlatTiledNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount () {
        return nodes.size;
    }

    @Override
    public Array<Connection<FlatTiledNode>> getConnections (FlatTiledNode fromNode) {
        return fromNode.getConnections();
    }

    private void addConnection (FlatTiledNode n, int xOffset, int yOffset) {
        FlatTiledNode target = getNode(n.x + xOffset, n.y + yOffset);
        if (target.type == FlatTiledNode.TILE_FLOOR) n.getConnections().add(new FlatTiledConnection(this, n, target));
    }

}