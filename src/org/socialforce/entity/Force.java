package org.socialforce.entity;

/**
 * represent forces between interactive entity.
 * the force will be calculated in a social force model.
 * @see InteractiveEntity
 * @see SocialForceModel
 * @author Ledenel
 * Created by Ledenel on 2016/7/28.
 */
public interface Force extends Vector {
    /**
     * accumulate the force and get the delta velocity.
     * @param mass the mass of the entity.
     * @param time the time to be accumulated.
     * @return the delta velocity.
     */
    Velocity deltaVelocity(double mass, double time);

    /**
     * creates and returns a copy of this force.
     * same as vector.
     * @return the copied force.
     */
    Force clone();
}
