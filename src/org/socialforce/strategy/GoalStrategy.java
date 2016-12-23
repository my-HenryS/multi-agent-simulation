package org.socialforce.strategy;

import org.socialforce.geom.Point;

/**接受场景，寻路器和目标点集合
 * 实现为场景中的每一个Agent按既定策略规划目标点
 * 并调用Pathfinder生成路径，赋予Agent
 * Created by sunjh1999 on 2016/12/14.
 */
public interface GoalStrategy {
    void pathDecision();
}

