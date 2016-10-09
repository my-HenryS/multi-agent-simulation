package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Box2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/9.
 */
public class BaseAgentTest {
    BaseAgent baseAgent;
    Circle2D circle2D;
    Box2D box2D;
    @Before
    public void setUp() throws Exception{
        baseAgent = new BaseAgent(circle2D);
    }

    @Test
    public void getShape() throws Exception {
        assertEquals(circle2D,baseAgent.getShape());
    }

}