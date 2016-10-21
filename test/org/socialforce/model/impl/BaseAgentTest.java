package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/18.
 */
public class BaseAgentTest {
    BaseAgent baseAgent;
    Circle2D circle = new Circle2D();
    Point2D initPoint;
    @Before
    public void setUp() throws Exception {
        initPoint = new Point2D(3,4);
        circle.moveTo(initPoint);
        circle.setRadius(5);
        baseAgent = new BaseAgent(circle);
        baseAgent.view  = circle;
    }
    @Test
    public void getShape() throws Exception {
        
    }

    @Test
    public void getView() throws Exception {

    }

}