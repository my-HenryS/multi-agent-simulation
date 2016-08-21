package org.socialforce.entity.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.InteractiveEntity;
import org.socialforce.entity.Pool;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListPoolTest {
    Pool<InteractiveEntity> entities;

    @Before
    public void setUp() throws Exception {
        entities = new LinkListPool<>();
        entities.add(new Wall(new Box2D(-1, -1, 2, 2)));
        entities.add(new Wall(new Line2D(new Point2D(3, 3), new Point2D(4, 4))));
        Circle2D circle2D = new Circle2D();
        circle2D.setRadius(4);
        entities.add(new Wall(circle2D));
    }

    @Test
    public void testSelect() throws Exception {
        Iterable<InteractiveEntity> iterable = entities.select(new Box2D(-1, -1, 1, 1));
        int count = 0;
        for (InteractiveEntity entity : iterable) {
            count++;
        }
        assertEquals(2,count);
        for (InteractiveEntity entity : iterable) {
            count++;
        }
        assertEquals(4,count);
    }
}