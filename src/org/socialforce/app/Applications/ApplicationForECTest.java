package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Vector;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.model.impl.Monitor;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.Wall;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by Whatever on 2016/12/2.
 */
public class ApplicationForECTest extends SimpleApplication implements SocialForceApplication {
    DistanceShape template;

    public ApplicationForECTest(){
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(scene, template);
            GoalStrategy strategy = new NearestGoalStrategy(scene, pathFinder);
            strategy.pathDecision();
            while (!scene.getAllAgents().isEmpty()) {
                scene.stepNext();
            }
            for(Iterator<SceneValue> iter = scene.getValueSet().iterator(); iter.hasNext();){
                SceneValue value = iter.next();
                if(value instanceof SVSR_Monitor){
                    /*System.out.println(((Monitor)value.getValue()).sayVelocity());*/
                }
            }
        }
    }


    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(-10,0,60,1))
                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_Exit(new Box2D[]{new Box2D(9,-2,2,5)})));
        parameters.addLast(genParameter(new SVSR_Monitor(new Circle2D(new Point2D(10,0),0.2))));
        parameters.addLast(genParameter(new SVSR_Monitor(new Circle2D(new Point2D(10,-1),0.2))));
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(60,new Box2D(5,-5,10,3),template)));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(6,1,8,1))));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

}
