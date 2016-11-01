package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/11/1.
 */
public class Rectangle2DTest {
    Rectangle2D testRec,temp;
    Box2D testBox;
    Point2D a,b,c,d,e;
    @Before
    public void setUp() throws Exception {
        testRec = new Rectangle2D(new Point2D(3,3),4,6,Math.PI/4);
        temp = new Rectangle2D(new Point2D(3,3),6,4,0);
        testBox = new Box2D(0,1,6,4);
        a = new Point2D(3+Math.sqrt(2)/2,3-2.5*Math.sqrt(2));
        b = new Point2D(3,3);
        c = new Point2D(0,0);
        d = new Point2D(1,2);
        e = new Point2D(4,4);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(testRec.contains(a));
        assertTrue(testRec.contains(b));
        assertFalse(testRec.contains(c));
        assertFalse(testRec.contains(d));
        assertTrue(testRec.contains(e));
    }

    @Test
    public void getDistance() throws Exception {

    }

    @Test
    public void getReferencePoint() throws Exception {
        assertEquals(new Point2D(3,3),testRec.getReferencePoint());
        testRec.moveTo(c);
        assertEquals(c,testRec.getReferencePoint());
    }

    @Test
    public void getBounds() throws Exception {
        assertEquals(new Box2D(new Point2D(3-2.5*Math.sqrt(2),3-2.5*Math.sqrt(2)),new Point2D(3+2.5*Math.sqrt(2),3+2.5*Math.sqrt(2))),testRec.getBounds());
        testRec.spin(Math.PI/4);
        assertEquals(new Box2D(0,1,6,4),testRec.getBounds());
        testRec.spin(Math.PI/2);
        assertEquals(new Box2D(1,0,4,6),testRec.getBounds());

    }

    @Test
    public void hits() throws Exception {

    }

    @Test
    public void moveTo() throws Exception {

    }

    @Test
    public void cloneTest() throws Exception {

    }


    @Test
    public void getAngle() throws Exception {
        assertEquals(Math.PI/4,testRec.getAngle(),0);
        testRec.spin(50);
        assertEquals(Math.PI/4+50,testRec.getAngle(),0);
    }

    @Test
    public void getScale() throws Exception {

    }

    @Test
    public void equals() throws Exception {
        assertEquals(testRec,new Rectangle2D(new Point2D(3,3),6,4,Math.PI*3/4));
    }

}