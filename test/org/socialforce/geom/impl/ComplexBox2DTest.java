package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/10/3.
 */
public class ComplexBox2DTest {
    ComplexBox2D complexBox;
    Box2D clipper;

    @Before
    public void setUp() throws Exception {
        Box2D clip = new Box2D(-2,-2,5,5);
        clipper = new Box2D(-1,-5,1,10);
        complexBox = (ComplexBox2D)clipper.clip(clip);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(complexBox.contains(new Point2D(-1.5,-1.5)));
        assertTrue(complexBox.contains(new Point2D(1,1)));
        assertFalse(complexBox.contains(new Point2D(-0.5,0)));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(Math.sqrt(2),complexBox.getDistance(new Point2D(-3,-3)),1e-7);
    }

    @Test
    public void getReferencePoint() throws Exception {

    }

    @Test
    public void moveTo() throws Exception {

    }

}