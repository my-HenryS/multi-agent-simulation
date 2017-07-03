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
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2017/4/1.
 */
public class SocialForceGeneratorTest extends WallForceGeneratorTest{
    SocialForceGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new SocialForceGenerator(0.2,2,0.5); //timestep intercept min-div
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
                }).setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new SafetyRegion(new Box2D(15,9.25,0.1,0.1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    public void setMap3(){
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0, 0, 1, 16)),
                        new Wall(new Box2D(0, 16, 16, 1)),
                        new Wall(new Box2D(15, 17, 1, 12)),
                        new Wall(new Box2D(5, -5, 1, 17)),
                        new Wall(new Box2D(6, 11, 15, 1)),
                        new Wall(new Box2D(20, 12, 1, 17)),
                }).setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new SafetyRegion(new Box2D(18, 32, 0.1, 0.1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    public void setMap4(){
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0, 0, 8, -0.1)),
                        new Wall(new Box2D(0, 6.78, 8, 0.1))
                }).setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(12, 3.39, 0.1, 0.1)))
                .addValue(new SafetyRegion(new Box2D(-2, 3.39, 0.1, 0.1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    public void setMap5(){
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0, 9.05, 16, 0.3)),
                        new Wall(new Box2D(0, 4.95, 16, -0.3))
                }).setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(18, 7.00, 0.1, 0.1)))
                .addValue(new SafetyRegion(new Box2D(-3, 7.00, 0.1, 0.1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    public void setMap6(){
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(0, 0, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(2, 4, 50, 0.3)),
                        new Wall(new Box2D(2, 50, 50, -0.3))
                }).setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(40, 9.5, 0.1, 0.1)))
        );
        loader.readParameterSet(parameters);
        scene = loader.readScene().getFirst();
    }

    @Test
    public void genOutput() throws Exception {
        generator.readFile("/input/Scene5Box1.csv", 2);
        generator.genOutput(scene);
        generator.readFile("/input/Scene5Box2.csv", 2);
        generator.genOutput(scene);
        setMap2();
        for(int i = 1; i <= 6; i++){
            generator.readFile("/input/bend"+i+".csv", 2);
            generator.genOutput(scene);
        }
        setMap6();
        generator.readFile("/input/straight1.csv", 2);
        generator.genOutput(scene);
        generator.readFile("/input/straight2.csv", 2);
        generator.genOutput(scene);
        generator.readFile("/input/straight3.csv", 2);
        generator.genOutput(scene);
        //generator.readFile("result2.csv", 1);
        //generator.genOutput(scene);
        //setMap5();
        //generator.readFile("/input/对流1.csv", 1);
        //generator.genOutput(scene);
        generator.toFile("/output/MultiSet.csv", 1);
    }

}