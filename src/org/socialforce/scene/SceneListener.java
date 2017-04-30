package org.socialforce.scene;

/**
 * Created by Ledenel on 2016/9/14.
 */
public interface SceneListener {
    /**
     * Listener添加进Scene后时触发的事件。
     * @param scene 触发的场景。
     */
    void onAdded(Scene scene);

    /**
     * 场景进行一步时触发的事件。
     * @param scene 触发的场景。
     */
    void onStep(Scene scene);
}
