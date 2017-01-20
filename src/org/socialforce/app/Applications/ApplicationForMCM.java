package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.*;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;
import org.socialforce.strategy.impl.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class ApplicationForMCM extends ApplicationForECTest implements SocialForceApplication {
    public ApplicationForMCM(){
        setUpScenes();
    }

    /**
     * start the application immediately.
     * TODO start和setUpstrategy重构，将strategy独立于scene
     */
    @Override
    public void start() {
        System.out.println("Application starts!!");
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            int iteration = 0;
            PathFinder pathFinder = new StraightPathFinder(scene);
            //strategy = new ECStrategy(scene, pathFinder);
            //strategy = new DynamicLifeBeltStrategy(scene, pathFinder);
            //strategy = new LifeBeltStrategy(scene, pathFinder);
            strategy = new NearestGoalStrategy(scene, pathFinder);
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
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(0, 0, 100, 100)),
                new Wall[]{
                        new Wall(new Rectangle2D(new Point2D(10,9.5), 20, 0.8, 7*Math.PI/18)),
                        new Wall(new Rectangle2D(new Point2D(20.5,9.5), 20, 0.8, 29*Math.PI/18)),
                        new Wall(new Rectangle2D(new Point2D(20.5,9.5), 20, 0.8, 29*Math.PI/18)),
                        new Wall(new Rectangle2D(new Point2D(20.5,9.5), 20, 0.8, 29*Math.PI/18)),

                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(105,new Box2D(0, 0,100,20))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(43,99,14,1))));
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

