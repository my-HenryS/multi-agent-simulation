package org.socialforce.entity;

import java.io.Closeable;
import java.io.Serializable;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface Shape extends Serializable, Closeable {
    public boolean contains(Point point);
    public double getDistance(Point point);
}
