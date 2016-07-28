package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface InteractiveEntity {
    void interact(InteractiveEntity other);
    Shape getShape();

}

