package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/31.
 */
public interface Expandable extends Shape {
    Expandable expandBy(double extent);
}
