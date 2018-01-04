package org.socialforce.app.Applications.modeling;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Circle2D;

/**
 * Created by Ledenel on 2018/1/4.
 */
public class OverlappableCircle2D extends Circle2D {
    public OverlappableCircle2D(Point center, double radius) {
        super(center, radius);
        this.forceUpbound = 600;
    }
}
