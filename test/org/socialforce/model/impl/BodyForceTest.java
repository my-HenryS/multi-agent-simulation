package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
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
        initPoint2 = new Point2D(3,4.1);
        circle.moveTo(initPoint);
        circle.setRadius(0.243);
        circle2.moveTo(initPoint2);
        circle2.setRadius(0.243);
        circle3.moveTo(initPoint2);
        circle3.setRadius(0.243);
        baseAgent = new BaseAgent(circle);
        targetAgent = new BaseAgent(circle2);
        targetAgent2 = new BaseAgent(circle3);

    }
    @Test
    public void hasForce() throws Exception {
        assertTrue(bodyForce.hasForce(targetAgent,baseAgent));
        //assertFalse(bodyForce.hasForce(targetAgent2,baseAgent));

    }

    @Test
    public void getForce() throws Exception {
        System.out.println(bodyForce.getForce(targetAgent,baseAgent).toString());
        System.out.println(targetAgent.getShape().distanceTo(baseAgent.getShape()));
        baseAgent.currVelocity = new Velocity2D(1,0);
        targetAgent2.currVelocity = new Velocity2D(0,1);
        System.out.println(bodyForce.getForce(targetAgent2,baseAgent).toString());
    }

}