package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;

/**
 * a path finder for the agent in the scene.
 * used to generate path.
 *
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {

    /**
     * generate a path from start point to end point.
     *
     * @param targetScene 智能体所在的场景。
     * @param agent       the start point.
     * @param goal        the end point.
     * @return the generated path.
     */
    Path plan(Scene targetScene, Agent agent, Point goal);
}
