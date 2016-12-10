package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Segment2D;
import org.socialforce.geom.impl.Point2D;

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
        assertEquals(-4,circleTest.getDistance(b),0);
        assertEquals(new Vector2D(1,0),circleTest.getDirection(new Point2D(9,4)));
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
        assertEquals(Math.sqrt(2) * 4 - 5,circleTest.distanceTo(new Segment2D(a,new Point2D(-1,-1))),1e-7);
        assertEquals(new Vector2D(-0.6,-0.8),circleTest.directionTo(new Box2D(9,12,6,7)));
    }

    @Test
    public void getBoundTest(){
        assertEquals(new Box2D(-2,-1,10,10),circleTest.getBounds());
        assertEquals(new Box2D(0,0,0,0),new Circle2D(new Point2D(0,0),0).getBounds());
    }

    @Test
    public void hitsTest(){
        assertTrue(circleTest.hits(new Box2D(2,2,2,2)));
        assertTrue(circleTest.hits(new Box2D(-1,-1,1,1)));
        assertTrue(circleTest.hits(new Box2D(2,-3,2,2)));
        assertFalse(circleTest.hits(new Box2D(-2,-2,1,1)));
    }

    @Test
    public void intersectTest(){
        assertTrue(circleTest.intersects(new Box2D(2,2,2,2)));
        assertTrue(circleTest.intersects(new Box2D(-1,-1,1,1)));
        assertTrue(circleTest.intersects(new Box2D(2,-3,2,2)));
        assertFalse(circleTest.intersects(new Box2D(-2,-2,1,1)));
        assertTrue(circleTest.intersects(new Circle2D(b,1)));
        assertTrue(circleTest.intersects(new Circle2D(a,1)));
        assertFalse(circleTest.intersects(new Circle2D(a,0.5)));
    }

    @Test
    public void cloneTest(){
        assertEquals(circleTest,circleTest.clone());
        assertFalse(circleTest==circleTest.clone());
    }
}