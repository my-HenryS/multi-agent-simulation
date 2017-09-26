package org.socialforce.app.Applications;

import org.socialforce.app.Application;
import org.socialforce.app.ApplicationListener;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.drawer.impl.SceneDrawerInstaller;
import org.socialforce.model.Model;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.ValueSet;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/2.
 */
public abstract class SimpleApplication implements Application {
    protected GoalStrategy strategy;
    protected LinkedList<Scene> scenes;
    protected Scene currentScene;
    protected ApplicationListener listener;
    protected boolean Pause = false, Skip = false;
    protected int minStepForward = 0;

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

    public boolean initScene(Scene scene){
        return scene.init();
    }

    public void stepNext(Scene scene){
        if (!Pause){
            long startT = System.currentTimeMillis();
            scene.stepNext();
            long span = System.currentTimeMillis() - startT;
            long sleepT = (span < minStepForward) ? minStepForward - span : 0;
            try {
                Thread.sleep(sleepT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ApplicationListener listener = this.getApplicationListener();// 2016/8/23 add step for all agent and statics.
            if (listener != null) {
                listener.onStep(this);
            }
        }
        else;//do nothing
    }

    public void setMinStepForward(int stepForward){
        this.minStepForward = stepForward;
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
        this.Skip = true;
    }

    public boolean toSkip() {
        return Skip || currentScene.getAllAgents().isEmpty();
    }

    boolean terminate = false;

    public void terminate(){
        skip();
        terminate = true;
    }

    /**
     * 在scene运行结束时调用
     * @return 是否结束application
     */
    public boolean onStop() {
        boolean tempT = terminate;
        Skip = false;
        terminate = false;
        return tempT;
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    @Override
    public void manageDrawer(SceneDrawer drawer){}


}
