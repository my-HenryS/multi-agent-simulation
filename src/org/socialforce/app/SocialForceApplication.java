package org.socialforce.app;

import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;

import java.util.List;

/**
 * 包含一系列场景的一个社会力模型应用
 * @see SocialForceModel
 * @see ApplicationListener
 * @author Ledenel
 * Created by Ledenel on 2016/8/3.
 */
public interface SocialForceApplication {
    /**
     * 立即执行此模拟
     */
    void start();

    /**
     * get the social force model the application is using.
     * TODO 这什么鬼意思，下一堆同
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
     * 获取模拟中正在被使用的listener
     * @return 模拟的listener
     */
    AgentEscapeListener getApplicationListener();

    /**
     * 为模拟设置listener
     * @param listener 需要被设置的listener
     */
    void setApplicationListener(AgentEscapeListener listener);

    Scene findScene(ValueSet set);

    List<PathFinder> getAllPathFinders();
}
