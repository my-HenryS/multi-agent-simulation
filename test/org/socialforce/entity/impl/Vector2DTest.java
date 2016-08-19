package org.socialforce.entity.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.Vector;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/7.
 */
public class Vector2DTest {
    Vector2D a,b,zero;

    @Before
    public void setUp() throws Exception {
        a = new Vector2D(3,4);
        b = new Vector2D(-3,2);
        zero = new Vector2D(0,0);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void dimension() throws Exception {
        assertEquals(2,a.dimension());

    }

    @Test
    public void basicAdd() throws Exception {
        a.add(b);
        assertEquals(new Vector2D(0,6),a);
    }

    @Test
    public void basicSub() throws Exception {
        a.sub(b);
        assertEquals(new Vector2D(6,2),a);
    }

    @Test (expected = IllegalArgumentException.class)
    public void convertTest() throws Exception{
        Vector notA2DVector=null;
        a.quiteConvert(notA2DVector);
    }

    @Test
    public void basicScale() throws Exception {
        a.scale(10);
        assertNotEquals(new Vector2D(0.3,0.4),a);
        assertEquals(new Vector2D(30,40),a);
    }

    @Test
    public void epsilonEquals() throws Exception {
        Vector2D small = new Vector2D(0.05,-0.1);
        assertTrue("equals within the epsilon 0.1",zero.epsilonEquals(small,0.1));
        assertFalse("not equals within the epsilon 0.05",zero.epsilonEquals(small,0.05));
        assertFalse("not equals within the epsilon 0.07",zero.epsilonEquals(small,0.07));
    }

    @Test
    public void basicDot() throws Exception {
        assertEquals(8,a.dot(b),1e10-7);
    }

    @Test
    public void get() throws Exception {
        double[] atest = {4,3};
        a.get(atest);
        assertEquals(atest[0],3,1e10-7);
        assertEquals(atest[1],4,1e10-7);
    }

    @Test
    public void overflowSet() throws Exception {
        double [] vals = {5.5,6.6,9.9};
        b.set(vals);
        assertEquals(new Vector2D(5.5,6.6), b);
    }

    @Test
    public void notEnoughSet() throws Exception {
        double [] vals = {6.6};
        b.set(vals);
        assertEquals(new Vector2D(6.6,2),b);
    }

    @Test
    public void testClone() throws Exception {
        Vector cloned = b.clone();
        assertFalse("b == b.clone() should be false",b == cloned);
        assertEquals("b.equals(b.clone()) should be true",b,cloned);
    }

    @Test
    public void otherSetTest() throws Exception {
        Vector vector = new Vector2D(-1,-5);
        a.set(vector);
        assertNotEquals(new Vector2D(3,4),a);
        assertEquals(new Vector2D(-1,-5),a);
    }

}