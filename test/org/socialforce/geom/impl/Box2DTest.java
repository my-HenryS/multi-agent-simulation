package org.socialforce.geom.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.Shape;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/9.
 */
public class Box2DTest {
        Box2D testBox;
        Point2D a,b,c,d,pointStrat,pointEnd;

    @Before
    public void setUp() throws Exception{
       testBox = new Box2D(0,0,3,4);
        a = new Point2D(1,1);
        b = new Point2D(6,8);
        c = new Point2D(-4,-3);
        d = new Point2D(1,-5);
        pointStrat = new Point2D(3,3);
        pointEnd = new Point2D(10,10);
    }
    @Test
    public void contains() throws Exception {
        assertEquals(true,testBox.contains(a));
        assertEquals(false,testBox.contains(b));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(0,testBox.getDistance(a).length(),0);
        assertEquals(new Vector2D(0,0),testBox.getDistance(a));
        assertEquals(5,testBox.getDistance(b).length(),0);
        assertEquals(new Vector2D(3,4),testBox.getDistance(b));
        assertEquals(5,testBox.getDistance(c).length(),0);
        assertEquals(new Vector2D(-4,-3),testBox.getDistance(c));
        assertEquals(5,testBox.getDistance(d).length(),0);
        assertEquals(new Vector2D(0,-5),testBox.getDistance(d));
        a = new Point2D(1,9);
        assertEquals(5,testBox.getDistance(a).length(),0);
        a = new Point2D(-4,7);
        assertEquals(5,testBox.getDistance(a).length(),0);
        a = new Point2D(-5,2);
        assertEquals(5,testBox.getDistance(a).length(),0);
        a = new Point2D(6,-4);
        assertEquals(5,testBox.getDistance(a).length(),0);
        a = new Point2D(8,2);
        assertEquals(5,testBox.getDistance(a).length(),0);
    }

    @Test
    public void getReferencePoint() throws Exception {
        Point2D center = new Point2D(1.5,2);
        assertEquals(center,testBox.getReferencePoint());
    }

    @Test
    public void getBounds() throws Exception {
        assertEquals(testBox,testBox.getBounds());
    }

    @Test
    public void hits() throws Exception {
        Box2D x = new Box2D(1,1,2,2);
        Box2D y = new Box2D(-1,-1,3,3);
        Box2D z = new Box2D(10,10,2,2);
        assertEquals(true,testBox.hits(x));
        assertEquals(true,testBox.hits(y));
        assertEquals(false,testBox.hits(z));
    }

    @Test
    public void moveTo() throws Exception {
    Point2D destination = new Point2D(5,7);
        testBox.moveTo(destination);
        assertEquals(destination,testBox.getReferencePoint());
        assertEquals(false,testBox.contains(a));
        assertEquals(true,testBox.contains(b));
        Point2D center = new Point2D(1.5,2);
        assertNotEquals(0,testBox.getReferencePoint().distanceTo(center).length(),0);
    }

    @Test
    public void testClone() throws Exception {
        Shape cloned = testBox.clone();
        assertEquals(cloned,testBox);
        assertFalse(cloned == testBox);

    }

    @Test
    public void getStartPoint() throws Exception {
    assertEquals(new Point2D(0,0),testBox.getStartPoint());
    }

    @Test
    public void getEndPoint() throws Exception {
        assertEquals(new Point2D(3,4),testBox.getEndPoint());
    }

    @Test
    public void getSize() throws Exception {
        Assert.assertEquals(new Vector2D(3,4),testBox.getSize());
    }

    @Test
    public void intersect() throws Exception {
    Box2D temp = new Box2D(1,1,3,4);
        Box2D temp1 = new Box2D(1,1,1,1);
        assertEquals(new Box2D(1,1,2,3),testBox.intersect(temp));
        assertEquals(temp1,testBox.intersect(temp1));
    }

    @Test
    public void negativeWeidthTest() throws Exception{
        assertEquals(testBox,new Box2D(3,0,-3,4));
        assertEquals(testBox,new Box2D(0,4,3,-4));
        assertEquals(testBox,new Box2D(3,4,-3,-4));
    }

    @Test
    public void expandTest(){
        assertEquals(new Box2D(0,0,3,4),testBox);
        testBox.expandTo(10);
        assertNotEquals(new Box2D(0,0,3,4),testBox);
        assertEquals(new Box2D(-3.5,0,10,4),testBox);
        testBox.setxAxisExpanded(false);
        testBox.expandTo(10);
        assertEquals(new Box2D(-3.5,-3,10,10),testBox);
    }


}