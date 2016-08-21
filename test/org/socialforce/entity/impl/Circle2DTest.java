package org.socialforce.entity.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/8/8.
 */
public class Circle2DTest {
    Circle2D circleTest = new Circle2D();
    Point2D initPoint,a,b;
    @Before
    public void setup()throws Exception{
        initPoint = new Point2D(3,4);
        circleTest.moveTo(initPoint);
        circleTest.setRadius(5);
        a = new Point2D(-1,0);
        b = new Point2D(3,5);
    }
    @Test
    public void contains() throws Exception {

        assertEquals(true,circleTest.contains(b));
        assertEquals(false,circleTest.contains(a));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(0,circleTest.getDistance(b),0);
        assertEquals(4*Math.sqrt(2)-5,circleTest.getDistance(a),0.01);
    }

    @Test
    public void getReferencePoint() throws Exception {
    assertEquals(initPoint,circleTest.getReferencePoint());
    }

    @Test
    public void getRadius() throws Exception {
    assertEquals(5,circleTest.getRadius(),0);
    }

    @Test
    public void moveTo() throws Exception {
    Point2D move = new Point2D(0,0);
        circleTest.moveTo(move);
        assertEquals(move,circleTest.getReferencePoint());
        assertEquals(false,circleTest.contains(b));
    }

    @Test
    public void testDistanceBetweenShapes() throws Exception {
        assertEquals(Math.sqrt(2) * 4 - 5,circleTest.distanceTo(new Line2D(a,new Point2D(-1,-1))),1e-7);
    }
}