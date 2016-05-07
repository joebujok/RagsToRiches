package com.bujok.ragstoriches.ai.pathfinding;

        import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

/** Graph interface representing a generic tiled map.
 *
 * @param <N> Type of node, either flat or hierarchical, extending the {@link TiledNode} class
 *
 * @author davebaol */
public interface TiledGraph<N extends TiledNode<N>> extends IndexedGraph<N> {

    public void init (int sizeX, int sizeY);

    public N getNode (int x, int y);

    public N getNode (int index);

}