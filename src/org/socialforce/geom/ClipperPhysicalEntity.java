package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/30.
 */
public interface ClipperPhysicalEntity extends PhysicalEntity {
    PhysicalEntity[] clip(ClippablePhysicalEntity shape);
}
