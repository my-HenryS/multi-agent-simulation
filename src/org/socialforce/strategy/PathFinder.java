package org.socialforce.strategy;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;

/**
 *在场景中，agent 的路径搜索器，
 * 用来搜索并产生一条路径。
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {

    /**
     * 获取Pathfinder根据goal生成的矢量场。
     * @return 搜索出的路径。
     */
    Path plan_for(Point goal);

    /**
     * 获取Pathfinder根据goal且必须包含某些给定点的矢量场。
     * @return 搜索出的路径。
     */
    Path constraint_plan_for(Point goal, Point ... toBeContained);

    /**
     * 获取goals集合
     */
    Point[] getGoals();

    /**
     * 改动模板scene并使得PathFinder可以根据goal生成新的路径
     */
    void addSituation(Scene scene, Point goal);

    /**
     * 加入新的goal生成新的路径
     */
    void addSituation(Point goal);

    /**
     * 清除缓存
     */
    void clearCache();
}
