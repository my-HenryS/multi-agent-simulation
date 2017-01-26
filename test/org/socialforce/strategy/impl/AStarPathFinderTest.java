package org.socialforce.strategy.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Applications.ApplicationForECStrategy;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.Wall;
import org.socialforce.strategy.Path;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by sunjh1999 on 2016/10/23.
 */
public class AStarPathFinderTest {
    AStarPathFinder aStarPathFinder;
    Point goal;
    Circle2D agent_shape = new Circle2D(new Point2D(0,0), 0.486/2);
    Scene scene;


    @Before
    public void setUp() throws Exception {
        goal = new Point2D(10,1.5);
        SceneLoader loader =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                        new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                        new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                        new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1))),
                        new Wall(new Box2D(new Point2D(5, 5), new Point2D(20, 6)))
                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(6,1,8,1))));
        loader.readParameterSet(parameters);
        scene = loader.readScene(new ApplicationForECStrategy()).get(0);
    }

    @Test
    public void testMap() throws Exception {
        double[][]map=new double[][]{{0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 0, 1, 0},{0, 0, 1, 1, 0}, {0, 0, 0, 0, 0}};
        aStarPathFinder = new AStarPathFinder(map,  new BaseAgent(agent_shape) , new Point2D(3,3));
        Path path = aStarPathFinder.plan_for(new Point2D(3,3));
        System.out.println(path.toString(new Point2D(1,1)));
    }

    @Test
    public void testSceneAfterMap() throws Exception {
        double[][]map=new double[][]{{0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 0, 1, 0},{0, 0, 1, 1, 0}, {0, 0, 0, 0, 0}};
        aStarPathFinder = new AStarPathFinder(map,  new BaseAgent(agent_shape) , new Point2D(3,3));
        aStarPathFinder = new AStarPathFinder(scene, agent_shape);
        Path path = aStarPathFinder.plan_for(goal);
        System.out.println(path.toString(new Point2D(10,10)));
    }

    @Test
    public void length(){
        aStarPathFinder = new AStarPathFinder(scene, agent_shape);
        Path path = aStarPathFinder.plan_for(goal);
        System.out.println(path.length(new Point2D(20,7)));
        System.out.println(((AStarPath)path).Length(new Point2D(20,7)));
    }



}