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
import org.socialforce.model.impl.Wall;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;

import java.io.InputStream;

import static org.junit.Assert.*;
import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by sunjh1999 on 2017/4/1.
 */
public class SocialForceGeneratorTest extends WallForceGeneratorTest{
    SocialForceGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new SocialForceGenerator(0.5,4,0.5); //timestep intercept min-div
        setMap();
    }

    public void setMap2(){
        double DoorWidth = 1.36;
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0,0,1,10.5)),
                        new Wall(new Box2D(0,10.5,13,1)),
                        new Wall(new Box2D(4,-8,1,16)), //防止A*从外面走
                        new Wall(new Box2D(5,7,8,1)),
                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(15,9.25,0.1,0.1))));
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/Scene5Box2.csv", 5);
        generator.genOutput(scene);
        setMap2();
        generator.readFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/curve4.csv", 5);
        generator.genOutput(scene);
        generator.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/MultiSet.csv", 2);
    }

}