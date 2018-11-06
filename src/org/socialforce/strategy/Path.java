package org.socialforce.strategy;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;

import java.util.LinkedList;

/**
 * 表示agent的路径。
 * 该接口的基本实现是返回一个Agent可以遵循的路径，给定Agent当前位置返回其下一个应该到达的点。
 * @author Ledenel sunjh(edit)
 * @see Agent
 * @see PathFinder
 * Created by Ledenel on 2016/8/6.
 */
public interface Path {
    /**
     * 获取路径的最终目标点。
     * @return 目标点。
     */
    Point getGoal();

    /**
     * 获取路径的当前目标点。
     * @return 目标点。
     */
    Point getCurrentGoal();

    /**
     * 获取路径的所有必经点。
     * @return 目标点集合。
     */
    LinkedList<Point> getGoals();

    /**
     *在当前位置获取下一步的预期点。
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    Point nextStep(Point current);

    /**
     * 计算路径总长度
     * @return 返回路径长度
     */
    double length(Point current);

    /**
     * toString方法
     */
    String toString(Point current);
}
