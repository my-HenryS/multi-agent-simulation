package org.socialforce.strategy.impl;

import org.socialforce.strategy.Path;
import org.socialforce.geom.Point;

/**
 * 定义StraightPath类，其实现了接口Path的方法。
 * Created by Ledenel on 2016/8/14.
 */
public class AStarPath implements Path {
    /**
     * 获取路径的起点。
     *
     * @return 返回起点。
     */
    @Override
    public Point getStartPoint() {
        return goals[0];
    }

    protected Point[] goals;

    public AStarPath(Point ... goals) {
        this.goals = goals;
    }

    protected int reached = 0;

    /**
     * 获取路径的目标点。
     *
     * @return 目标点。
     */
    @Override
    public Point getGoal() {
        return goals[goals.length-1];
    }

    /**
     *在当前位置获取下一个目标点。
     *
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    @Override
    public Point getCurrentGoal(Point current) {
        while (reached < goals.length && goals[reached].epsilonEquals(current, AStarPathFinder.min_div)) {
            reached++;
        }
        if(done()) {
            return goals[goals.length-1];
        }
        return goals[reached];
    }

    /**
     * 检查路径是否走完。
     *
     * @return 如果走完返回真，否则假。
     */
    @Override
    public boolean done() {
        return reached >= goals.length;
    }

    public String toString(){
        String string = "路径为";
        for(int i =0; i<goals.length; i++){
            string += goals[i].toString()+"， ";
        }
        return string;
    }

    public double length(){
        double length = 0;
        for(int i =1; i<goals.length; i++){
            length += goals[i].distanceTo(goals[i-1]);
        }
        return length;
    }
}
