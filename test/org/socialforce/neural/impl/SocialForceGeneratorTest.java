package org.socialforce.neural.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.neural.DataSetGenerator;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2017/4/1.
 */
public class SocialForceGeneratorTest {
    DataSetGenerator generator;
    @Before
    public void setUp() throws Exception {
        generator = new SocialForceGenerator(0.5,4,0.5);
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/Scene5Box2.csv", 5);
        generator.genOutput();
        generator.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/Scene5Set2.csv", 5);
    }

}