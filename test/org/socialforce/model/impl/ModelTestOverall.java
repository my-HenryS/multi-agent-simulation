package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.scene.impl.SimpleScene;
import org.socialforce.scene.impl.SVSR_AgentGenerator;
import org.socialforce.container.AgentPool;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.strategy.impl.StraightPath;

/**
 * Created by sunjh1999 on 2016/10/22.
 */
public class ModelTestOverall {

    SimpleScene scene = new SimpleScene(new Box2D(new Point2D(0, 0), new Point2D(20, 20)));
    SVSR_AgentGenerator SVSR_agentGenerator = new SVSR_AgentGenerator(1,1,1,new Box2D(new Point2D(1, 1), new Point2D(2, 2)));

    @Before
    public void setUp() throws Exception {
        SVSR_agentGenerator.apply(scene);

    }
    @Test
    public void getAgent() throws Exception {
        AgentPool allAgents = scene.getAllAgents();
        for (Agent agent: allAgents){
            agent.setPath(new StraightPath(agent.getShape().getReferencePoint(), new Point2D(4,4)));
            agent.setScene(scene);
        }
        scene.stepNext();


    }


}
