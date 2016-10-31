package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.Point;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/10/3.
 */
public class ComplexBox2DTest {
    ComplexBox2D complexBox,tempBox;
    Box2D clipper;

    @Before
    public void setUp() throws Exception {
        Box2D clip = new Box2D(-2,-2,5,5);
        clipper = new Box2D(-1,-5,1,10);
        complexBox = (ComplexBox2D)clipper.clip(clip);
        tempBox = new ComplexBox2D();
    }

    @Test
    public void contains() throws Exception {
        assertTrue(complexBox.contains(new Point2D(-1.5,-1.5)));
        assertTrue(complexBox.contains(new Point2D(1,1)));
        assertFalse(complexBox.contains(new Point2D(-0.5,0)));
        assertFalse(complexBox.contains(new Point2D(-2,-3)));
        assertFalse(complexBox.contains(new Point2D(-3,-2)));
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(Math.sqrt(2),complexBox.getDistance(new Point2D(-3,-3)).length(),1e-7);
    }

    @Test
    public void getReferencePoint() throws Exception {
        assertEquals(new Point2D(0.5,0.5),complexBox.getReferencePoint());
    }

    @Test
    public void moveTo() throws Exception {
        complexBox.moveTo(new Point2D(5,2.5));
        assertEquals(new Point2D(5,2.5),complexBox.getReferencePoint());
        assertTrue(complexBox.contains(new Point2D(2.5,1)));
        assertFalse(complexBox.contains(new Point2D(1,1)));
    }

    @Test
    public void hitsTest() throws Exception{
        assertTrue(complexBox.hits(clipper));
        assertFalse(complexBox.hits(new Box2D(-0.9,-5,0.8,10)));
    }

    @Test
    public void DictionaryTest() throws Exception{
        assertEquals(complexBox.getBoxDictionary()[0][0],new Point2D(-2,-2));
        assertEquals(complexBox.getBoxDictionary()[1][0],new Point2D(-1,3));
        assertEquals(complexBox.getBoxDictionary()[0][1],new Point2D(0,-2));
        assertEquals(complexBox.getBoxDictionary()[1][1],new Point2D(3,3));
    }

    @Test
    public void EqualTest() throws Exception{
        tempBox.SetWithArray(complexBox.getBoxDictionary());
        assertEquals(complexBox,tempBox);
        tempBox.SetWithArray(new Point[][]{{new Point2D(-2,-2),new Point2D(0,-2)},{new Point2D(-1,3),new Point2D(3,3)}});
        assertEquals(complexBox,tempBox);
        tempBox.SetWithArray(new Point[][]{{new Point2D(0,-2),new Point2D(-2,-2)},{new Point2D(3,3),new Point2D(-1,3)}});
        assertEquals(complexBox,tempBox);
        tempBox.SetWithArray(new Point[][]{{new Point2D(0,-2),new Point2D(0,-2)},{new Point2D(3,3),new Point2D(3,3)}});
        assertNotEquals(complexBox,tempBox);
        tempBox = (ComplexBox2D) complexBox.clone();
        assertEquals(tempBox,complexBox);
    }
}