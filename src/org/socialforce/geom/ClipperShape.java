package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/30.
 */
public interface ClipperShape extends Shape {
    Shape clip(ClipableShape shape);
}
