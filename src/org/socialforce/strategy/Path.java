package org.socialforce.strategy;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;

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
     * 获取路径的目标点。
     * @return 目标点。
     */
    Point getGoal();

    /**
     *在当前位置获取下一个目标点。
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    Point nextStep(Point current);

    /**
     * 计算路径长度
     * @return 返回路径长度
     */
    double length(Point current);

    /**
     * toString方法
     */
    String toString(Point current);
}
