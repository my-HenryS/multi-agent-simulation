package org.socialforce.app.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Interpreter;
import org.socialforce.scene.SceneLoader;

import java.io.File;

/**
 * Created by sunjh1999 on 2017/1/15.
 */
public class SimpleParserTest {
    File file;
    Interpreter parser = new SimpleInterpreter();
    @Before
    public void setUp() throws Exception {
        file = new File("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/test/org/socialforce/app/impl/test.s");
    }
    @Test
    public void loadFile() throws Exception {
        parser.loadFile(file);
    }

    @Test
    public void readParameter() throws Exception {
        loadFile();
        SceneLoader loader = parser.setLoader();
    }

}