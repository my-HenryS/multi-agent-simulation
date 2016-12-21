package org.socialforce.model.impl;


import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.impl.AStarPathFinder;

/**
 * Created by sunjh1999 on 2016/10/23.
 */
public class AStarPathFinderTest {
    AStarPathFinder aStarPathFinder;
    Point start_point;
    Point goal;
    Circle2D agent_shape = new Circle2D();
    @Before
    public void setUp() throws Exception {
        start_point = new Point2D(0,0);
        goal = new Point2D(2,2);
        agent_shape.moveTo(new Point2D(4,4));
        agent_shape.setRadius(0.484);
        SquareRoomLoader square = new SquareRoomLoader();
        Scene scene = square.readScene();
        aStarPathFinder = new AStarPathFinder(scene, new BaseAgent(agent_shape), goal);
        /*
        start_point = new Point2D(0,0);
        goal = new Point2D(0,0);
        agent_shape.moveTo(new Point2D(20,20));               此种设定会出现行人规划路径避开障碍物的情况
        agent_shape.setRadius(0.484);
         */
    }

    @Test
    public void testMap() throws Exception {
        double[][]map=new double[][]{{0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 0, 1, 0},{0, 0, 1, 1, 0}, {0, 0, 0, 0, 0}};
        aStarPathFinder = new AStarPathFinder(map,  new BaseAgent(agent_shape) , goal);
        Path path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
    }

    @Test
    public void testScene() throws Exception {
        Path path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
    }

    @Test
    public void testApply() throws Exception {
        aStarPathFinder.applyGoal(new Point2D(8,8));
        Path path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
        agent_shape.moveTo(new Point2D(3,3));
        aStarPathFinder.applyAgent(new BaseAgent(agent_shape));
        path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
    }

    @Test
    public void testApply2() throws Exception {
        SquareRoomLoader square = new SquareRoomLoader();
        Scene scene = square.readScene();
        aStarPathFinder = new AStarPathFinder(scene);
        aStarPathFinder.applyGoal(new Point2D(8,8));
        agent_shape.moveTo(new Point2D(3,3));
        aStarPathFinder.applyAgent(new BaseAgent(agent_shape));
        Path path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
    }

    @Test
    public void testAssert() throws Exception {
        SquareRoomLoader square = new SquareRoomLoader();
        Scene scene = square.readScene();
        aStarPathFinder = new AStarPathFinder(scene);
        aStarPathFinder.plan_for();
    }
}