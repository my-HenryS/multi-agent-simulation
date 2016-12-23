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
public class ApplicationForECStrategy implements SocialForceApplication{

    public ApplicationForECStrategy(){
        SetUpParameter();
        setUpScenes();
        //setUpStrategy();
    }

    protected StaticStrategy strategy;
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
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     * 目前就先随便生成一堆算了……
     * 手动map
     */
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

    protected ParameterSet parameterSet;//目前先不用这个，之后肯定要用
    protected LinkedList<SceneParameter> parameters = new LinkedList<>();

    public void SetUpParameter(){
        SimpleSceneParameter parameter = new SimpleSceneParameter();
        parameter.addValue(new SVSR_RandomAgentGenerator(400,new Box2D(4,4,26.5,14.5)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(24,2,4,1)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(33,12,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(2,8,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(12,21,4,1)));
        parameters.addLast(parameter);
    }

    /**
     * get the social force model the application is using.
     *
     * @return the model.
     */
    @Override
    public SocialForceModel getModel() {
        return model;
    }
    protected SocialForceModel model = new SimpleSocialForceModel();

    /**
     * set the social force model for the application.
     *
     * @param model the model to be set.
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }

    /**
     * get all the scenes the applicaion is simulating.
     *
     * @return all scenes to simulate.
     */
    @Override
    public Iterable<Scene> getAllScenes() {
        return scenes;
    }
    protected LinkedList<Scene> scenes = new LinkedList<>();

    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public ApplicationListener getApplicationListener() {
        return listener;
    }
    protected ApplicationListener listener;

    /**
     * set a listener for application events.
     *
     * @param listener the listener to be set.
     */
    @Override
    public void setApplicationListener(ApplicationListener listener) {
        this.listener = listener;
    }

    @Override
    public Scene findScene(ValueSet set) {
        return null;
    }

    @Override
    public List<PathFinder> getAllPathFinders() {
        return null;
    }
}
