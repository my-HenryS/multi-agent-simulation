package org.socialforce.geom.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class Ellipse2DTest {

    Ellipse2D ellipseTest,test1,test2;
    Point2D center,p,q,m,testPoint;

    @Before
    public void setUp() throws Exception {
        center = new Point2D(0.5,0.5);
        ellipseTest = new Ellipse2D(0.68,0.40,center,(Math.PI)/4);
        test1 = new Ellipse2D(1,0.5,new Point2D(0,0),0);
        p = new Point2D(0.5,0.6);
        q = new Point2D(1.5,0.5);      //一般的椭圆外一点
        m = new Point2D(1.0,0);      //短轴延长线上的点
        testPoint = new Point2D(0.44735,0.44735);
    }

    @Test
    public void getReferencePoint() throws Exception {
        assertEquals(center,ellipseTest.getReferencePoint());
    }

    @Test
    public void moveTo() throws Exception {
        Point2D point = new Point2D(1.5,0.5);
        ellipseTest.moveTo(point);
        assertEquals(point,ellipseTest.getReferencePoint());
        assertEquals(false,ellipseTest.contains(p));
    }

    @Test
    public void contains() throws Exception {
        assertEquals(true,ellipseTest.contains(p));
        assertEquals(false,ellipseTest.contains(q));
        assertEquals(false,test1.contains(testPoint));
        System.out.print(testPoint.getX()*testPoint.getX()+testPoint.getY()*testPoint.getY()/0.5/0.5);
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(0.3071,ellipseTest.getDistance(m),0.01);
        assertEquals(0.4806,ellipseTest.getDistance(q),0.01);
        System.out.print(test1.getDistance(testPoint));
    }

    @Test
    public void getDirection() throws Exception {
        assertEquals(new Vector2D(1*Math.sqrt(0.5),(-1)*Math.sqrt(0.5)),ellipseTest.getDirection(m));
    }


    @Test
    public void distanceTo() throws Exception {
        //assertEquals(0.9421,ellipseTest.distanceTo(new Segment2D(new Point2D(2.0,0),new Point2D(2.0,2.0))),0.01);
        //assertEquals(0.9421,ellipseTest.distanceTo(new Box2D(2.0,0,2.0,2.0)),0.01);  //最近点位于Box的边上
        //assertEquals(0.4806,ellipseTest.distanceTo(new Box2D(1.5,0,0.5,0.5)),0.01);  //最近是Box的顶点
        //assertEquals(1.2639,ellipseTest.distanceTo(new Rectangle2D(new Point2D(4,1*(Math.sqrt(3))),2*(Math.sqrt(3)),2,(Math.PI)/6)),0.01);
        //assertEquals(0.4679,ellipseTest.distanceTo(new Circle2D(new Point2D(-1,0.5),0.5)),0.01);    //椭圆与圆相离（最短距离）
        //assertEquals(-0.1156,ellipseTest.distanceTo(new Circle2D(new Point2D(1.1,0),0.5)),0.01);     //椭圆与圆相交（交集部分的最长距离）
        //assertEquals(0.8842,ellipseTest.distanceTo(new Ellipse2D(0.68,0.40,new Point2D(2.5,0.5),3*(Math.PI)/4)),0.01);  //椭圆相离
        //assertEquals(-0.1158,ellipseTest.distanceTo(new Ellipse2D(0.68,0.40,new Point2D(1.5,0.5),3*(Math.PI)/4)),0.01);  //椭圆相交
        double a[] = new double[]{1,2,3,11,24,-2};
        Arrays.sort(a);
        System.out.print(a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]);

    }

    @Test
    public void directionTo() throws Exception {
        //assertEquals(new Vector2D(-1.0,0),ellipseTest.directionTo(new Segment2D(new Point2D(2.0,0),new Point2D(2.0,2.0))));
        //assertEquals(new Vector2D((-1)*Math.sqrt(0.5),1*Math.sqrt(0.5)),ellipseTest.directionTo(new Circle2D(m,0.5)));
        double value[] = new double[2];
        //ellipseTest.directionTo(new Ellipse2D(0.68,0.40,new Point2D(2.5,0.5),3*(Math.PI)/4)).get(value);  //椭圆相离
        //assertEquals(-1,value[0],1.0e-7);
        //assertEquals(0,value[1],1.0e-7);
        ellipseTest.directionTo(new Ellipse2D(0.68,0.40,new Point2D(1.5,0.5),3*(Math.PI)/4)).get(value);  //椭圆相交
        //assertEquals(-1,value[0],1.0e-7);
        //assertEquals(0,value[1],1.0e-7);
    }

    @Test
    public void getBounds() throws Exception {
        Box2D box = (Box2D) ellipseTest.getBounds();
        assertEquals(-0.0579,box.getXmin(),0.01);
        assertEquals(-0.0578,box.getYmin(),0.01);
        assertEquals(1.0579,box.getXmax(),0.01);
        assertEquals(1.0578,box.getYmax(),0.01);
        assertEquals(new Box2D(0.10,-0.18,0.80,1.36),new Ellipse2D(0.68,0.40,center,3*(Math.PI)/2).getBounds());
    }

    @Test
    public void hits() throws Exception {
        assertTrue(ellipseTest.hits(new Box2D(0.30,1.05,2,2)));
        assertTrue(ellipseTest.hits(new Box2D(-2.05,-1.70,2,2)));
        assertTrue(ellipseTest.hits(new Box2D(-1.5,-2.05,2,2)));
        assertFalse(ellipseTest.hits(new Box2D(1.05,-1.7,2,2)));

    }

    @Test
    public void intersects() throws Exception {
        //assertTrue(ellipseTest.intersects(new Segment2D(new Point2D(0,0),new Point2D(0,2))));
        //assertFalse(ellipseTest.intersects(new Segment2D(new Point2D(0,0),new Point2D(1,0))));
        assertTrue(ellipseTest.intersects(new Box2D(0,0,1,1)));
        assertFalse(ellipseTest.intersects(new Box2D(1.0,-0.6,1,1)));
        assertTrue(ellipseTest.intersects(new Rectangle2D(new Point2D(1.0,2.0),2*(Math.sqrt(3)),2,(Math.PI)/3)));
        assertFalse(ellipseTest.intersects(new Rectangle2D(new Point2D(3.0,1*(Math.sqrt(3))),2*(Math.sqrt(3)),2,(Math.PI)/6)));
        assertTrue(ellipseTest.intersects(new Circle2D(new Point2D(0.4,-0.4),0.4)));
        assertFalse(ellipseTest.intersects(new Circle2D(new Point2D(1.0,0),0.3)));
        assertTrue(ellipseTest.intersects(new Ellipse2D(0.68,0.40,new Point2D(-0.5,0.5),(-1/6)*(Math.PI))));
        assertFalse(ellipseTest.intersects(new Ellipse2D(0.68,0.40,new Point2D(2.0,0.5),3*(Math.PI)/4)));
    }

    @Test
    public void testClone() throws Exception{
        //assertEquals(ellipseTest.clone(),ellipseTest);
        assertFalse(ellipseTest==ellipseTest.clone());
    }

    @Test
    public void getProjection() throws Exception{
        Ellipse2D ellipse1 = new Ellipse2D(2,1,new Point2D(1,0),Math.PI/3);
        Ellipse2D ellipse2 = new Ellipse2D(2,1,new Point2D(1,0),4*(Math.PI)/3);
        Segment2D segment1 = new Segment2D(new Point2D(-1,0),new Point2D(1,0));
        Segment2D segment2 = new Segment2D(new Point2D(-1*Math.sqrt(3),-1),new Point2D(1*Math.sqrt(3),1));
        Segment2D segment3 = new Segment2D(new Point2D(-1*Math.sqrt(3),1),new Point2D(1*Math.sqrt(3),-1));

        assertEquals(-0.25,ellipse1.getProjection(segment1).getExtrimePoint()[0].getX(),0.01);
        assertEquals(0,ellipse1.getProjection(segment1).getExtrimePoint()[0].getY(),0.01);
        assertEquals(2.25,ellipse1.getProjection(segment1).getExtrimePoint()[1].getX(),0.01);
        assertEquals(0,ellipse1.getProjection(segment1).getExtrimePoint()[1].getY(),0.01);

        assertEquals(-0.25,ellipse2.getProjection(segment1).getExtrimePoint()[0].getX(),0.01);
        assertEquals(0,ellipse2.getProjection(segment1).getExtrimePoint()[0].getY(),0.01);
        assertEquals(2.25,ellipse2.getProjection(segment1).getExtrimePoint()[1].getX(),0.01);
        assertEquals(0,ellipse2.getProjection(segment1).getExtrimePoint()[1].getY(),0.01);

        assertEquals(-0.808,ellipse1.getProjection(segment2).getExtrimePoint()[0].getX(),0.01);
        assertEquals(-0.467,ellipse1.getProjection(segment2).getExtrimePoint()[0].getY(),0.01);
        assertEquals(2.308,ellipse1.getProjection(segment2).getExtrimePoint()[1].getX(),0.01);
        assertEquals(1.333,ellipse1.getProjection(segment2).getExtrimePoint()[1].getY(),0.01);

        assertEquals(-0.808,ellipse2.getProjection(segment2).getExtrimePoint()[0].getX(),0.01);
        assertEquals(-0.467,ellipse2.getProjection(segment2).getExtrimePoint()[0].getY(),0.01);
        assertEquals(2.308,ellipse2.getProjection(segment2).getExtrimePoint()[1].getX(),0.01);
        assertEquals(1.333,ellipse2.getProjection(segment2).getExtrimePoint()[1].getY(),0.01);

        assertEquals(-0.116,ellipse1.getProjection(segment3).getExtrimePoint()[0].getX(),0.01);
        assertEquals(0.067,ellipse1.getProjection(segment3).getExtrimePoint()[0].getY(),0.01);
        assertEquals(1.616,ellipse1.getProjection(segment3).getExtrimePoint()[1].getX(),0.01);
        assertEquals(-0.933,ellipse1.getProjection(segment3).getExtrimePoint()[1].getY(),0.01);

        assertEquals(-0.116,ellipse2.getProjection(segment3).getExtrimePoint()[0].getX(),0.01);
        assertEquals(0.067,ellipse2.getProjection(segment3).getExtrimePoint()[0].getY(),0.01);
        assertEquals(1.616,ellipse2.getProjection(segment3).getExtrimePoint()[1].getX(),0.01);
        assertEquals(-0.933,ellipse2.getProjection(segment3).getExtrimePoint()[1].getY(),0.01);
    }


}