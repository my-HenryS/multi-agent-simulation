package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/10/9.
 */
public class Force2DTest {
    Force2D testForce;
    double time,mass;
    Point2D p1,p2,o;

    @Before
    public void setUp(){
        testForce = new Force2D(3,5);
        time = 2;
        mass = 5;
        o = new Point2D(0,0);
        p1 = new Point2D(2,0);
        p2 = new Point2D(3,5);
    }

    @Test
    public void deltaVelocity() throws Exception {
        assertEquals(new Velocity2D(1.2,2),testForce.deltaVelocity(mass,time));
    }

    @Test
    public void cloneTest() throws Exception {
        assertEquals(testForce,testForce.clone());
        assertFalse(testForce == testForce.clone());
    }

    @Test
    public void CalculateMoment() throws Exception{
        assertEquals(new Moment2D(0),testForce.CalculateMoment(p2,o));
        assertEquals(new Moment2D(10),testForce.CalculateMoment(p1,o));
    }
}