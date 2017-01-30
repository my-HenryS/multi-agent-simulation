package org.socialforce.scene;

/**
 * Created by Ledenel on 2016/9/14.
 */
public interface SceneListener {
    /**
     * 场景全部加载完毕时触发的事件。
     * @param scene 触发的场景。
     */
    void onLoaded(Scene scene);

    /**
     * 场景进行一步时触发的事件。
     * @param scene 触发的场景。
     */
    void onStep(Scene scene);
}
