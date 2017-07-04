package org.socialforce.app.Applications;

import org.jfree.chart.plot.dial.DialScale;
import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.Box;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.impl.*;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.FurthestGoalStrategy;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by Administrator on 2017/7/4.
 */
public class ApplicationForRotateForce extends SimpleApplication{
    protected DistanceShape template;
    protected double DoorWidth;

    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            currentScene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(currentScene, template);
            GoalStrategy strategy = new FurthestGoalStrategy(currentScene, pathFinder);
            strategy.pathDecision();
            while (!toSkip()) {
                this.StepNext(currentScene);
            }
            if(onStop()) return;
        }
    }

    @Override
    public void setUpScenes(){
        template = new Ellipse2D(0.486/2,0.3/2,new Point2D(0,0),0);
        scenes = new LinkedList<>();
        DoorWidth = 1.36;
        setUpscene1();
    }

    private void setUpscene1(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)}),new SV_Exit(new Box2D[]{new Box2D(10-DoorWidth,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5,-5),0),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5,-5.3),0),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,10,8,1))));
        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()){
            scenes.add(s);
        }
    }
    private void setUpscene2()
    {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)}),new SV_Exit(new Box2D[]{new Box2D(10-DoorWidth,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5,-5),90),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5.3,-5),90),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,10,8,1))));
        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()){
            scenes.add(s);
        }
    }
    private void setUpscene3()
    {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)}),new SV_Exit(new Box2D[]{new Box2D(10-DoorWidth,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5,-6),45),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5.361,-6),45),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,10,8,1))));
        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()){
            scenes.add(s);
        }
    }
    private void setUpscene4()
    {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)}),new SV_Exit(new Box2D[]{new Box2D(10-DoorWidth,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5,-6),135),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_ExactAgentGenerator(new Ellipse2D(0.243,0.15,new Point2D(5.361,-6),135),new Velocity2D(0,0),new Palstance2D(0))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,10,8,1))));
        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()){
            scenes.add(s);
        }
    }
}

