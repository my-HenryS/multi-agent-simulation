package org.socialforce.model.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Path;
import org.socialforce.model.PathFinder;
import org.socialforce.model.impl.StraightPath;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder {
    Path path;

    public StraightPathFinder(Scene targetScene, Agent agent, Point goal) {
        path = new StraightPath(agent.getShape().getReferencePoint(),goal);
    }

    /**
     * 从起点到目标点之间产生一条路径。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan_for(){
        return path;
    }
}
