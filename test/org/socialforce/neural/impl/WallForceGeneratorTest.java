package org.socialforce.neural.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.neural.DataSetGenerator;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public class WallForceGeneratorTest {
    DataSetGenerator generator;
    @Before
    public void setUp() throws Exception {
        generator = new WallForceGenerator(0.5, 4, 0.4);
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/model3Box.csv" ,1 );
        generator.genOutput();
        generator.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/model3BoxSet2.csv");
    }

}