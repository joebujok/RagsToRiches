package com.bujok.ragstoriches.ai.pathfinding;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.InputAdapter;
        import com.badlogic.gdx.InputProcessor;
        import com.badlogic.gdx.ai.pfa.PathSmoother;
        import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
        import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder.Metrics;
        import com.badlogic.gdx.graphics.Camera;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
        import com.badlogic.gdx.math.MathUtils;
        import com.badlogic.gdx.math.Path;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.math.Vector3;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
        import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
        import com.badlogic.gdx.utils.TimeUtils;

        import java.util.ArrayList;
        import java.util.List;

/** This test shows how the {@link IndexedAStarPathFinder} can be used on a tiled map with no diagonal movement. It also shows how
 * to use a {@link PathSmoother} on the found path to reduce the zigzag.
 *
 * @author davebaol */
public class FlatTiledAStarTest {


    private final Stage parent;

    private Texture grid = new Texture(Gdx.files.internal("gridsquare_10x10.png"));
    private Texture blank = new Texture(Gdx.files.internal("blank.png"));

    //ShapeRenderer renderer;
    //Vector3 tmpUnprojection = new Vector3();

    int lastScreenX;
    int lastScreenY;
    int endTileX;
    int endTileY;
    int startTileX;
    int startTileY;

    FlatTiledGraph map;
    List<TiledSmoothableGraphPath<FlatTiledNode>> pathList = new ArrayList<TiledSmoothableGraphPath<FlatTiledNode>>();
    TiledManhattanDistance<FlatTiledNode> heuristic;
    IndexedAStarPathFinder<FlatTiledNode> pathFinder;
    PathSmoother<FlatTiledNode, Vector2> pathSmoother;

    boolean smooth = false;

    public FlatTiledAStarTest (Stage parent)
    {
        this.parent = parent;
        //this.renderer = new ShapeRenderer();
    }

    public void create () {

        // Create the map
        map = new FlatTiledGraph();
        map.init(TestHarnessUtils.MAPSIZEX, TestHarnessUtils.MAPSIZEY);


        heuristic = new TiledManhattanDistance<FlatTiledNode>();
        pathFinder = new IndexedAStarPathFinder<FlatTiledNode>(map, true);
        pathSmoother = new PathSmoother<FlatTiledNode, Vector2>(new TiledRaycastCollisionDetector<FlatTiledNode>(this.map));


        this.updatePath();
    }

    public void render(Batch batch, boolean visible)
    {
        if (visible) {
            for (int x = 0; x < TestHarnessUtils.MAPSIZEX; x++) {
                for (int y = 0; y < TestHarnessUtils.MAPSIZEY; y++) {
                    switch (this.map.getNode(x, y).type) {
                        case FlatTiledNode.TILE_FLOOR:
                            //batch.setColor(new Color(1,1,0,0.5f));
                            break;
                        case FlatTiledNode.TILE_WALL:
                            batch.setColor(new Color(0.8f, 0.8f, 0.8f, 0.25f));
                            batch.draw(this.blank, TestHarnessUtils.XPADDING + x * TestHarnessUtils.TILEWIDTH, TestHarnessUtils.YPADDING + y * TestHarnessUtils.TILEWIDTH,
                                    TestHarnessUtils.TILEWIDTH, TestHarnessUtils.TILEWIDTH);
                            break;
                        default:
                            batch.setColor(Color.BLACK);
                            batch.draw(this.blank, TestHarnessUtils.XPADDING + x * TestHarnessUtils.TILEWIDTH, TestHarnessUtils.YPADDING + y * TestHarnessUtils.TILEWIDTH,
                                    TestHarnessUtils.TILEWIDTH, TestHarnessUtils.TILEWIDTH);
                            break;
                    }
                    batch.setColor(new Color(0.8f, 0.8f, 0.8f, 0.2f));
                    batch.draw(this.grid, TestHarnessUtils.XPADDING + x * TestHarnessUtils.TILEWIDTH, TestHarnessUtils.YPADDING + y * TestHarnessUtils.TILEWIDTH,
                            TestHarnessUtils.TILEWIDTH, TestHarnessUtils.TILEWIDTH);
                }
            }

            for (TiledSmoothableGraphPath<FlatTiledNode> path : this.pathList) {
                int nodeCount = path.getCount();
                for (int i = 0; i < nodeCount; i++) {
                    FlatTiledNode node = path.nodes.get(i);
                    if (i == 0) {
                        batch.setColor(Color.CYAN);
                    } else if (i == nodeCount - 1) {
                        batch.setColor(Color.BLUE);
                    } else {
                        batch.setColor(i % 2 == 0 ? Color.RED : Color.LIME);
                    }
                    batch.draw(this.blank, TestHarnessUtils.XPADDING + node.x * TestHarnessUtils.TILEWIDTH, TestHarnessUtils.YPADDING + node.y * TestHarnessUtils.TILEWIDTH,
                            TestHarnessUtils.TILEWIDTH, TestHarnessUtils.TILEWIDTH);
                    batch.setColor(new Color(0.9f, 0.9f, 0.9f, 0.5f));
                    batch.draw(this.grid, TestHarnessUtils.XPADDING + node.x * TestHarnessUtils.TILEWIDTH, TestHarnessUtils.YPADDING + node.y * TestHarnessUtils.TILEWIDTH,
                            TestHarnessUtils.TILEWIDTH, TestHarnessUtils.TILEWIDTH);
                }
            }
        }

//        if (smooth) {
//            renderer.end();
//            renderer.begin(ShapeType.Line);
//            float hw = width / 2f;
//            if (nodeCount > 0) {
//                FlatTiledNode prevNode = path.nodes.get(0);
//                for (int i = 1; i < nodeCount; i++) {
//                    FlatTiledNode node = path.nodes.get(i);
//                    renderer.line(node.x * width + hw, node.y * width + hw, prevNode.x * width + hw, prevNode.y * width + hw);
//                    prevNode = node;
//                }
//            }
//        }
    }

