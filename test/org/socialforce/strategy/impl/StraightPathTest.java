package org.socialforce.strategy.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/10.
 */
public class StraightPathTest {
    StraightPath path;
    Point a,b,c,d;
    /*
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
        assertEquals(d,path.getCurrentGoal(d));                   //TODO 随着之后A*算法的加入 目标点判断的算法可能改变

    }

    @Test
    public void getStartPoint() throws Exception {
        assertEquals(a,path.getStartPoint());
    }

    @Test
    public void getGoal() throws Exception {
        assertEquals(d,path.getGoal());
    }
*/
}