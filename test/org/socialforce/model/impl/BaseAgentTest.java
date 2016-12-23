package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.SocialForceModel;
import org.socialforce.strategy.impl.StraightPath;

/**
 * Created by sunjh1999 on 2016/10/18.
 */
public class BaseAgentTest {
    BaseAgent baseAgent;
    Circle2D circle = new Circle2D();
    Point2D initPoint;
    SimpleScene scene = new SimpleScene(new Box2D(new Point2D(0, 0), new Point2D(20, 20)));
    SocialForceModel model = new SimpleSocialForceModel();
    @Before
    public void setUp() throws Exception {
        initPoint = new Point2D(3,4);
        circle.moveTo(initPoint);
        circle.setRadius(5);
        baseAgent = new BaseAgent(circle);
        baseAgent.view  = circle;
        baseAgent.setPath(new StraightPath(baseAgent.getShape().getReferencePoint(), new Point2D(4,4)));
        baseAgent.setScene(scene);
        baseAgent.setModel(model);
    }
    @Test
    public void getShape() throws Exception {
        
    }

    @Test
    public void determineNext() throws Exception {
        baseAgent.determineNext();
        System.out.println(baseAgent.toString());
    }

}