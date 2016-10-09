package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/10/9.
 */
public class Velocity2DTest {
    Velocity2D testVelocity;
    double time;
    @Before
    public void setUp(){
        testVelocity = new Velocity2D(4,8);
        time = 2;
    }

    @Test
    public void deltaDistance() throws Exception {
        assertEquals(new Vector2D(8,16),testVelocity.deltaDistance(time));
    }

    @Test
    public void cloneTest() throws Exception {
        assertEquals(testVelocity,testVelocity.clone());
        assertFalse(testVelocity == testVelocity.clone());
    }

}