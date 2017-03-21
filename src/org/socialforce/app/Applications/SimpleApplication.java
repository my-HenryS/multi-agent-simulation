package org.socialforce.app.Applications;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.model.Model;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.ValueSet;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/2.
 */
public abstract class SimpleApplication implements SocialForceApplication {
    protected GoalStrategy strategy;
    protected LinkedList<Scene> scenes;
    protected Scene currentScene;
    protected ApplicationListener listener;
    protected Model model = new SimpleSocialForceModel();
    protected boolean Pause = false;

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
    public Model getModel() {
        return model;
    }
    /**
     * set the social force model for the application.
     *
     * @param model the model to be set.
     */
    @Override
    public void setModel(Model model) {
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


    public void StepNext(Scene scene){
        if (Pause == false){
            scene.stepNext();
        }
        else;//do nothing
    }

    @Override
    public void pause(){
        Pause = true;
    }

    @Override
    public void resume(){
        Pause = false;
    }

    public void skip(){
        currentScene.getAllAgents().clear();
    }


}
