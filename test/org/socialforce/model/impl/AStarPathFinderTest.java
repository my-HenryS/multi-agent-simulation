package org.socialforce.model.impl;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/23.
 */
public class AStarPathFinderTest {
    AStarPathFinder aStarPathFinder = new AStarPathFinder(new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}});
    Point start_point;
    Point goal;
    @Before
    public void setUp() throws Exception {
        start_point = new Point2D(0,0);
        goal = new Point2D(2,2);
    }

    @Test
    public void plan() throws Exception {
        AStarPath path = aStarPathFinder.plan_for(start_point, goal);
        System.out.println(path.toString());
    }

    @Test
    public void map_initiate() throws Exception {

    }

}