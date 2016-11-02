package org.socialforce.model;

import org.socialforce.geom.Point;

/**
 * 表示agent的路径。
 * 该接口的基本实现是获取一组目标点。
 * @author Ledenel
 * @see Agent
 * @see PathFinder
 * Created by Ledenel on 2016/8/6.
 */
public interface Path {
    /**
     * 获取路径的起点。
     * @return 返回起点。
     */
    Point getStartPoint();

    /**
     * 获取路径的目标点。
     * @return 目标点。
     */
    Point getGoal();

    /**
     *在当前位置获取下一个目标点。
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    Point getCurrentGoal(Point current);

    /**
     * 检查路径是否走完。
     * @return 如果走完返回真，否则假。
     */
    boolean done();

    Path moveBy(double x, double y);
}
