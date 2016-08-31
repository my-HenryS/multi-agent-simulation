package org.socialforce.app;

import org.socialforce.model.Agent;

/**
 * 包含一系列社会力模型应用的listener
 * @author Ledenel
 * @see SocialForceApplication
 * Created by Ledenel on 2016/8/3.
 */
public interface ApplicationListener extends AgentEscapeListener {
    /**
     * 当该应用被启用时触发的listener
     */
    void onStart();

    /**
     * 当该应用停止时被触发的listener
     * @param terminated 描述进程是否被强制终止
     */
    void onStop(boolean terminated);

    /**
     * 当场景时序超前时被触发
     * @param scene 时序超前的场景
     */
    void onStep(Scene scene);

}
