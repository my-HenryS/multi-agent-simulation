package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.SimpleSceneParameter;
import org.socialforce.app.impl.preset.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;
import org.socialforce.strategy.impl.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/15.
 */
public class ApplicationForECStrategy extends ApplicationForECTest implements SocialForceApplication{

    public ApplicationForECStrategy(){
        SetUpParameter();
        setUpScenes();
        //setUpStrategy();
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

        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     * 目前就先随便生成一堆算了……
     * 手动map
     */
    @Override
    public void setUpScenes(){
        for (Iterator<SceneParameter> iterator = parameters.iterator(); iterator.hasNext();)
        {
            SceneParameter parameter = iterator.next();
            SceneLoader loader = new ECTestLoader2();
            Scene scene = loader.readScene();
            if (parameter instanceof SimpleSceneParameter){
                while (true){
                    SceneValue sceneValue =((SimpleSceneParameter) parameter).removeValue();
                    if (sceneValue == null){break;}
                    else sceneValue.apply(scene);
                }
            }
            scene.setApplication(this);
            scenes.addLast(scene);
        }
    }

    @Override
    public void SetUpParameter(){
        SimpleSceneParameter parameter = new SimpleSceneParameter();
        parameter.addValue(new SVSR_RandomAgentGenerator(400,new Box2D(4,4 ,27.5,15.5)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(24,2,4,1)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(33,12,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(2,8,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(12,21,4,1)));
        parameters.addLast(parameter);
    }

}
