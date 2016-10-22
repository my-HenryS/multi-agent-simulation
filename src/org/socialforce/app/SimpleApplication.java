package org.socialforce.app;

import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;

import java.util.List;

/**
 * Created by Whatever on 2016/10/22.
 */
public class SimpleApplication implements SocialForceApplication {
    /**
     * start the application immediately.
     */
    @Override
    public void start() {

    }

    protected SocialForceModel model;
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


    protected Scene singleScene;
    /**
     * get all the scenes the applicaion is simulating.
     *
     * @return all scenes to simulate.
     */
    @Override
    public Iterable<Scene> getAllScenes() {
        return (Iterable<Scene>) singleScene;//TODO 加相关方法，不然不对
    }


    protected AgentEscapeListener listener;
    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public AgentEscapeListener getApplicationListener() {
        return listener;
    }

    /**
     * set a listener for application events.
     *
     * @param listener the listener to be set.
     */
    @Override
    public void setApplicationListener(AgentEscapeListener listener) {
        this.listener = listener;
    }

    @Override
    public Scene findScene(ValueSet set) {
        return singleScene;
    }

    protected  List<PathFinder> pathFinder;
    @Override
    public List<PathFinder> getAllPathFinders() {
        return pathFinder;
    }
}
