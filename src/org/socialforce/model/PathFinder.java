package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;

/**
 *在场景中，agent 的路径搜索器，
 * 用来搜索并产生一条路径。
 * @author Ledenel
 * @see Path
 * Created by Ledenel on 2016/8/6.
 */
public interface PathFinder {

    /**
     * 获取Pathfinder为Agent生成的路径。
     * @return 搜索出的路径。
     */
    Path plan_for();
}
