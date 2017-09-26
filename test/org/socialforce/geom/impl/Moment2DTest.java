package org.socialforce.geom.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.Palstance;
import org.socialforce.geom.Shape;

import static org.junit.Assert.*;

public class Moment2DTest {
    Moment2D M0,M1,M2,M3;
    Palstance w0;
    @Before
    public void setUp() throws Exception {
        M0 = new Moment2D(10);
        M0.add(M0);
        w0=M0.deltaPalstance(0.01,1.2);
    }

    @Test
    public void name() throws Exception {
        assertEquals(2399.999999999, w0.getOmega(),0.00001);
    }

}
