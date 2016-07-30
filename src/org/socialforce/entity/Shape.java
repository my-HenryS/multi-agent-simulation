package org.socialforce.entity;

import java.io.Serializable;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface Shape extends Serializable, Cloneable, DimensionEntity{
    /**
     * check if a point belongs to a <code>Shape</code>.
     * @return true if the point is a part of the shape; otherwise false.
     */
    boolean contains(Point point);
    
    double getDistance(Point point);
}
