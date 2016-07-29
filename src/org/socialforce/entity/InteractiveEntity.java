package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface InteractiveEntity {
     /**
     * the prosses of stuff interacting with other stuff
     * such as agent, wall, fire etc.
     * @param other could be any class of InteractiveEntity
     * @return depends on the class of InteractiveEntity
     * if InteractiveEntity is Agent
     * @teturn Force
     * @see Shape Force Agent
     */
    void interact(InteractiveEntity other);
    /**
     * get the class of Shape
     * line,square,circle etc.
     */
    Shape getShape();

}

