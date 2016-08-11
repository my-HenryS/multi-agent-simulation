package org.socialforce.entity.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.Shape;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/8/10.
 */
public class Line2DTest {
    Line2D testline;
    double k2,b2,startX1,endX1;
    Point2D a,b;
    @Before
    public void setUp() throws Exception {
    testline = new Line2D(1,2,0,5);
        a = new Point2D(0,2);
        b = new Point2D(0,0);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(testline.contains(a));
        assertFalse(testline.contains(b));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(0,testline.getDistance(a),0);
        assertEquals(1.414,testline.getDistance(b),0.001);
    }

    @Test
    public void getReferencePoint() throws Exception {
        assertEquals(2.5,testline.getReferencePoint().getX(),0);
        assertEquals(4.5,testline.getReferencePoint().getY(),0);
    }

    @Test
    public void getBounds() throws Exception {
        assertEquals(new Box2D(0,2,5,5),testline.getBounds());
    }

    @Test
    public void moveTo() throws Exception {
        testline.moveTo(new Point2D(0,0));
        assertEquals(0,testline.getDistance(b),0);
        assertEquals(1.414,testline.getDistance(a),0.001);
    }

   @Test
    public void cloneTest() throws Exception {
       Shape cloned = testline.clone();
       assertFalse(cloned == testline);
       assertEquals(testline,cloned);           //@TODO 到底是怎么回事我也不懂了。总之是跑不通。

    }

    @Test (expected = IllegalArgumentException.class)
    public void setUpTest(){
        testline = new Line2D(3,5,1,1);
    }

}