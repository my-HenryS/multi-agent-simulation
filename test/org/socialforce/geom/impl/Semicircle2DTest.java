package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/11/21.
 */
public class Semicircle2DTest {
    Semicircle2D semicircle, semicircle_2, semicircle_3;
    @Before
    public void setUp() throws Exception {
        semicircle = new Semicircle2D(new Point2D(0,0),4,0);
        semicircle_2 = new Semicircle2D(new Point2D(0,0),4,Math.PI/2);
        semicircle_3 = new Semicircle2D(new Point2D(1,1),4,3*Math.PI/4);
    }
    @Test
    public void contains_1() throws Exception {
        assertTrue(semicircle.contains(new Point2D(0,2)));
        assertTrue(semicircle.contains(new Point2D(-1,0)));
        assertFalse(semicircle.contains(new Point2D(0,-2)));
    }

    @Test
    public void contains_2() throws Exception {
        assertTrue(semicircle_2.contains(new Point2D(-1,0)));
        assertFalse(semicircle_2.contains(new Point2D(1,0)));
        assertTrue(semicircle_2.contains(new Point2D(-1,-3)));
    }

    @Test
    public void contains_3() throws Exception {
        assertTrue(semicircle_3.contains(new Point2D(-1,-1)));
        assertFalse(semicircle_3.contains(new Point2D(-1.7,3.828)));
        assertTrue(semicircle_3.contains(new Point2D(2,-2)));
    }

}