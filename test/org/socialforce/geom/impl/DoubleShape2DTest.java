package org.socialforce.geom.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/9/24 0024.
 */
public class DoubleShape2DTest {
    DoubleShape2D test1;
    Point2D p,q;

    @Before
    public void setUp() throws Exception {
        Box2D box2D = new Box2D(0,0,3,2);
        Circle2D circle2D = new Circle2D(new Point2D(1.5,0),1.5);
        test1 = new DoubleShape2D(box2D,circle2D);
        p = new Point2D(1.5,1);
        q = new Point2D(3,-1.5);
    }

    @Test
    public void contains() throws Exception {
        assertEquals(true,test1.contains(p));
        assertEquals(false,test1.contains(q));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(-1,test1.getDistance(p),0.01);
        assertEquals(1.5*(Math.sqrt(2)-1),test1.getDistance(q),0.01);
    }

    @Test
    public void getDirection() throws Exception {
        assertEquals(new Vector2D(0,-1),test1.getDirection(p));
        assertEquals(new Vector2D(1/Math.sqrt(2),-1/Math.sqrt(2)),test1.getDirection(q));
    }

    @Test
    public void getReferencePoint() throws Exception {
        assertEquals(new Point2D(1.5,1),test1.getReferencePoint());
    }

    @Test
    public void getBounds() throws Exception {
    }

    @Test
    public void hits() throws Exception {
        assertEquals(true,test1.hits(new Box2D(new Point2D(-1,-1),new Point2D(4,4))));
        assertEquals(true,test1.hits(new Box2D(new Point2D(1.5,-3),new Point2D(4,0))));
        assertEquals(false,test1.hits(new Box2D(new Point2D(1.5,-3),new Point2D(4,-2))));
    }

    @Test
    public void getLinkVector() throws Exception {
        assertEquals(new Vector2D(0,-1),test1.getLinkVector());
    }

    @Test
    public void moveTo() throws Exception {
        Point2D location = new Point2D(4,-1);
        test1.moveTo(location);
        assertEquals(location,test1.getMainShape().getReferencePoint());
        assertEquals(new Point2D(4,-2),test1.getSubShape().getReferencePoint());
    }

    @Test
    public void expandBy() throws Exception {
    }


}