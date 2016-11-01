package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/11/1.
 */
public class Rectangle2DTest {
    Rectangle2D testRec,temp;
    Point2D a,b,c,d,e;
    @Before
    public void setUp() throws Exception {
        testRec = new Rectangle2D(new Point2D(3,3),4,6,Math.PI/4);
        a = new Point2D(3+Math.sqrt(2)/2,3-2.5*Math.sqrt(2));
        b = new Point2D(3,3);
        c = new Point2D(0,0);
        d = new Point2D(1,2);
        e = new Point2D(4,5);
    }

    @Test
    public void contains() throws Exception {
        //assertTrue(testRec.contains(a));
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

    }

    @Test
    public void getBounds() throws Exception {

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
    public void spin() throws Exception {

    }

    @Test
    public void getAngle() throws Exception {

    }

    @Test
    public void getScale() throws Exception {

    }

    @Test
    public void equals() throws Exception {

    }

}