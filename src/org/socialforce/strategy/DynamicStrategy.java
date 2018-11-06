package org.socialforce.strategy;

/**
 * Created by sunjh1999 on 2016/12/14.
 */
public interface DynamicStrategy extends GoalStrategy {
    /**
     * 为Agent在逃生途中选择出口，规划路径
     */
    void dynamicDecision();
}
