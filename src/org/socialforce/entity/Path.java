package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/8/6.
 */
public interface Path {
    Point getStartPoint();
    Point getGoal();
    Point getCurrentGoal(Point current);
}
