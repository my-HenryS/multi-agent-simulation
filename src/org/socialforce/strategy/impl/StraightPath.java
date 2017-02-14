package org.socialforce.strategy.impl;

import org.socialforce.strategy.Path;
import org.socialforce.geom.Point;

import java.util.LinkedList;

/**
 * 定义StraightPath类，其实现了接口Path的方法。
 * Created by Ledenel on 2016/8/14.
 */
public class StraightPath implements Path {
    protected Point[] goals;

    public StraightPath(Point ... goals) {
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

    public Point getCurrentGoal(){ return goals[goals.length-1]; }

    public LinkedList<Point> getGoals() {
        LinkedList<Point> Goals = new LinkedList<>();
        Goals.addLast(goals[goals.length-1]);
        return Goals;
    }

    /**
     *在当前位置获取下一个目标点。
     *
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    @Override
    public Point nextStep(Point current) {
        int index = 0;
        double length = Double.POSITIVE_INFINITY;
        for(int i = 0; i < goals.length; i++) {
            Point point = goals[i];
            if (current.distanceTo(point) < length) {
                length = current.distanceTo(point);
                index = i;
            }
        }
        return goals[index];
    }

    public int getNext(Point current) {
        int index = 0;
        double length = Double.POSITIVE_INFINITY;
        for(int i = 0; i < goals.length; i++) {
            Point point = goals[i];
            if (current.distanceTo(point) < length) {
                length = current.distanceTo(point);
                index = i;
            }
        }
        return index;
    }

    public String toString(Point current){
        String string = "路径为";
        for(int i = getNext(current); i<goals.length; i++){
            string += goals[i].toString()+"， ";
        }
        return string;
    }

    public double length(Point current){
        double length = 0;
        for(int i = getNext(current); i<goals.length; i++){
            length += goals[i].distanceTo(goals[i-1]);
        }
        return length;
    }
}
