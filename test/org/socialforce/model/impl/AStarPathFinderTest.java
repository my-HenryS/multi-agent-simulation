package org.socialforce.model.impl;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Path;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/23.
 */
public class AStarPathFinderTest {
    AStarPathFinder aStarPathFinder = new AStarPathFinder(new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}});
    Point start_point;
    Point goal;
    Circle2D agent_shape = new Circle2D();
    @Before
    public void setUp() throws Exception {
        start_point = new Point2D(0,0);
        goal = new Point2D(2,2);
        agent_shape.moveTo(new Point2D(4,4));
        agent_shape.setRadius(0.484);
        /*
        start_point = new Point2D(0,0);
        goal = new Point2D(0,0);
        agent_shape.moveTo(new Point2D(20,20));               此种设定会出现行人规划路径避开障碍物的情况
        agent_shape.setRadius(0.484);
         */
    }

    @Test
    public void plan_for() throws Exception {
        AStarPath path = aStarPathFinder.plan_for(start_point, goal);
        System.out.println(path.toString());
    }

    @Test
    public void plan() throws Exception {
        SquareRoomLoader square = new SquareRoomLoader();
        Scene scene = square.readScene();
        Path path = aStarPathFinder.plan(scene, new BaseAgent(agent_shape) , goal);
        System.out.println(path.toString());

    }



}