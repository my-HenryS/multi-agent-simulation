package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2017/1/29.
 */
public class Polygon2DTest {
    Point2D[] nods;
    Polygon2D testP,testP2;

    @Before
    public void setUp() throws Exception{
        nods = new Point2D[]{new Point2D(0,0),new Point2D(5,0),new Point2D(7,4),new Point2D(5,8),new Point2D(0,8),new Point2D(-2,4)};
        //testP = new Polygon2D();
        testP = new Polygon2D(nods);
    }


    @Test
    public void sortTest() throws Exception {
        Point2D[] sorted = testP.sort(nods);
        System.out.println(sorted);
    }

    @Test
    public void contains() throws Exception {

    }

    @Test
    public void getDistance() throws Exception {

    }

    @Test
    public void getDirection() throws Exception {

    }

    @Test
    public void getReferencePoint() throws Exception {

    }

    @Test
    public void getBounds() throws Exception {

    }

    @Test
    public void hits() throws Exception {

    }

    @Test
    public void moveTo() throws Exception {

    }

    @Test
    public void cloneTest() throws Exception {

    }

}