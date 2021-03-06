package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/14.
 */
public class PsychologicalForceRegulationTest {
    Model model = new SimpleForceModel() ;
    Class<InteractiveEntity> BaseAgent;
    BaseAgent a;
    Class<Agent> agent;
    BaseAgent baseAgent, targetAgent, targetAgent2;
    Circle2D circle = new Circle2D(), circle2 = new Circle2D(), circle3 = new Circle2D();
    Point2D initPoint, initPoint2;
    PsychologicalForceRegulation p;
    @Before
    public void setUp() throws Exception {
        p = new PsychologicalForceRegulation(Blockable.class,Agent.class, model);
        initPoint = new Point2D(3,4);
        initPoint2 = new Point2D(3,4);    //TODO 修正上界方向问题
        circle.moveTo(initPoint);
        circle.setRadius(0.243);
        circle2.moveTo(initPoint2);
        circle2.setRadius(0.243);
        circle3.moveTo(initPoint2);
        circle3.setRadius(1);
        baseAgent = new BaseAgent(circle, new Velocity2D(0,0));
        targetAgent = new BaseAgent(circle2, new Velocity2D(0,0));
        targetAgent2 = new BaseAgent(circle3, new Velocity2D(0,0));
    }
    @Test
    public void hasForce() throws Exception {
        assertTrue(p.hasForce(targetAgent,baseAgent));
        assertTrue(p.hasForce(targetAgent2,baseAgent));
    }

    @Test
    public void getForce() throws Exception {
        System.out.println(p.getForce(targetAgent,baseAgent).toString());
    }

}