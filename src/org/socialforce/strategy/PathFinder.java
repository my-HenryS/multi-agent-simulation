package org.socialforce.strategy;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;

/**
 *在场景中，agent 的路径搜索器，
 * 用来搜索并产生一条路径。
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {

    /**
     * 获取Pathfinder为Agent生成的路径。
     * @return 搜索出的路径。
     */
    Path plan_for();

    /**
     * 设置Pathfinder的参数。
     * @param targetScene 目标场景
     * @param agent 目标agent
     * @param goal 目标位置
     */
    void setParameters(Scene targetScene, Agent agent, Point goal);
}
