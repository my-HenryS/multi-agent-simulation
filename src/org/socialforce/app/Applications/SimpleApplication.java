package org.socialforce.app.Applications;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.ValueSet;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import javax.naming.OperationNotSupportedException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by Whatever on 2016/12/2.
 */
public abstract class SimpleApplication implements SocialForceApplication {
    protected GoalStrategy strategy;
    protected LinkedList<Scene> scenes;
    protected ApplicationListener listener;
    protected SocialForceModel model = new SimpleSocialForceModel();

    @Override
    public void stop() {
        throw new UnsupportedOperationException("不支持类型" + this.getClass() + "的应用停止。");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    String name = this.getClass().getSimpleName();


    public SimpleApplication(){
        setUpScenes();
    }
    /**
     * 需要根据parameter的map来生成一系列scene
     */
    public void setUpScenes(){
        scenes = new LinkedList<>();
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
    public LinkedList<Scene> getAllScenes() {
        return scenes;
    }
    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public ApplicationListener getApplicationListener() {
        return listener;
    }

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
