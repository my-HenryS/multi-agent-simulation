package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.Wall;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.strategy.StaticStrategy;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/2.
 */
public class ApplicationForECTest implements SocialForceApplication {

    public ApplicationForECTest(){
        setUpScenes();
        setUpStrategy();
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene>iterator = scenes.iterator();iterator.hasNext();){
            Scene scene = iterator.next();
        while (!scene.getAllAgents().isEmpty()) {
            scene.stepNext();
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
     */
    public void setUpScenes(){
        ParameterPool parameters = new SimpleParameterPool();
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(-10,0,60,1))
                });
        SimpleSceneParameter parameter = new SimpleSceneParameter();
        parameter.addValue(new SVSR_Exit(new Box2D[]{new Box2D(9,-2,2,5)}));
        parameter.addValue(new SVSR_RandomAgentGenerator(60,new Box2D(5,-5,10,3)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(6,1,8,1)));
        parameters.addLast(parameter);
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }



    protected StaticStrategy strategy;

    public void setUpStrategy(){
        Scene scene;
        for (Iterator<Scene> iterator = scenes.iterator();iterator.hasNext();){
            scene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(scene);
            strategy = new NearestGoalStrategy(scene, pathFinder, new Point2D(10, 8));
            strategy.pathDecision();
        }
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
