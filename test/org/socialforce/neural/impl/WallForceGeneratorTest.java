package org.socialforce.neural.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public class WallForceGeneratorTest {
    WallForceGenerator generator;
    Scene scene;
    @Before
    public void setUp() throws Exception {
        generator = new WallForceGenerator(0.5, 4, 0.4);
        setMap();
    }

    public void setMap(){
        double DoorWidth = 1.36;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_Wall(new Box2D[]{new Box2D(4,-4,2,2)})));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,10,8,1))));
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/model3Box.csv" ,1 );
        generator.genOutput(scene);
        generator.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/model3BoxSet2.csv");
    }

}