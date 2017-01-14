package org.socialforce.strategy.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.impl.SimpleScene;
import org.socialforce.app.impl.StandardSceneLoader;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.Wall;
import org.socialforce.strategy.Path;

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
        SceneLoader square =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                        new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                        new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                        new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
                });
        Scene scene = square.staticScene();
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
        SceneLoader square =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                        new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                        new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                        new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
                });
        Scene scene = square.staticScene();
        aStarPathFinder = new AStarPathFinder(scene);
        agent_shape.moveTo(new Point2D(3,3));
        aStarPathFinder.applyAgent(new BaseAgent(agent_shape));
        aStarPathFinder.applyGoal(new Point2D(8,8));
        Path path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
        aStarPathFinder.applyGoal(new Point2D(8,8));
        path = aStarPathFinder.plan_for();
        System.out.println(path.toString());
    }

    @Test
    public void testAssert(){
        SceneLoader square =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                        new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                        new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                        new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
                });
        Scene scene = square.staticScene();
        aStarPathFinder = new AStarPathFinder(scene);
        try{
            aStarPathFinder.plan_for();
        }catch(java.lang.IllegalStateException e){
            System.out.println("Exception caught");
        }

    }
}