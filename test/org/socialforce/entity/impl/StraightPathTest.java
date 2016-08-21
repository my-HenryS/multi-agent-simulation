package org.socialforce.entity.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.Point;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/20.
 */
public class StraightPathTest {
    StraightPath path;
    Point a,b,c,d;
    @Before
    public void setUp() throws Exception {
        a = new Point2D(0,0);
        b = new Point2D(0,1);
        c = new Point2D(2,1);
        d = new Point2D(2,0);
        path = new StraightPath(a,b,c,d);
    }

    @Test
    public void getCurrentGoal() throws Exception {
        assertEquals(b,path.getCurrentGoal(a));
        assertEquals(c,path.getCurrentGoal(b));
        assertEquals(c,path.getCurrentGoal(a));
        assertEquals(c,path.getCurrentGoal(b));
        assertFalse(path.done());
        assertEquals(d,path.getCurrentGoal(c));
        assertFalse(path.done());
        assertEquals(d,path.getCurrentGoal(d));
        assertTrue(path.done());
        assertEquals(d,path.getCurrentGoal(d));

    }

}