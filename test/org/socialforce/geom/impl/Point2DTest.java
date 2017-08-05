package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.Point;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Point2DTest {
    Point2D x,y,z;
    @Before
    public void setUp() throws Exception {
        x = new Point2D(0,0);
        y = new Point2D(3,4);
        z = new Point2D(0,0);
    }

    @Test
    public void getX() throws Exception {
        assertEquals(0,x.getX(),1e-7);
        assertEquals(3,y.getX(),1e-7);
    }

    @Test
    public void getY() throws Exception {
        assertEquals(0,x.getY(),1e-7);
        assertEquals(4,y.getY(),1e-7);
    }

    @Test
    public void distanceTo() throws Exception {
        assertEquals(5,x.distanceTo(y),1e-7);
        assertEquals(new Vector2D(0.6,0.8),x.directionTo(y));
    }

    @Test
    public void testClone() throws Exception {
        Point cloned = x.clone();
        assertFalse(x == cloned);
        assertTrue( x.equals(z));
        assertEquals(x,cloned);
        cloned.set(new double[]{0.1,0});
        assertNotEquals(x,cloned);
    }

    @Test
    public void getAngle() throws Exception {
        Point2D a = new Point2D(0,0);
        Point2D b = new Point2D(1,0);
        Point2D c = new Point2D(1,1);
        assertEquals((Math.PI)/4,c.getAngle(),1e-7);
    }

    @Test
    public void coordinateTransfer() throws Exception {
        Point2D a = new Point2D(0,0);
        Point2D c = new Point2D(1,1);
        Point A = a.coordinateTransfer(c,(Math.PI)/4);
        assertEquals((-1)*Math.sqrt(2),A.getX(),1e-7);
        assertEquals(0,A.getY(),1e-7);

        Point2D test = new Point2D(0,0);
        Point2D center = new Point2D(1,1);
        Point result = test.coordinateTransfer(center,7*(Math.PI)/4);
        assertEquals(0,result.getX(),1e-7);
        assertEquals((-1)*Math.sqrt(2),result.getY(),1e-7);


    }

    @Test
    public void inverseCoordinateTransfer() throws Exception {
        Point2D A = new Point2D(1*Math.sqrt(2),0);
        Point2D c = new Point2D(1,1);
        Point a = A.inverseCoordinateTransfer(c,5*(Math.PI)/4);
        assertEquals(0,a.getX(),1e-7);
        assertEquals(0,a.getY(),1e-7);

        Point2D test = new Point2D(0,Math.sqrt(2));
        Point2D center = new Point2D(1,1);
        Point result = test.inverseCoordinateTransfer(center,3*(Math.PI)/4);
        assertEquals(0,result.getX(),1e-7);
        assertEquals(0,result.getY(),1e-7);
    }

    @Test
    public void DirectionTo() throws Exception{
        assertEquals(new Vector2D(0.6,0.8),x.directionTo(y));
    }
}