package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.impl.BaseAgent;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/11/2.
 */
public class SVSR_AgentGeneratorTest {
    SVSR_AgentGenerator agentGenerator;
    Scene singleScene;
    @Before
    public void setUp() throws Exception {
        agentGenerator = new SVSR_AgentGenerator(1, 1, 1, new Box2D(3, 3, 1, 1));
        singleScene = new SimpleScene(new Box2D(-50, -50, 100, 100));
    }

    @Test
    public void id_test() throws Exception {
        agentGenerator.apply(singleScene);
        Circle2D circle = new Circle2D(new Point2D(0, 0), 100);
        Agent agent = singleScene.getAllAgents().selectTop(circle);
        assertEquals(1,agent.getId());
    }

}