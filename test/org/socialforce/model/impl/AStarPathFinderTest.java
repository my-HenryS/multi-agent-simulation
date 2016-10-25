package org.socialforce.model.impl;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.impl.Box2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/23.
 */
public class AStarPathFinderTest {
    @Before
    public void setUp() throws Exception {
        SquareRoomLoader loader = new SquareRoomLoader();
        Scene scene = loader.readScene();
        SVSR_AgentGenerator svsr_agentGenerator = new SVSR_AgentGenerator(1,1,1,new Box2D(3,3,2,2));
        //BaseAgent new_agent = svsr_agentGenerator.agentGenerator.model.createAgent();


        AStarPathFinder aStarPathFinder = new AStarPathFinder();
        //aStarPathFinder.plan(scene,);
    }

    @Test
    public void plan() throws Exception {

    }

    @Test
    public void map_initiate() throws Exception {

    }

}