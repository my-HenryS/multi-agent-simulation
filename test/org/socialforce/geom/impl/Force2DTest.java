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

    @Before
    public void setUp(){
        testForce = new Force2D(3,5);
        time = 2;
        mass = 5;
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

}