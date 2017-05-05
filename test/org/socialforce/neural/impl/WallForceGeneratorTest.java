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
import org.socialforce.model.impl.Exit;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;

import java.io.File;
import java.io.InputStream;


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
        SceneLoader loader = interpreter.setLoader().setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new Exit(new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)))
        );
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new Exit(new Box2D(4,-4,2,2)))
        );
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new SafetyRegion(new Box2D(1,10,8,1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("model3Box.csv" ,1 );
        generator.genOutput(scene);
        generator.toFile("model3BoxSet2.csv");
    }

}