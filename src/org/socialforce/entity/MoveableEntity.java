package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/30.
 */
public interface MoveableEntity extends InteractiveEntity {
    Vector getVelocity();

    void push(Force force);

    void push(Force force, Point startPoint);
}
