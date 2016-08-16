package org.socialforce.entity.impl;

import org.socialforce.entity.Vector;
import org.socialforce.entity.Velocity;

/**
 * Created by Ledenel on 2016/8/16.
 */
public class Velocity2D extends Vector2D implements Velocity {
    public Velocity2D(double x, double y) {
        super(x, y);
    }

    public Velocity2D() {
    }

    /**
     * accumulate the total distance in specific time.
     *
     * @param time the time to be accumulated.
     * @return the delta distance vector.
     */
    @Override
    public Vector deltaDistance(double time) {
        return new Vector2D(values[0] * time, values[1] * time);
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
    public Velocity clone() {
        Velocity2D cloned = new Velocity2D();
        cloned.values[0] = values[0];
        cloned.values[1] = values[1];
        return cloned;
    }
}
