package org.socialforce.app.Applications;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.ValueSet;
import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.SimpleSocialForceModel;

import java.util.List;

/**
 * Created by Whatever on 2016/12/2.
 */
public class ApplicationForECTest implements SocialForceApplication {
    /**
     * start the application immediately.
     */
    @Override
    public void start() {

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
        return null;
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
