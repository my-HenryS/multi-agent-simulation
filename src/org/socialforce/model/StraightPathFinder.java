package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.StraightPath;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder{
    /**
     * 从起点到目标点之间产生一条路径。
     *
     * @param targetScene 路径所在的场景。
     * @param agent       需要规划的Agent。
     * @param goal        Agent的目标点。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan(Scene targetScene, Agent agent, Point goal) {
        Path plan = new StraightPath(agent.getShape().getReferencePoint(),goal);
        return plan;
    }
}
