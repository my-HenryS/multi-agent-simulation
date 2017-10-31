package org.socialforce.app.Applications;

import org.socialforce.app.Application;
import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.AgentStepCSVWriter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.*;
import org.socialforce.model.impl.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.MultipleEntitiesGenerator;
import org.socialforce.scene.impl.RandomEntityGenerator2D;
import org.socialforce.scene.impl.SimpleEntityGenerator;
import org.socialforce.scene.impl.SimpleParameterPool;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/7/4.
 */
public class ApplicationForEllipse extends SimpleApplication implements Application {
    BaseAgent template;
    double DoorWidth;
    int density;
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            AgentStepCSVWriter csvWriter = new AgentStepCSVWriter();
            currentScene = iterator.next();
            currentScene.addSceneListener(csvWriter);
            PathFinder pathFinder = new AStarPathFinder(currentScene, new Circle2D(new Point2D(0,0),0.25/2));
            GoalStrategy strategy = new NearestGoalStrategy(currentScene, pathFinder);
            strategy.pathDecision();
            this.initScene(currentScene);
            while (!toSkip()) {
                this.stepNext(currentScene);
            }
            //csvWriter.writeCSV("output/agent.csv");
            if(onStop()) return;
        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes(){
        //template = new BaseAgent(new Circle2D(new Point2D(0,0),0.486/2), new Velocity2D(0,0));   //FIXME 行人的形状切换为圆
        template = new BaseAgent(new Ellipse2D(0.45/2,0.25/2,new Point2D(0,0),0), new Velocity2D(0,0));  //FIXME 行人的形状切换为椭圆
        scenes = new LinkedList<>();
        DoorWidth = 0.9;  //FIXME 门宽
        density = 20;
        setUpT1Scene5();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }

    /**
     * 门前有柱子
     */
    protected void setUpT1Scene5(){

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader().setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        /*parameters.addValuesAsParameter(
                new SimpleEntityGenerator()
                        .setValue(new Wall(new Box2D(4,-4,2,2)))
                        .setPriority(10)
                ,new SimpleEntityGenerator()
                        .setValue(new Wall(new Circle2D(new Point2D(5,-3),1)))
                        .setPriority(10)
        );*/

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(1,10,8,1)))
                .addValue(new Monitor(new Box2D(0,0,10,1)))

        );

        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new DoorTurn(new Box2D(5-DoorWidth/2,-0.01,DoorWidth,1.02),new Segment2D(new Point2D(5-DoorWidth/2,-0.1),new Point2D(5+DoorWidth/2,-0.1)),0.4))  //FIXME 主动侧身作用区域（出口）的大小
                .setPriority(5)
        );

        parameters.addValuesAsParameter(
                new RandomEntityGenerator2D(30,new Box2D(0,-10,10,5))
                        .setValue(template)
                        .setGaussianParameter(1,0.025)
                //FIXME 行人的总数（修改“26”）
        );
        /*parameters.addValuesAsParameter(
                new RandomEntityGenerator2D(1,new Box2D(4.3,-3,0.5,2)).setValue(template)
        );*/

        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()){
            scenes.add(s);
        }

    }
}
