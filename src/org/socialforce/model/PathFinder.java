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
     * 从起点到目标点之间产生一条路径。
     * @param targetScene 路径所在的场景。
     * @param agent 需要规划的Agent。
     * @param goal Agent的目标点。
     * @return 搜索出的路径。
     */
    Path plan(Scene targetScene, Agent agent, Point goal);
}
