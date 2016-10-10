package org.socialforce.model.impl;

import org.junit.Test;
import org.socialforce.geom.impl.Box2D;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/10/10.
 */
public class WallTest {
    Box2D box2D = new Box2D(3,3,4,5);
    Wall wall = new Wall(box2D);
    @Test
    public void affect() throws Exception {

    }

    @Test
    public void getMass() throws Exception {
        assertTrue(wall.getMass() == Double.POSITIVE_INFINITY);
    }

}