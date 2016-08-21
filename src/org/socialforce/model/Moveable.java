package org.socialforce.model;

import org.socialforce.geom.Force;
import org.socialforce.geom.Point;
import org.socialforce.geom.Velocity;

/**
 * represent entities which are able to move.
 *
 * @see Agent
 * Created by Ledenel on 2016/7/30.
 */
public interface Moveable {
    /**
     * get the velocity of this moving entity.
     *
     * @return the velocity.
     */
    Velocity getVelocity();

    /**
     * push the entity with a force on the reference point.
     * the force will move this entity.
     *
     * @param force the force to push.
     */
    void push(Force force);

    /**
     * push the entity on a specific point.
     * this method can also cause entity to rotate.
     *
     * @param force      the force to push.
     * @param startPoint the point which the force is push on.
     */
    void push(Force force, Point startPoint);
}
