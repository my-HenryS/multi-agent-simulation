package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/8/1.
 */
public interface Box extends Shape{
    Point getStartPoint();
    Point getEndPoint();
    Vector getSize();
    Box intersect(Box other);

}
