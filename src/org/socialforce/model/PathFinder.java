package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;

/**
 *在场景中，agent 的路径搜索器，
 * 用来搜索并产生一条路径。
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {
    /**
     * 获取路径搜索器应该搜索的场景。
     * @return  场景
     */
    Scene getScene();

    /**
     * 设置路径搜索器应该搜索的场景。
     * @param scene 场景
     */
    void setScene(Scene scene);

    /**
     * 从起点到目标点之间产生一条路径。
     * @param start 起点。
     * @param end 终点。
     * @return 搜索出的路径。
     */
    Path plan(Point start,Point end);
}
