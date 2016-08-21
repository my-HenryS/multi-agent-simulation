package org.socialforce.geom;

import org.socialforce.geom.Shape;
import org.socialforce.geom.Vector;

/**
 * represent objects which have dimesion-limits.
 * @author Ledenel
 * @see Vector
 * @see Shape
 * Created by Ledenel on 2016/7/28.
 */
public interface DimensionEntity {
    /**
     * get the dimension of the DimensionEntity.
     *
     * @return the dimension.
     */
    int dimension();

}

