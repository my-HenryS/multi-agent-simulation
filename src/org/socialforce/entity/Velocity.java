package org.socialforce.entity;

/**
 * represent a velocity vector.
 * @author Ledenel
 * @see Agent
 * @see Moveable
 * Created by Ledenel on 2016/7/30.
 */
public interface Velocity extends Vector {
    /**
     * accumulate the total distance in specific time.
     * @param time the time to be accumulated.
     * @return the delta distance vector.
     */
    Vector deltaDistance(double time);

    /** creates and returns a copy of this velocity.
     * same as vector.
     * @return the copied velocity.
     */
    Velocity clone();
}
