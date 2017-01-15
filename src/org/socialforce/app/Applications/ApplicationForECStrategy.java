package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.Wall;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Whatever on 2016/12/15.
 */
public class ApplicationForECStrategy extends ApplicationForECTest implements SocialForceApplication{

    public ApplicationForECStrategy(){
        setUpScenes();
    }

    /**
     * start the application immediately.
     * TODO start和setUpstrategy重构，将strategy独立于scene
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            int iteration = 0;
            PathFinder pathFinder = new AStarPathFinder(scene);
            strategy = new ECStrategy(scene, pathFinder,  new Point2D((25+26.75)/2,2), new Point2D(34,14), new Point2D((13+14.5)/2,22), new Point2D(2,(9.5+10.25)/2));
            //strategy = new DynamicLifeBeltStrategy(scene, pathFinder,  new Point2D((25+26.75)/2,2), new Point2D(34,14), new Point2D((13+14.5)/2,22), new Point2D(2,(9.5+10.25)/2));
            //strategy = new LifeBeltStrategy(scene, pathFinder,  new Point2D((25+26.75)/2,2), new Point2D(34,14), new Point2D((13+14.5)/2,22), new Point2D(2,(9.5+10.25)/2));
            //strategy = new NearestGoalStrategy(scene, pathFinder,  new Point2D((25+26.75)/2,2), new Point2D(34,14), new Point2D((13+14.5)/2,22), new Point2D(2,(9.5+10.25)/2));
            strategy.pathDecision();
            while (!scene.getAllAgents().isEmpty()) {
                scene.stepNext();
                iteration += 1;
                if(iteration % 500 ==0 && strategy instanceof DynamicStrategy){
                    ((DynamicStrategy) strategy).dynamicDecision();
                }
            }
            System.out.println("empty! go on next");

        }
    }


    public void setUpScenes(){
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(new Point2D(4, 3),  new Point2D(32,4))),
                        new Wall(new Box2D(new Point2D(32, 4), new Point2D(33, 20))),
                        new Wall(new Box2D(new Point2D(3, 4),  new Point2D(4,20))),
                        new Wall(new Box2D(new Point2D(4, 20),  new Point2D(32, 21))),
                        new Wall(new Circle2D(new Point2D(11.5,11),2)),
                        new Wall(new Circle2D(new Point2D(26,11.5),2))
                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_RandomAgentGenerator(10,new Box2D(4,4 ,27.5,15.5)), new SVSR_RandomAgentGenerator(600,new Box2D(4,4 ,27.5,15.5))));
        parameters.addLast(genParameter((new SVSR_SafetyRegion(new Box2D(24,2,4,1)))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(33,12,1,4))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(2,8,1,4))));
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(12,21,4,1))));
        parameters.addLast(genParameter(new SVSR_Exit(new Box2D[]{new Box2D(new Point2D(25,2), new Point2D(26.75,5)),
                                             new Box2D(new Point2D(31,13.5), new Point2D(34,14.5)),
                                             new Box2D(new Point2D(2,9.5), new Point2D(5,10.25)),
                                             new Box2D(new Point2D(13,19), new Point2D(14.5,22))})));
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
