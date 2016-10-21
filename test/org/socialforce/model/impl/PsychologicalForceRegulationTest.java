package org.socialforce.model.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.Classes;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/14.
 */
public class PsychologicalForceRegulationTest {
    SocialForceModel model ;
    Class<InteractiveEntity> BaseAgent;
    BaseAgent a;
    Class<Agent> agent;
    @Before
    public void setUp() throws Exception {
        PsychologicalForceRegulation p = new PsychologicalForceRegulation(BaseAgent,agent,model);
    }
    @Test
    public void hasForce() throws Exception {

    }

    @Test
    public void getForce() throws Exception {

    }

}