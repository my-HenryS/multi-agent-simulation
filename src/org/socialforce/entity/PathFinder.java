package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {
    Scene getScene();
    void setScene(Scene scene);
    Path plan(Point start,Point end);
}
