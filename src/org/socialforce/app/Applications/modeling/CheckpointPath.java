package org.socialforce.app.Applications.modeling;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.Point;
import org.socialforce.strategy.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Ledenel on 2018/1/5.
 */
public class CheckpointPath implements Path {
    private ArrayList<PhysicalEntity> ckpts = null;

    public CheckpointPath(PhysicalEntity... ckpts) {
        this.ckpts = new ArrayList<>(Arrays.asList(ckpts));
    }

    int currIndex = 0;

    @Override
    public Point getGoal() {
        return currentCheckpoint().getReferencePoint();
    }

    @Override
    public Point getCurrentGoal() {
        return currentCheckpoint().getReferencePoint();
    }

    @Override
    public LinkedList<Point> getGoals() {
        return null;
    }

    private void inc() {
        if(currIndex < ckpts.size() - 1) {
            currIndex ++;
        }
    }

    @Override
    public Point nextStep(Point current) {
        if(currentCheckpoint().contains(current)) {
            inc();
        }
        return currentCheckpoint().getReferencePoint();
    }

    private PhysicalEntity currentCheckpoint() {
        return ckpts.get(currIndex);
    }

    @Override
    public double length(Point current) {
        return currentCheckpoint().getDistance(current);
    }

    @Override
    public String toString(Point current) {
        return "";
    }
/*
    @Override
    public String toString(Point current) {
        return null;
    }*/
}
