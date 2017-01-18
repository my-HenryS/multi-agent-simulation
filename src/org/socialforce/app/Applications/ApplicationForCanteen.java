package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.ECStrategy;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/13.
 */
public class ApplicationForCanteen extends ApplicationForECTest implements SocialForceApplication {


    public ApplicationForCanteen(){
        setUpScenes();
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        System.out.println("Application starts!!");
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            int iteration = 0;
            PathFinder pathFinder = new AStarPathFinder(scene);
            strategy = new ECStrategy(scene, pathFinder);
            //strategy = new DynamicLifeBeltStrategy(scene, pathFinder);
            //strategy = new LifeBeltStrategy(scene, pathFinder);
            //strategy = new NearestGoalStrategy(scene, pathFinder);
            strategy.pathDecision();
            while (scene.getAllAgents().size() > 5) {
                scene.stepNext();
                iteration += 1;
                if(iteration % 500 ==0 && strategy instanceof DynamicStrategy){
                    ((DynamicStrategy) strategy).dynamicDecision();
                }
            }
        }
    }


    public void setUpScenes(){
        File file = new File("C:\\Users\\Whatever\\Desktop\\CNU\\UnrealSocialForceSimulation\\SocialForceSimulation\\test\\org\\socialforce\\app\\impl\\canteen2.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFile(file);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(255,new Box2D(0,0,25,18))));
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(150,new Box2D(0,18,25,3))));
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(0,new Box2D(0,0,22,6))));       //测试用生成器
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(0,new Box2D(-45,-45,5,5))));       //测试用生成器
        parameters.addLast(genParameter((new SVSR_SafetyRegion(new Box2D(-3,-0.5,1,4)))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(18.5,-3,4,1))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(30,17.5,1,4))));
        parameters.addLast(genParameter(new SVSR_Exit(new Box2D[]{new Box2D(new Point2D(-2,0.75), new Point2D(1.8,2.25)),
                new Box2D(new Point2D(3,0.75), new Point2D(4,2.25)),
                new Box2D(new Point2D(19.75,2), new Point2D(21.25,3)),
                new Box2D(new Point2D(19.75,-2), new Point2D(21.25,1.8)),
                new Box2D(new Point2D(24,18.75), new Point2D(26.2,20.25)),
                new Box2D(new Point2D(27.5,18.75), new Point2D(29.5,20.25))})));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

    public SceneParameter genParameter(SceneValue... sceneValue){
        SceneParameter parameter;
        LinkedList<SceneValue> values = new LinkedList<>();
        for(SceneValue value : sceneValue){
            values.addLast(value);
        }
        parameter = new SimpleSceneParameter(values);
        return parameter;
    }

}
