package org.socialforce.model.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Velocity;
import org.socialforce.model.Agent;

/**
 * Created by sunjh1999 on 2017/1/22.
 */
public class SelfDrivingCar extends BaseAgent implements Agent {
    public SelfDrivingCar(DistanceShape shape, Velocity velocity) {
        super(shape, velocity);
    }
}
