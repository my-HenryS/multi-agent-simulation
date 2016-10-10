package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.BodyForce;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/9.
 */
public class BodyForceTest {
    BaseAgent baseAgent, targetAgent, targetAgent2;
    Circle2D circle = new Circle2D(), circle2 = new Circle2D(), circle3 = new Circle2D();
    Point2D initPoint, initPoint2;
    BodyForce bodyForce = new BodyForce();
    @Before
    public void setUp() throws Exception{
        initPoint = new Point2D(3,4);
        initPoint2 = new Point2D(10,10);
        circle.moveTo(initPoint);
        circle.setRadius(5);
        circle2.moveTo(initPoint2);
        circle2.setRadius(5);
        circle3.moveTo(initPoint2);
        circle3.setRadius(1);
        baseAgent = new BaseAgent(circle);
        baseAgent.view  = circle;
        targetAgent = new BaseAgent(circle2);
        targetAgent2 = new BaseAgent(circle3);

    }
    @Test
    public void hasForce() throws Exception {
        assertTrue(bodyForce.hasForce(baseAgent,targetAgent));
        assertFalse(bodyForce.hasForce(baseAgent,targetAgent2));       

    }

    @Test
    public void getForce() throws Exception {

    }

}