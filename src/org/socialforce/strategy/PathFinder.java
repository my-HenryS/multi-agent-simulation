package org.socialforce.strategy;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;

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
     * 获取goals集合
     */
    Point[] getGoals();

}
