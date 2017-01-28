package org.socialforce.app;

import org.socialforce.app.gui.SceneShower;
import org.socialforce.scene.Scene;
import org.socialforce.scene.ValueSet;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.SocialForceModel;

import java.util.List;

/**
 * a social force simulation application with a set of scenes.
 * @see SocialForceModel
 * @see ApplicationListener
 * @author Ledenel
 * Created by Ledenel on 2016/8/3.
 */
public interface SocialForceApplication {
    /**
     * start the application immediately.
     */
    void start();

    /**
     * get the social force model the application is using.
     * @return the model.
     */
    SocialForceModel getModel();

    /**
     * set the social force model for the application.
     * @param model the model to be set.
     */
    void setModel(SocialForceModel model);

    /**
     * get all the scenes the applicaion is simulating.
     * @return all scenes to simulate.
     */
    Iterable<Scene> getAllScenes();

    /**
     * get the application listener for the application.
     * @return the application listener.
     */
    ApplicationListener getApplicationListener();

    /**
     * set a listener for application events.
     * @param listener the listener to be set.
     */
    void setApplicationListener(ApplicationListener listener);

    Scene findScene(ValueSet set);

    List<PathFinder> getAllPathFinders();
}
