package org.socialforce.strategy.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.strategy.Path;

import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/2/8.
 */
public class CompoundAStarPath implements Path {
    private LinkedList<AStarPathFinder.Maps> mapSet = new LinkedList<>();
    public LinkedList<Point> goalSet = new LinkedList<>();
    public int currentGoal = 0;

    public CompoundAStarPath(){
    }

    public CompoundAStarPath(AStarPathFinder.Maps map){
        mapSet.addLast(map);
        goalSet.addLast(map.getGoal());
    }
    public CompoundAStarPath(AStarPathFinder.Maps ... maps){
        for(AStarPathFinder.Maps map:maps){
            mapSet.addLast(map);
            goalSet.addLast(map.getGoal());
        }
    }

    public void addMap(AStarPathFinder.Maps map){
        mapSet.addLast(map);
        goalSet.addLast(map.getGoal());
    }

    @Override
    public Point getGoal() {
        return goalSet.getLast();
    }

    public Point getCurrentGoal(){ return goalSet.get(currentGoal);}

    public LinkedList<Point> getGoals(){ return goalSet; }


    @Override
    public Point nextStep(Point current) {
        Point nextPoint = mapSet.get(currentGoal).findNext(current);
        if(nextPoint.distanceTo(goalSet.get(currentGoal)) <= 0.4 && currentGoal+1 != mapSet.size())
            currentGoal++;
        return nextPoint;
    }

    @Override
    public double length(Point current) {
        double length = 0;
        int goalNo = 0;
        for (AStarPathFinder.Maps map : mapSet) {
                length += map.getDistance(current);
                current = goalSet.get(goalNo++);
        }
        return length;
    }

    @Override
    public String toString(Point current) {
        return null;
    }
}
