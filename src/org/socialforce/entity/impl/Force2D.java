package org.socialforce.entity.impl;

import org.socialforce.entity.Force;
import org.socialforce.entity.Velocity;

/**
 * Created by Ledenel on 2016/8/16.
 */
public class Force2D extends Vector2D implements Force {
    public Force2D() {
    }

    public Force2D(double x, double y) {
        super(x, y);
    }

    /**
     * accumulate the force and get the delta velocity.
     *
     * @param mass the mass of the entity.
     * @param time the time to be accumulated.
     * @return the delta velocity.
     */
    @Override
    public Velocity deltaVelocity(double mass, double time) {
        double scale = time / mass;
        return new Velocity2D(values[0] * scale, values[1] * scale);
    }

    /**
     * Creates and returns a copy of this vector.
     * The precise meaning of "copy" may depend on the class of the vector.
     * The general intent is that, for any vector x, the expression: <br>
     * x.clone() != x <br>
     * will be true.
     *
     * @return the copied vector.
     */
    @Override
    public Force clone() {
        Force2D force = new Force2D();
        force.values[0] = values[0];
        force.values[1] = values[1];
        return force;
    }
}
