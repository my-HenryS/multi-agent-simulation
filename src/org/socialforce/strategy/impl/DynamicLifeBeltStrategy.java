package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2016/12/24.
 */
public class DynamicLifeBeltStrategy extends LifeBeltStrategy implements DynamicStrategy {
    public DynamicLifeBeltStrategy(Scene scene, PathFinder pathFinder) {
        super(scene, pathFinder);
    }

    public void dynamicDecision(){
        pathDecision();
    }
}
