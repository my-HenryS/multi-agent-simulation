package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/30.
 */
public interface ClipperModelShape extends ModelShape {
    ModelShape[] clip(ClippableModelShape shape);
}
