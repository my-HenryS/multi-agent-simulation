package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/13.
 */
public class ApplicationForCanteen extends ApplicationForECTest implements SocialForceApplication {


    public ApplicationForCanteen(){

    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        System.out.println("Application starts!!");
        for(int i = 0; i < 40; i++){
            setUpScenes();
            for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
                Scene scene = iterator.next();
                int total_num = 0;
                for(Iterator<SceneValue> iter = scene.getValueSet().iterator(); iter.hasNext();){
                    SceneValue sceneValue = iter.next();
                    if(sceneValue instanceof SVSR_RandomAgentGenerator){
                        total_num += ((SVSR_RandomAgentGenerator) sceneValue).get_Agentnum();
                    }
                }
                System.out.print("Population of "+total_num);
                int iteration = 0;
                PathFinder pathFinder = new AStarPathFinder(scene, new Circle2D(new Point2D(0,0),0.486/2));
                if(i<10){
                    System.out.print(" with ECStrategy, ");
                    strategy = new ECStrategy(scene, pathFinder);
                }
                else if(i<20){
                    System.out.print(" with DynamicLBStrategy, ");
                    strategy = new DynamicLifeBeltStrategy(scene, pathFinder);
                }
                else if(i<30){
                    System.out.print(" with LifeBeltStrategy, ");
                    strategy = new LifeBeltStrategy(scene, pathFinder);
                }
                else if(i<40){
                    System.out.print(" with NRStrategy, ");
                    strategy = new NearestGoalStrategy(scene, pathFinder);
                }
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
    }


    public void setUpScenes(){
        File file = new File("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/test/org/socialforce/app/impl/canteen2.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFile(file);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(150,new Box2D(0,0,25,18)), new SVSR_RandomAgentGenerator(250,new Box2D(0,0,25,18)), new SVSR_RandomAgentGenerator(350,new Box2D(0,0,25,18))));
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(155,new Box2D(0,18,25,3))));
        parameters.addLast(genParameter((new SVSR_SafetyRegion(new Box2D(-3,-0.5,1,4)))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(18.5,-3,4,1))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(30,17.5,1,4))));
        parameters.addLast(genParameter(new SVSR_Exit(new Box2D[]{new Box2D(new Point2D(-2,0.75), new Point2D(1.8,2.18)),
                new Box2D(new Point2D(3,0.82), new Point2D(4,2.18)),
                new Box2D(new Point2D(19.82,2), new Point2D(21.18,3)),
                new Box2D(new Point2D(19.82,-2), new Point2D(21.18,1.8)),
                new Box2D(new Point2D(24,18.82), new Point2D(26.2,20.18)),
                new Box2D(new Point2D(27.5,18.82), new Point2D(29.5,20.18))})));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }



}
