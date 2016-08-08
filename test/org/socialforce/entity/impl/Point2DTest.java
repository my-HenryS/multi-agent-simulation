package org.socialforce.entity.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.Point;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Point2DTest {
    Point2D x,y;
    @Before
    public void setUp() throws Exception {
        x = new Point2D(0,0);
        y = new Point2D(3,4);
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
    }

    @Test
    public void testClone() throws Exception {
        Point cloned = x.clone();
        assertFalse(x == cloned);
        assertEquals(x,cloned);
        cloned.set(new double[]{0.1,0});
        assertNotEquals(x,cloned);
    }

}