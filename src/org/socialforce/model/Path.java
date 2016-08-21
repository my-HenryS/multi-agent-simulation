package org.socialforce.model;

import org.socialforce.geom.Point;

/**
 * represent a path for the agent.
 * a basic implement is a set of goal points.
 * @author Ledenel
 * @see Agent
 * @see PathFinder
 * Created by Ledenel on 2016/8/6.
 */
public interface Path {
    /**
     * get the start point of this path.
     * @return the start point.
     */
    Point getStartPoint();

    /**
     * get the end point of this path(the goal of this path).
     * @return the end point(the goal).
     */
    Point getGoal();

    /**
     * returns the next point the agent should go while at the current point.
     * @param current the point that agent is there.
     * @return the point that agent should go in this path.
     */
    Point getCurrentGoal(Point current);

    /**
     * check if the path is walked completely.
     * @return true if the path is done; otherwise false.
     */
    boolean done();
}
