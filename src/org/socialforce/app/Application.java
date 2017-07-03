package org.socialforce.app;

import org.socialforce.scene.Scene;
import org.socialforce.scene.ValueSet;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.Model;

import java.util.List;

/**
 * a social force simulation application with a set of scenes.
 * @see Model
 * @see ApplicationListener
 * @author Ledenel
 * Created by Ledenel on 2016/8/3.
 */
public interface Application {
    /**
     * start the application immediately.
     */
    void start();

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

    String getName();

    void setName(String name);

    /**
     * 返回当前运行的场景
     */
    Scene getCurrentScene();

    /**
     * 跳过当前场景
     */
    void skip();

    @Deprecated
    void stop();

    /**
     * 暂停当前场景
     */
    void pause();

    /**
     * 恢复当前场景
     */
    void resume();

    /**
     * 结束所有场景
     */
    void terminate();

    /**
     * 定义场景在何种场景下自动结束
     * @return
     */
    boolean toSkip();

    /**
     * 定义运行一步的最小时间
     * @param stepForward
     */
    void setMinStepForward(int stepForward);
}
