package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;

/**
 * a path finder for the agent in the scene.
 * used to generate path.
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {
    /**
     * get the scene where the path finder should search.
     * @return the scene.
     */
    Scene getScene();

    /**
     * set the scene for the path finder to search.
     * @param scene the scene.
     */
    void setScene(Scene scene);

    /**
     * generate a path from start point to end point.
     * @param agent the start point.
     * @param goal the end point.
     * @return the generated path.
     */
    Path plan(Agent agent,Point goal);
}
