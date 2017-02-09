package org.socialforce.geom;

/**
 * 代表有尺寸限制的对象.
 * @author Ledenel
 * @see Vector
 * @see ModelShape
 * Created by Ledenel on 2016/7/28.
 */
public interface DimensionEntity {
    /**
     * 获取该维度实体的尺寸.
     *
     * @return 尺寸.
     */
    int dimension();

}