    public void updatePath()
    {
        startTileX = 23;
        startTileY = 3;
        endTileX = 6;
        endTileY = 9;
        TiledSmoothableGraphPath<FlatTiledNode> newPath = new TiledSmoothableGraphPath<FlatTiledNode>();
        pathList.add(newPath);
        this.updatePath(newPath);

        startTileX = endTileX;
        startTileY = endTileY;
        endTileX = 5;
        endTileY = 26;
        newPath = new TiledSmoothableGraphPath<FlatTiledNode>();
        pathList.add(newPath);
        this.updatePath(newPath);

        startTileX = endTileX;
        startTileY = endTileY;
        endTileX = 40;
        endTileY = 4;
        newPath = new TiledSmoothableGraphPath<FlatTiledNode>();
        pathList.add(newPath);
        this.updatePath(newPath);

        startTileX = endTileX;
        startTileY = endTileY;
        endTileX = 24;
        endTileY = 3;
        newPath = new TiledSmoothableGraphPath<FlatTiledNode>();
        pathList.add(newPath);
        this.updatePath(newPath);
    }

    public void updatePath (TiledSmoothableGraphPath<FlatTiledNode> pathToUpdate)
    {
            FlatTiledNode startNode = this.map.getNode(startTileX, startTileY);
            FlatTiledNode endNode = this.map.getNode(this.endTileX, this.endTileY);
            pathToUpdate.clear();
                this.map.startNode = startNode;
                long startTime = nanoTime();
                pathFinder.searchNodePath(startNode, endNode, heuristic, pathToUpdate);
                if (pathFinder.metrics != null) {
                    float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
                    System.out.println("----------------- Indexed A* Path Finder Metrics -----------------");
                    System.out.println("Visited nodes................... = " + pathFinder.metrics.visitedNodes);
                    System.out.println("Open list additions............. = " + pathFinder.metrics.openListAdditions);
                    System.out.println("Open list peak.................. = " + pathFinder.metrics.openListPeak);
                    System.out.println("Path finding elapsed time (ms).. = " + elapsed);
                }
                if (smooth) {
                    startTime = nanoTime();
                    pathSmoother.smoothPath(pathToUpdate);
                    if (pathFinder.metrics != null) {
                        float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
                        System.out.println("Path smoothing elapsed time (ms) = " + elapsed);
                    }
                }
    }


    private long nanoTime () {
        return pathFinder.metrics == null ? 0 : TimeUtils.nanoTime();
    }

    public List<Vector2> getPath(int pathIndex)
    {
        final List<Vector2> path = new ArrayList<Vector2>();
        final TiledSmoothableGraphPath<FlatTiledNode> graph = this.pathList.get(pathIndex);
        int nodeCount = graph.getCount();
        for (int i = 0; i < nodeCount; i++) {
            Vector2 tPos = graph.getNodePosition(i);
            path.add(new Vector2(TestHarnessUtils.convertMapVectorToWorldVector(tPos)));
        }
        return path;
    }
}