package org.socialforce.strategy.impl;

import org.socialforce.strategy.Path;
import org.socialforce.geom.Point;

import java.util.LinkedList;
import java.util.Map;

/**
 * 定义StraightPath类，其实现了接口Path的方法。
 * Created by Ledenel on 2016/8/14.
 */
public class AStarPath implements Path {
    protected AStarPathFinder.Maps map;

    public AStarPath(AStarPathFinder.Maps map) {
        this.map = map;
    }

    protected int reached = 0;

    /**
     * 获取路径的目标点。
     *
     * @return 目标点。
     */
    @Override
    public Point getGoal() {
        return map.getGoal();
    }

    /**
     *在当前位置获取下一个目标点。
     *
     * @param current agent 所在的当前位置点。
     * @return 当前位置的目标点，该点为归还路径上的点。
     */
    @Override
    public Point getCurrentGoal(Point current) {
        return map.findNext(current);
    }

    public String toString(Point current){
        String string = "路径为";
        LinkedList<Point> path = predict(current);
        for(int i = 0; i < path.size(); i++){
            string += path.get(i).toString()+"， ";
        }
        return string;
    }

    //Old version of length
    public double Length(Point current){
        double length = 0;
        LinkedList<Point> path = predict(current);
        for(int i = 0; i < path.size() - 1; i++){
            length += path.get(i).distanceTo(path.get(i+1));
        }
        return length;
    }

    public double length(Point current){
        return map.getDistance(current);
    }

    public LinkedList<Point> predict(Point current){
        LinkedList<Point> path = new LinkedList<>();
        Point temp = current.clone();
        while(!temp.equals(getGoal())){
            path.addLast(temp);
            temp = getCurrentGoal(temp);
        }
        path.addLast(temp);
        return path;
    }
}
