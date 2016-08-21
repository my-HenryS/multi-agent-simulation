package org.socialforce.model.impl;

import org.socialforce.model.Path;
import org.socialforce.geom.Point;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class StraightPath implements Path {
    /**
     * get the start point of this path.
     *
     * @return the start point.
     */
    @Override
    public Point getStartPoint() {
        return goals[0];
    }

    protected Point[] goals;

    public StraightPath(Point ... goals) {
        this.goals = goals;
    }

    protected int reached = 0;

    /**
     * get the end point of this path(the goal of this path).
     *
     * @return the end point(the goal).
     */
    @Override
    public Point getGoal() {
        return goals[goals.length-1];
    }

    /**
     * returns the next point the agent should go while at the current point.
     *
     * @param current the point that agent is there.
     * @return the point that agent should go in this path.
     */
    @Override
    public Point getCurrentGoal(Point current) {
        while (reached < goals.length && goals[reached].epsilonEquals(current,10e-5)) {
            reached++;
        }
        if(done()) {
            return goals[goals.length-1];
        }
        return goals[reached];
    }

    /**
     * check if the path is walked completely.
     *
     * @return true if the path is done; otherwise false.
     */
    @Override
    public boolean done() {
        return reached >= goals.length;
    }
}
